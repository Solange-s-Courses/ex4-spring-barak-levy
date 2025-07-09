package com.example.ex4spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a regular user.
 * Extends BaseUser and sets the role to USER.
 * A user can own multiple products and write multiple reviews.
 */
@Entity
@DiscriminatorValue("USER")
public class UserEntity extends BaseUser {

    /**
     * List of products owned by the user.
     * One user can own many products.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductEntity> products = new ArrayList<>();

    /**
     * List of reviews written by the user.
     * One user can write many reviews.
     */
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ReviewEntity> reviews = new ArrayList<>();

    /**
     * Default constructor.
     * Calls BaseUser default constructor.
     */
    public UserEntity() {
        super();
    }

    /**
     * Constructs a user with username, email, and password.
     * Sets the user role to USER.
     *
     * @param userName the user's username
     * @param email the user's email address
     * @param password the user's password (hashed)
     */
    public UserEntity(String userName, String email, String password) {
        super(userName, email, password);
        setRole(UserRole.USER);
    }

    /**
     * Gets the list of products owned by the user.
     * @return list of products
     */
    public List<ProductEntity> getProducts() {
        return products;
    }

    /**
     * Sets the list of products owned by the user.
     * @param products list of products
     */
    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    /**
     * Adds a product to the user's owned products.
     * Also sets this user as the owner of the product.
     * @param product product to add
     */
    public void addProduct(ProductEntity product) {
        products.add(product);
        product.setOwner(this);
    }

    /**
     * Removes a product from the user's owned products.
     * Also nullifies the owner reference in the product.
     * @param product product to remove
     */
    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setOwner(null);
    }

    /**
     * Gets the list of reviews written by the user.
     * @return list of reviews
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews written by the user.
     * @param reviews list of reviews
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to the user's list of reviews.
     * Also sets this user as the reviewer of the review.
     * @param review review to add
     */
    public void addReview(ReviewEntity review) {
        reviews.add(review);
        review.setReviewer(this);
    }

    /**
     * Removes a review from the user's list of reviews.
     * Also nullifies the reviewer reference in the review.
     * @param review review to remove
     */
    public void removeReview(ReviewEntity review) {
        reviews.remove(review);
        review.setReviewer(null);
    }
}