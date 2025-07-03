package com.example.ex4spring.controllers;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.services.BaseUserService;
import com.example.ex4spring.services.BaseUserService2;
import com.example.ex4spring.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final BaseUserService2 baseUserService;

    public UserController(BaseUserService2 baseUserService) {
        this.baseUserService = baseUserService;
    }

    @GetMapping
    public List<BaseUser> getAllUsers() {
        return baseUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUser> getUserById(@PathVariable Long id) {
        Optional<BaseUser> userOpt = baseUserService.findById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<BaseUser> getUserByEmail(@PathVariable String email) {
        Optional<BaseUser> userOpt = baseUserService.findByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUser user) {
        BaseUser createdUser = baseUserService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseUser> updateUser(@PathVariable Long id, @RequestBody BaseUser updatedUser) {
        Optional<BaseUser> userOpt = baseUserService.findById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BaseUser existingUser = userOpt.get();

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setAccessLocked(updatedUser.isAccessLocked());

        baseUserService.save(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<BaseUser> userOpt = baseUserService.findById(id);
        if (userOpt.isPresent()) {
            baseUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}