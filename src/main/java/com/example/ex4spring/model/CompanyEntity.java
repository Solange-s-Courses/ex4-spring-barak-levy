package com.example.ex4spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a company user.
 * Extends BaseUser and sets the role to COMPANY.
 * A company can own multiple products.
 */
@Entity
@DiscriminatorValue("COMPANY")
public class CompanyEntity extends BaseUser {

    /**
     * List of products owned by the company.
     * The relationship is one-to-many, mapped by the "owner" field in ProductEntity.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductEntity> products = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public CompanyEntity() {
        super();
    }

    /**
     * Constructs a CompanyEntity with given user details.
     * Sets the role to COMPANY.
     *
     * @param userName the username of the company user
     * @param email    the email of the company user
     * @param password the password of the company user
     */
    public CompanyEntity(String userName, String email, String password) {
        super(userName, email, password);
        setRole(UserRole.COMPANY);
    }

    /**
     * Returns the list of products owned by the company.
     * @return list of ProductEntity
     */
    public List<ProductEntity> getProducts() {
        return products;
    }

    /**
     * Sets the list of products owned by the company.
     * @param products list of ProductEntity to set
     */
    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    /**
     * Adds a product to the company's product list.
     * @param product the ProductEntity to add
     */
    public void addProduct(ProductEntity product) {
        products.add(product);
    }

    /**
     * Removes a product from the company's product list.
     * Also clears the product's owner reference.
     * @param product the ProductEntity to remove
     */
    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setOwner(null);
    }
}