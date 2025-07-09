package com.example.ex4spring.repo;

import com.example.ex4spring.model.AdminEntity;
import com.example.ex4spring.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link AdminEntity} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface AdminRepository extends BaseUserRepositoryTemplate<AdminEntity> {
    Optional<AdminEntity> findByEmail(String email);
}