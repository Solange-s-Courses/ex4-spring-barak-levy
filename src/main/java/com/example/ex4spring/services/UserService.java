package com.example.ex4spring.services;

import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.model.ReviewEntity;
import com.example.ex4spring.model.UserEntity;
import com.example.ex4spring.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create or update a user
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Find user by ID
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Find all users
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Add product to user
    public void addProductToUser(Long userId, ProductEntity product) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.addProduct(product);
            userRepository.save(user);
        });
    }

    // Remove product from user
    public void removeProductFromUser(Long userId, ProductEntity product) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.removeProduct(product);
            userRepository.save(user);
        });
    }

    // Add review to user
    public void addReviewToUser(Long userId, ReviewEntity review) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.getReviews().add(review);
            userRepository.save(user);
        });
    }

    // Find user by username
    public Optional<UserEntity> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }
}