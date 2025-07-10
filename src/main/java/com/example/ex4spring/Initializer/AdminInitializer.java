package com.example.ex4spring.Initializer;

import com.example.ex4spring.model.AdminEntity;
import com.example.ex4spring.model.UserRole;
import com.example.ex4spring.services.AdminService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final AdminService adminService;
    private boolean alreadySetup = false;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(AdminService adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        if (adminService.findByEmail("admin@example.com").isEmpty()) {
            AdminEntity admin = new AdminEntity();
            admin.setUserName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole(UserRole.ADMIN);
            adminService.save(admin);
        }

        alreadySetup = true;
    }
}