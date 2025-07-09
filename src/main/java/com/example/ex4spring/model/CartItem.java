package com.example.ex4spring.model;

/**
 * Represents an item in the shopping cart.
 * Holds product details, quantity, price per unit, and available stock quantity.
 */
public class CartItem {

    private Long productId;
    private String productName;
    private int quantity;
    private double price; // Price per unit
    private int availableQuantity;

    /**
     * Default no-argument constructor.
     */
    public CartItem() {
    }

    /**
     * Constructs a CartItem with specified product details and quantities.
     *
     * @param productId         the unique identifier of the product
     * @param productName       the name of the product
     * @param quantity          the quantity of the product in the cart
     * @param price             the price per unit of the product
     * @param availableQuantity the quantity of the product available in stock
     */
    public CartItem(Long productId, String productName, int quantity, double price, int availableQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    /**
     * Returns the product ID.
     *
     * @return the productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId the productId to set
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Returns the product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Returns the quantity of the product in the cart.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the cart.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price per unit of the product.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per unit of the product.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Calculates the total price for this cart item
     * based on the unit price and quantity.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * Returns the available quantity of the product in stock.
     *
     * @return the availableQuantity
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the available quantity of the product in stock.
     *
     * @param availableQuantity the availableQuantity to set
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
