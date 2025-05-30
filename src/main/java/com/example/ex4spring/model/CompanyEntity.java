package com.example.ex4spring.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COMPANY")
public class CompanyEntity extends BaseUser {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    public CompanyEntity() {
        super();
    }

    public CompanyEntity(String userName, String email, String password) {
        super(userName, email, password);
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void addProduct(ProductEntity product) {
        products.add(product);
        // product.setOwner(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setOwner(null);
    }
}