package com.example.ex4spring.controllers;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.services.BaseUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * Controller responsible for handling the user's profile page.
 * Retrieves the currently authenticated user's details and passes them to the view.
 */
@Controller
public class ProfileController {

    private final BaseUserService userService;

    /**
     * Constructor for injecting the user service.
     * @param userService the service for accessing user data
     */
    public ProfileController(BaseUserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to the /profile endpoint.
     * Retrieves the authenticated user's profile data and displays it.
     *
     * @param model the model to pass user data to the view
     * @return the profile view if user is found, otherwise an error view
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<BaseUser> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "profile";
        } else {
            return "error";
        }
    }
}