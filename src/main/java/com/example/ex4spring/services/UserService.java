package com.example.ex4spring.services;

import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.model.ReviewEntity;
import com.example.ex4spring.model.UserEntity;
import com.example.ex4spring.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService extends BaseUserService<UserEntity, UserRepository> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Optional<UserEntity> userOpt = super.findById(id);
        userOpt.ifPresent(user -> {
            // Eagerly initialize lazy collections
            user.getProducts().size();
            user.getReviews().size();
        });
        return userOpt;
    }

    public void addProductToUser(Long userId, ProductEntity product) {
        findById(userId).ifPresent(user -> {
            user.addProduct(product);
            save(user);
        });
    }

    public void removeProductFromUser(Long userId, ProductEntity product) {
        findById(userId).ifPresent(user -> {
            user.removeProduct(product);
            save(user);
        });
    }

    public void addReviewToUser(Long userId, ReviewEntity review) {
        findById(userId).ifPresent(user -> {
            user.getReviews().add(review);
            save(user);
        });
    }
}