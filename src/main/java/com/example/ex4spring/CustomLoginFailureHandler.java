package com.example.ex4spring.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomLoginFailureHandler is a custom implementation of Spring Security's AuthenticationFailureHandler.
 * It handles failed login attempts by examining the reason for the failure (e.g., locked account, wrong email),
 * setting an appropriate error message in the session, and redirecting the user back to the login page.
 */
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * This method is triggered automatically when a login attempt fails.
     * It checks the type of AuthenticationException to determine the reason for the failure
     * and sets a corresponding error message in the session. Finally, it redirects the user
     * back to the login page with an error flag in the URL.
     *
     * @param request the HTTP request made by the client
     * @param response the HTTP response returned to the client
     * @param exception the exception thrown during authentication, indicating the reason for failure
     * @throws IOException if an input or output error occurs during redirect
     * @throws ServletException if a servlet specific error occurs
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        if (exception instanceof LockedException) {
            request.getSession().setAttribute("error", "Your account is locked.");
        } else if (exception instanceof org.springframework.security.core.userdetails.UsernameNotFoundException) {
            request.getSession().setAttribute("error", "Email not found.");
        } else {
            request.getSession().setAttribute("error", "Email or password are invalid.");
        }

        response.sendRedirect("/login?error");
    }
}