package com.example.ex4spring.controllers;

import com.example.ex4spring.model.AdminEntity;
import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.model.UserEntity;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "/register";
    }

    @GetMapping("/api/users/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.findByEmail(email).isPresent();
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String userType,
                               @RequestParam String userName,
                               @RequestParam String email,
                               @RequestParam String password,
                               Map<String, Object> model) {

        if (userService.findByEmail(email).isPresent()) {
            model.put("error", "Email is already in use");
            return "/register";
        }

        String encodedPassword = passwordEncoder.encode(password);

        BaseUser newUser;

        switch (userType.toUpperCase()) {
            case "ADMIN":
                newUser = new AdminEntity(userName, email, encodedPassword);
                break;
            case "COMPANY":
                newUser = new CompanyEntity(userName, email, encodedPassword);
                break;
            case "USER":
            default:
                newUser = new UserEntity(userName, email, encodedPassword);
                userService.saveUser((UserEntity) newUser);
                break;
        }

        return "redirect:/login";
    }
}