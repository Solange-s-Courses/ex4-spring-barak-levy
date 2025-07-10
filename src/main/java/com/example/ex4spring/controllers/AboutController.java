package com.example.ex4spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for displaying the About page.
 */
@Controller
public class AboutController {

    /**
     * Displays the About page with information about the site
     *
     * @param model The model to pass attributes to the view
     * @return the name of the about view (e.g., about.html)
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        model.addAttribute("description", "Welcome to our website! This platform was built with Spring Boot to demonstrate secure login, error handling, and modern web design.");
        return "/about";
    }
}
