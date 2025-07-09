package com.example.ex4spring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Represents an admin user in the system.
 *
 * This entity inherits from BaseUser and sets the user's role to ADMIN.
 * It is distinguished in the database with the discriminator value "ADMIN".
 */
@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends BaseUser {

    /**
     * Default constructor
     */
    public AdminEntity() {
        super();
    }

    /**
     * Constructs an AdminEntity with the given user details.
     *
     * @param userName the admin's username
     * @param email the admin's email address
     * @param password the admin's password
     *
     * Upon creation, the user role is set to ADMIN.
     */
    public AdminEntity(String userName, String email, String password) {
        super(userName, email, password);
        setRole(UserRole.ADMIN);
    }
}