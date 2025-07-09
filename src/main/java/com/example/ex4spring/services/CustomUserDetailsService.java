package com.example.ex4spring.services;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.repo.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * Loads user-specific data during the authentication process.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BaseUserRepository userRepository;

    /**
     * Locates the user based on the email address. This method is called by Spring Security
     * during the authentication process.
     *
     * @param email the email address identifying the user whose data is required.
     * @return a fully populated {@link UserDetails} object (used by Spring Security).
     * @throws UsernameNotFoundException if the user is not found.
     * @throws LockedException if the user's access is locked.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (user.isAccessLocked()) {
            throw new LockedException("User access is locked");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}