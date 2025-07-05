package com.example.ex4spring.repo;

import com.example.ex4spring.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepositoryTemplate<T extends BaseUser> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}
