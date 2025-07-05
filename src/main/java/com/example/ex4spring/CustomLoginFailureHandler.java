package com.example.ex4spring.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

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