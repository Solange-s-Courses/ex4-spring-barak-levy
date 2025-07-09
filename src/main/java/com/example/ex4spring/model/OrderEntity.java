package com.example.ex4spring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents an order placed in the system.
 * Contains details about the purchased product, buyer, seller company, quantity, price, and purchase date.
 */
@Entity
public class OrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantityPurchased;
    private double totalPrice;
    private LocalDateTime purchaseDate;

    /**
     * The buyer who placed the order.
     * Many orders can be placed by one user.
     * Eagerly fetched for immediate availability.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id")
    private UserEntity buyer;

    /**
     * The product being purchased in the order.
     * Many orders can relate to one product.
     * Eagerly fetched for immediate availability.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /**
     * The company selling the product.
     * Many orders can relate to one selling company.
     * Eagerly fetched for immediate availability.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity sellerCompany;

    /**
     * Gets the order ID.
     * @return the order ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the order ID.
     * @param id the order ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the quantity purchased in the order.
     * @return quantity purchased
     */
    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    /**
     * Sets the quantity purchased in the order.
     * @param quantityPurchased quantity purchased
     */
    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    /**
     * Gets the total price for this order.
     * @return total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for this order.
     * @param totalPrice total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the purchase date and time.
     * @return purchase date/time
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the purchase date and time.
     * @param purchaseDate purchase date/time
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets the buyer of the order.
     * @return buyer user entity
     */
    public UserEntity getBuyer() {
        return buyer;
    }

    /**
     * Sets the buyer of the order.
     * @param buyer user entity
     */
    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    /**
     * Gets the product in the order.
     * @return product entity
     */
    public ProductEntity getProduct() {
        return product;
    }

    /**
     * Sets the product in the order.
     * @param product product entity
     */
    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    /**
     * Gets the company selling the product.
     * @return company entity
     */
    public CompanyEntity getSellerCompany() {
        return sellerCompany;
    }

    /**
     * Sets the company selling the product.
     * @param sellerCompany company entity
     */
    public void setSellerCompany(CompanyEntity sellerCompany) {
        this.sellerCompany = sellerCompany;
    }
}