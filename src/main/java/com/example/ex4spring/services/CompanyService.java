package com.example.ex4spring.services;

import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing CompanyEntity operations.
 * Provides methods to perform CRUD operations using CompanyRepository.
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Constructor that injects the CompanyRepository.
     *
     * @param companyRepository the repository used for accessing company data
     */
    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Retrieves all companies from the database.
     *
     * @return a list of all CompanyEntity instances
     */
    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Finds a company by its ID.
     *
     * @param id the ID of the company
     * @return an Optional containing the CompanyEntity if found, or empty otherwise
     */
    public Optional<CompanyEntity> findById(Long id) {
        return companyRepository.findById(id);
    }

    /**
     * Finds a company by its email address.
     *
     * @param email the email of the company
     * @return an Optional containing the CompanyEntity if found, or empty otherwise
     */
    public Optional<CompanyEntity> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    /**
     * Saves a new company or updates an existing one.
     *
     * @param company the company entity to save
     * @return the saved CompanyEntity
     */
    public CompanyEntity save(CompanyEntity company) {
        return companyRepository.save(company);
    }

    /**
     * Deletes a company by its ID.
     *
     * @param id the ID of the company to delete
     */
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}