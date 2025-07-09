package com.example.ex4spring.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a product available for sale.
 * Includes product details such as name, price, quantity, description, category,
 * associated company, owner, image URL, reviews, and deletion status.
 */
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
    private String description;
    private String imageUrl;

    /**
     * Flag indicating whether the product is deleted (soft delete).
     */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * The company selling the product.
     * Many products can belong to one company.
     * Eagerly fetched for immediate availability.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    /**
     * The owner of the product.
     * Many products can belong to one user.
     * Eagerly fetched for immediate availability.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    /**
     * List of reviews associated with this product.
     * One product can have many reviews.
     * Cascade all changes and remove orphan reviews.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    /**
     * Gets the product ID.
     * @return product ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the product ID.
     * @param id product ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product category.
     * @return category enum
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     * @param category category enum
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the price of the product.
     * @return price value
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price price value
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the available quantity of the product.
     * @return quantity in stock
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the available quantity of the product.
     * @param quantity quantity in stock
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the product description.
     * @return description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     * @param description description string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the company selling this product.
     * @return company entity
     */
    public CompanyEntity getCompany() {
        return company;
    }

    /**
     * Sets the company selling this product.
     * @param company company entity
     */
    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    /**
     * Gets the owner of this product.
     * @return owner user entity
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this product.
     * @param owner user entity
     */
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    /**
     * Gets the URL of the product image.
     * @return image URL string
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the product image.
     * @param imageUrl image URL string
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the list of reviews for this product.
     * @return list of reviews
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this product.
     * @param reviews list of review entities
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to this product.
     * Also sets this product as the owner of the review.
     * @param review review entity to add
     */
    public void addReview(ReviewEntity review) {
        reviews.add(review);
        review.setProduct(this);
    }

    /**
     * Removes a review from this product.
     * Also dissociates the review from this product.
     * @param review review entity to remove
     */
    public void removeReview(ReviewEntity review) {
        reviews.remove(review);
        review.setProduct(null);
    }

    /**
     * Returns whether the product is marked as deleted (soft delete).
     * @return true if deleted, false otherwise
     */
    public boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deleted status of the product.
     * @param deleted true to mark as deleted, false otherwise
     */
    public void setIsDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }
}