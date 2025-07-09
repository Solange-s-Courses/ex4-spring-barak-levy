package com.example.ex4spring.services;

import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.model.ReviewEntity;
import com.example.ex4spring.model.UserEntity;
import com.example.ex4spring.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related business logic.
 * Extends {@link BaseUserServiceTemplate} for generic user functionality.
 */
@Service
@Transactional
public class UserService extends BaseUserServiceTemplate<UserEntity, UserRepository> {

    /**
     * Constructs the UserService with the injected {@link UserRepository}.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    /**
     * Retrieves a user by ID and eagerly initializes the user's products and reviews collections.
     *
     * @param id the ID of the user
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    @Override
    public Optional<UserEntity> findById(Long id) {
        Optional<UserEntity> userOpt = super.findById(id);
        userOpt.ifPresent(user -> {
            // Eagerly initialize lazy collections to avoid LazyInitializationException
            user.getProducts().size();
            user.getReviews().size();
        });
        return userOpt;
    }

    /**
     * Adds a product to the user's list of products and saves the user.
     *
     * @param userId the ID of the user
     * @param product the {@link ProductEntity} to add
     */
    public void addProductToUser(Long userId, ProductEntity product) {
        findById(userId).ifPresent(user -> {
            user.addProduct(product);
            save(user);
        });
    }

    /**
     * Removes a product from the user's list of products and saves the user.
     *
     * @param userId the ID of the user
     * @param product the {@link ProductEntity} to remove
     */
    public void removeProductFromUser(Long userId, ProductEntity product) {
        findById(userId).ifPresent(user -> {
            user.removeProduct(product);
            save(user);
        });
    }

    /**
     * Adds a review to the user's list of reviews and saves the user.
     *
     * @param userId the ID of the user
     * @param review the {@link ReviewEntity} to add
     */
    public void addReviewToUser(Long userId, ReviewEntity review) {
        findById(userId).ifPresent(user -> {
            user.getReviews().add(review);
            save(user);
        });
    }
}