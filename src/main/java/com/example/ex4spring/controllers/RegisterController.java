package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.BaseUserService; // assuming this is the generic service
import com.example.ex4spring.services.CompanyService;
import com.example.ex4spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegisterController {

    private final UserService userService;
    // private final AdminService adminService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService,
                             // AdminService adminService,
                              CompanyService companyService,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
       // this.adminService = adminService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "/register";
    }

    @GetMapping("/api/users/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.findByEmail(email).isPresent() ||
                // adminService.findByEmail(email).isPresent() ||
                companyService.findByEmail(email).isPresent();
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String userType,
                               @RequestParam String userName,
                               @RequestParam String email,
                               @RequestParam String password,
                               Map<String, Object> model) {

        // Check email in all user types
        boolean emailExists =
                userService.findByEmail(email).isPresent() ||
                        // adminService.findByEmail(email).isPresent() ||
                        companyService.findByEmail(email).isPresent();

        if (emailExists) {
            model.put("error", "Email is already in use");
            return "/register";
        }

        String encodedPassword = passwordEncoder.encode(password);

        switch (userType.toUpperCase()) {
            case "ADMIN":
                AdminEntity admin = new AdminEntity(userName, email, encodedPassword);
                admin.setRole(UserRole.ADMIN);
                // adminService.save(admin);
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