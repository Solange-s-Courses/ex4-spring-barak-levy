package com.example.ex4spring.services;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.repo.BaseUserRepositoryTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Abstract service template that provides common CRUD operations
 * for any entity extending BaseUser and using a compatible repository.
 *
 * @param <T> the type of user (must extend BaseUser)
 * @param <R> the type of repository (must extend BaseUserRepositoryTemplate)
 */
public abstract class BaseUserServiceTemplate<T extends BaseUser, R extends BaseUserRepositoryTemplate<T>> {

    protected final R repository;

    /**
     * Constructor that injects the repository.
     *
     * @param repository the repository used for data access
     */
    public BaseUserServiceTemplate(R repository) {
        this.repository = repository;
    }

    /**
     * Saves or updates a user entity.
     *
     * @param user the user to save
     * @return the saved user entity
     */
    public T save(T user) {
        return repository.save(user);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<T> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * Toggles the accessLocked status of a user.
     *
     * @param id           the ID of the user
     * @param accessLocked the new access lock status to set
     * @throws IllegalArgumentException if the user with the given ID is not found
     */
    public void toggleAccess(Long id, boolean accessLocked) {
        T user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
        user.setAccessLocked(accessLocked);
        repository.save(user);
    }
}