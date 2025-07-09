package com.example.ex4spring.repo;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Generic repository interface for {@link BaseUser} entities.
 *
 * @param <T> the type of the BaseUser subclass
 */
public interface BaseUserRepositoryTemplate<T extends BaseUser> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}