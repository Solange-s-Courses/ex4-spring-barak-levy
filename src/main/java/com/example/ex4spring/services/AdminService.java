package com.example.ex4spring.services;

import com.example.ex4spring.model.AdminEntity;
import com.example.ex4spring.repo.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing operations related to AdminEntity.
 * It provides methods to interact with the AdminRepository for retrieving,
 * saving, and searching admin data.
 */
@Service
@Transactional
public class AdminService extends BaseUserServiceTemplate<AdminEntity, AdminRepository> {

    private final AdminRepository adminRepository;

    /**
     * Constructor that initializes the AdminService with the given AdminRepository.
     *
     * @param adminRepository the repository used to perform CRUD operations on AdminEntity
     */
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        super(adminRepository);
        this.adminRepository = adminRepository;
    }

    /**
     * Retrieves all admin entities from the database.
     *
     * @return a list of all AdminEntity objects
     */
    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }

    /**
     * Retrieves an admin entity by its ID.
     *
     * @param id the ID of the admin to find
     * @return an Optional containing the AdminEntity if found, or empty otherwise
     */
    public Optional<AdminEntity> findById(Long id) {
        return adminRepository.findById(id);
    }

    /**
     * Retrieves an admin entity by its email address.
     *
     * @param email the email of the admin to find
     * @return an Optional containing the AdminEntity if found, or empty otherwise
     */
    public Optional<AdminEntity> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    /**
     * Saves a new admin entity or updates an existing one in the database.
     *
     * @param admin the AdminEntity to save or update
     * @return the saved or updated AdminEntity
     */
    public AdminEntity save(AdminEntity admin) {
        return adminRepository.save(admin);
    }

}