package com.example.ex4spring.repo;

import com.example.ex4spring.model.ReviewEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @EntityGraph(attributePaths = {"product", "reviewer"})
    List<ReviewEntity> findByProductId(Long productId);
}