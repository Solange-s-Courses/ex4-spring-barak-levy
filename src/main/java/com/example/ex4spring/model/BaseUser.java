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

    /**
     * Default constructor
     */
    public BaseUser() {}

    /**
     * Constructs a BaseUser with the specified username, email, and password.
     * The accessLocked flag is set to false by default.
     *
     * @param userName the username of the user
     * @param email the email address of the user
     * @param password the user's password
     */
    public BaseUser(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.accessLocked = false;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the user ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the role of the user.
     *
     * @return the user role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the user role to set
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether the user's access is locked.
     *
     * @return true if access is locked, false otherwise
     */
    public boolean isAccessLocked() {
        return accessLocked;
    }

    /**
     * Sets whether the user's access is locked.
     *
     * @param accessLocked true to lock access, false to unlock
     */
    public void setAccessLocked(boolean accessLocked) {
        this.accessLocked = accessLocked;
    }

    /**
     * Returns a string representation of the BaseUser object,
     * including the id, username, and email. Password is omitted for security.
     *
     * @return string representation of the user
     */
    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}