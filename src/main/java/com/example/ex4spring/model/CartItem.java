package com.example.ex4spring.model;

public class CartItem {

    private Long productId;
    private String productName;
    private int quantity;
    private double price; // Price per unit
    private int availableQuantity;

    // Default constructor
    public CartItem() {
    }

    // Constructor
    public CartItem(Long productId, String productName, int quantity, double price, int availableQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Calculate total price for this item
    public double getTotalPrice() {
        return price * quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
