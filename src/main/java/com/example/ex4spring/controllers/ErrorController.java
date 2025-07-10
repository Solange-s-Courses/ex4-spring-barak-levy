package com.example.ex4spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error Controller handles general error handling for the application.
 */
@Controller
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Simulates an exception being thrown.
     * This method is used to deliberately trigger a runtime exception,
     * which can be helpful for testing error handling mechanisms.
     */
    @RequestMapping("/simulateError")
    public void simulateError() {
        throw new RuntimeException("This is a simulated exception thrown by a controller");
    }

    /**
     * Displays a custom error page and shows the exception message.
     *
     * @param ex The exception that was thrown
     * @param model The model to pass data to the view
     * @return the name of the error view (e.g., error.html)
     */
    @RequestMapping("/errorpage")
    public String error(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    /**
     * Displays a 403 Forbidden page when the user tries to access a restricted resource.
     *
     * @return the name of the 403 view
     */
    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }

    /**
     * Handles all unhandled exceptions in the application.
     * This global exception handler catches any exception not specifically handled elsewhere.
     * It sets the HTTP response status to 500 (Internal Server Error), logs the error,
     * and shows a friendly error page to the user.
     *
     * @param ex The exception that occurred
     * @param model The model to pass data to the view
     * @return the name of the error view
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {

        logger.error("Exception during execution of SpringSecurity application", ex);
        String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}