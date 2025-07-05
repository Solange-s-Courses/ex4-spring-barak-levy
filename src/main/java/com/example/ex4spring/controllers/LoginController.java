package com.example.ex4spring.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {

    @GetMapping("")
    public String showLoginPage() {
        return "redirect:/login";
    }

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