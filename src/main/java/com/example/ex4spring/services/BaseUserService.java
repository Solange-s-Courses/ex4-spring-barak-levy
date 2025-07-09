package com.example.ex4spring.services;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.repo.BaseUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing base users in the system.
 */
@Service
public class BaseUserService {

    private final BaseUserRepository baseUserRepository;

    /**
     * Constructor for BaseUserService.
     *
     * @param baseUserRepository the repository used for user data access
     */
    public BaseUserService(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    public List<BaseUser> findAll() {
        return baseUserRepository.findAll();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<BaseUser> findById(Long id) {
        return baseUserRepository.findById(id);
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<BaseUser> findByEmail(String email) {
        return baseUserRepository.findByEmail(email);
    }

    /**
     * Saves or updates a user in the repository.
     *
     * @param user the user to save
     * @return the saved user entity
     */
    public BaseUser save(BaseUser user) {
        return baseUserRepository.save(user);
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteById(Long id) {
        baseUserRepository.deleteById(id);
    }

    /**
     * Toggles the accessLocked status of a user.
     *
     * @param id the ID of the user
     * @param accessLocked the new access lock status to set
     * @throws IllegalArgumentException if the user with the given ID is not found
     */
    public void toggleAccess(Long id, boolean accessLocked) {
        BaseUser user = baseUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
        user.setAccessLocked(accessLocked);
        baseUserRepository.save(user);
    }
}