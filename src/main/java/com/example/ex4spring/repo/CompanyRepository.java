package com.example.ex4spring.repo;

import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link CompanyEntity} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface CompanyRepository extends BaseUserRepositoryTemplate<CompanyEntity> {
    Optional<CompanyEntity> findByEmail(String email);
}