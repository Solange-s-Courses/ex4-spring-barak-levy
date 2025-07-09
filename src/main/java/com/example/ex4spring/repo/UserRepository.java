package com.example.ex4spring.repo;

import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link UserEntity} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface UserRepository extends BaseUserRepositoryTemplate<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}