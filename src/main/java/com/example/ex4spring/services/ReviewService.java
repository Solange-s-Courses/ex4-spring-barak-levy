package com.example.ex4spring.services;

import com.example.ex4spring.model.ReviewEntity;
import com.example.ex4spring.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling review-related business logic.
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * Constructor that injects the {@link ReviewRepository}.
     *
     * @param reviewRepository the review repository
     */
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Finds all reviews for a specific product.
     *
     * @param productId the ID of the product
     * @return list of {@link ReviewEntity} related to the product
     */
    public List<ReviewEntity> findByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    /**
     * Saves a new review or updates an existing one.
     *
     * @param review the {@link ReviewEntity} to save
     * @return the saved review
     */
    public ReviewEntity save(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    /**
     * Finds a single review by a specific user for a specific product.
     *
     * @param userId the ID of the reviewer
     * @param productId the ID of the product
     * @return the matching {@link ReviewEntity}, or null if none found
     */
    public ReviewEntity findByReviewerIdAndProductId(long userId, Long productId) {
        return reviewRepository.findByReviewerIdAndProductId(userId, productId);
    }

    /**
     * Finds all reviews written by a specific user for a specific product.
     * (Usually, this should return at most one review unless the design allows multiple.)
     *
     * @param userId the ID of the reviewer
     * @param productId the ID of the product
     * @return list of matching {@link ReviewEntity}
     */
    public List<ReviewEntity> findAllByReviewerIdAndProductId(long userId, Long productId) {
        return reviewRepository.findAllByReviewerIdAndProductId(userId, productId);
    }
}