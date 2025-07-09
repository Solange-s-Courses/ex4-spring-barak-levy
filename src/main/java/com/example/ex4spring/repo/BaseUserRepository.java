package com.example.ex4spring.repo;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for {@link BaseUser} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByEmail(String email);
}