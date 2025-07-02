package com.example.ex4spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String userName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid, please try again")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "access_locked")
    private boolean accessLocked = false;

    public BaseUser() {}

    public BaseUser(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.accessLocked = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccessLocked() {
        return accessLocked;
    }

    public void setAccessLocked(boolean accessLocked) {
        this.accessLocked = accessLocked;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}