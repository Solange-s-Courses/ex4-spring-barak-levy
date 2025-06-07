package com.example.ex4spring.services;

import com.example.ex4spring.model.ReviewEntity;
import com.example.ex4spring.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewEntity> findByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public ReviewEntity save(ReviewEntity review) {
        return reviewRepository.save(review);
    }
}