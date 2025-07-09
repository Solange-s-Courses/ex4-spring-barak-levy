package com.example.ex4spring.controllers;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.services.BaseUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing base users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final BaseUserService baseUserService;

    /**
     * Constructs a UserController with the given BaseUserService.
     *
     * @param baseUserService service to manage base users
     */
    public UserController(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    /**
     * Retrieves all users.
     *
     * @return list of all BaseUser entities
     */
    @GetMapping
    public List<BaseUser> getAllUsers() {
        return baseUserService.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user if found, or 404 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseUser> getUserById(@PathVariable Long id) {
        Optional<BaseUser> userOpt = baseUserService.findById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return the user if found, or 404 if not
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<BaseUser> getUserByEmail(@PathVariable String email) {
        Optional<BaseUser> userOpt = baseUserService.findByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user entity
     */
    @PostMapping
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUser user) {
        BaseUser createdUser = baseUserService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id the ID of the user to update
     * @param updatedUser the updated user data
     * @return the updated user if found and updated, or 404 if not found
     */
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

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return 204 if deleted, 404 if user not found
     */
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