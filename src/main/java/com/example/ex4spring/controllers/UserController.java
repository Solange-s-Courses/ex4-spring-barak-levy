package com.example.ex4spring.controllers;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.services.BaseUserService;
import com.example.ex4spring.services.BaseUserService2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (userOpt.isPresent()) {
            BaseUser existingUser = userOpt.get();
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            // other fields

            baseUserService.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
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

/* import com.example.ex4spring.model.UserEntity;
import com.example.ex4spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.findAll();
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> userOpt = userService.findById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        Optional<UserEntity> userOpt = userService.findByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - create a new user
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    // PUT - update existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        Optional<UserEntity> userOpt = userService.findById(id);

        if (userOpt.isPresent()) {
            UserEntity existingUser = userOpt.get();
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            userService.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - remove user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<UserEntity> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} */