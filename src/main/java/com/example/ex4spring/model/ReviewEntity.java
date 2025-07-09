package com.example.ex4spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Entity representing a product review submitted by a user.
 * Includes details such as the product reviewed, the reviewer,
 * the comment, rating, and creation timestamp.
 */
@Entity
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The product being reviewed.
     * Many reviews can belong to one product.
     * Lazy fetching to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /**
     * The user who wrote the review.
     * Many reviews can belong to one user.
     * Lazy fetching to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private UserEntity reviewer;

    private String comment;

     // The rating is between 1 and 5.
    @Min(1)
    @Max(5)
    private int rating;

    private LocalDateTime createdAt;

    /**
     * Default constructor
     */
    public ReviewEntity() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor to create a new review with given details.
     * Automatically sets creation timestamp to now.
     *
     * @param product the product being reviewed
     * @param reviewer the user who wrote the review
     * @param comment the review comment text
     * @param rating the rating value (1-5)
     */
    public ReviewEntity(ProductEntity product, UserEntity reviewer, String comment, int rating) {
        this.product = product;
        this.reviewer = reviewer;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Gets the review ID.
     * @return review ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the review ID.
     * @param id review ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product being reviewed.
     * @return product entity
     */
    public ProductEntity getProduct() {
        return product;
    }

    /**
     * Sets the product being reviewed.
     * @param product product entity
     */
    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    /**
     * Gets the user who wrote the review.
     * @return user entity
     */
    public UserEntity getReviewer() {
        return reviewer;
    }

    /**
     * Sets the user who wrote the review.
     * @param reviewer user entity
     */
    public void setReviewer(UserEntity reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Gets the comment text of the review.
     * @return comment string
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment text of the review.
     * @param comment comment string
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the rating value.
     * @return rating between 1 and 5
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating value.
     * @param rating rating between 1 and 5
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the creation timestamp of the review.
     * @return creation datetime
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the review.
     * @param createdAt creation datetime
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}