package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.AdminService;
import com.example.ex4spring.services.CompanyService;
import com.example.ex4spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Controller for handling user registration.
 */
@Controller
public class RegisterController {

    private final UserService userService;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a RegisterController with injected services and password encoder.
     *
     * @param userService service for managing users
     * @param adminService service for managing admins
     * @param companyService service for managing companies
     * @param passwordEncoder encoder for hashing passwords
     */
    @Autowired
    public RegisterController(UserService userService,
                              AdminService adminService,
                              CompanyService companyService,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.adminService = adminService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Displays the registration form.
     * Redirects authenticated users to the products page.
     *
     * @param authentication the current authentication context
     * @return the registration form view or a redirect to /products
     */
    @GetMapping("/register")
    public String showRegisterForm(Authentication authentication) {
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products";
        }
        return "/register";
    }

    /**
     * Checks whether an email is already taken by any type of user.
     * Accessible via AJAX from the frontend.
     *
     * @param email the email to check
     * @return a JSON response indicating whether the email exists
     */
    @GetMapping("/api/users/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.findByEmail(email).isPresent() ||
                adminService.findByEmail(email).isPresent() ||
                companyService.findByEmail(email).isPresent();
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    /**
     * Processes registration form submission and creates a new user,
     * company, or admin based on the selected user type.
     * Check if the password is right and if email already exists in the system,
     * Encode password before storing it and create and save the appropriate user type.
     *
     * @param userType the type of user to register (USER, COMPANY, ADMIN)
     * @param userName the desired username
     * @param email the email address
     * @param password the password
     * @param confirmPassword confirmation of the password
     * @param model a map for returning error messages to the view
     * @return redirect to login on success or back to register page with error
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String userType,
                               @RequestParam String userName,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam("confirm-password") String confirmPassword,
                               Map<String, Object> model) {

        if (!password.equals(confirmPassword)) {
            model.put("error", "Passwords do not match");
            return "/register";
        }

        boolean emailExists =
                userService.findByEmail(email).isPresent() ||
                        companyService.findByEmail(email).isPresent() ||
                        adminService.findByEmail(email).isPresent();

        if (emailExists) {
            model.put("error", "Email is already in use");
            return "/register";
        }

        String encodedPassword = passwordEncoder.encode(password);

        switch (userType.toUpperCase()) {
            case "ADMIN":
                AdminEntity admin = new AdminEntity(userName, email, encodedPassword);
                admin.setRole(UserRole.ADMIN);
                adminService.save(admin);
                break;

            case "COMPANY":
                CompanyEntity company = new CompanyEntity(userName, email, encodedPassword);
                company.setRole(UserRole.COMPANY);
                companyService.save(company);
                break;

            case "USER":
            default:
                UserEntity user = new UserEntity(userName, email, encodedPassword);
                user.setRole(UserRole.USER);
                userService.save(user);
                break;
        }

        return "redirect:/login";
    }
}