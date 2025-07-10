package com.example.ex4spring.model;

/**
 * The Enum for the products categories.
 */
public enum Category {
    SMARTPHONES("Smartphones"),
    COMPUTERS("Computers"),
    TV("TV");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}