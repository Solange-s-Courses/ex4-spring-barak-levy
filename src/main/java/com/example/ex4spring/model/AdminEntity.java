package com.example.ex4spring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends BaseUser {

    public AdminEntity() {
        super();
    }

    public AdminEntity(String userName, String email, String password) {
        super(userName, email, password);
    }
}