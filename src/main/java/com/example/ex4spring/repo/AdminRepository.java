package com.example.ex4spring.repo;

import com.example.ex4spring.model.AdminEntity;

import java.util.Optional;

public interface AdminRepository extends BaseUserRepositoryTemplate<AdminEntity> {
    Optional<AdminEntity> findByEmail(String email);
}