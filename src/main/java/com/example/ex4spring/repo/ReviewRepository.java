package com.example.ex4spring.repo;

import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.model.ReviewEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link ReviewEntity} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @EntityGraph(attributePaths = {"product", "reviewer"})
    List<ReviewEntity> findByProductId(Long productId);

    ReviewEntity findByReviewerIdAndProductId(long reviewerId, Long productId);

    List<ReviewEntity >findAllByReviewerIdAndProductId(long reviewerId, Long productId);
}