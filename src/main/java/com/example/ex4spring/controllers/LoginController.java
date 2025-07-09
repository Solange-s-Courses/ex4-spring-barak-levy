package com.example.ex4spring.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

/**
 * Controller responsible for handling login page requests and redirects.
 * Manages the logic for redirecting authenticated users, displaying logout messages,
 * and showing error messages during login attempts.
 */
@Controller
public class LoginController {

    /**
     * Handles the root URL ("/") and redirects users to the login page.
     * @return a redirect to the login page
     */
    @GetMapping("")
    public String showLoginPage() {
        return "redirect:/login";
    }

    /**
     * Displays the login page or redirects authenticated users to the products page.
     * Also handles the logout and error messages to display to the user.
     *
     * @param logout optional logout query parameter
     * @param model the model to pass attributes to the view
     * @param authentication the current authentication object (injected by Spring Security)
     * @param request the current HTTP servlet request (used to retrieve session attributes)
     * @return the name of the login view or a redirect if the user is already authenticated
     */
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            Authentication authentication,
            HttpServletRequest request) {

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products";
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out.");
        }

        Object error = request.getSession().getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error.toString());
            request.getSession().removeAttribute("error");
        }

        return "login";
    }
}