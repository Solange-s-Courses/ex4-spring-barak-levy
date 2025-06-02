package com.example.ex4spring.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("USER")
public class UserEntity extends BaseUser {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    public UserEntity() {
        super();
    }

    public UserEntity(String userName, String email, String password) {
        super(userName, email, password);
        setRole(UserRole.USER);
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void addProduct(ProductEntity product) {
        products.add(product);
        product.setOwner(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setOwner(null);
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public void addReview(ReviewEntity review) {
        reviews.add(review);
        review.setReviewer(this);
    }

    public void removeReview(ReviewEntity review) {
        reviews.remove(review);
        review.setReviewer(null);
    }
}