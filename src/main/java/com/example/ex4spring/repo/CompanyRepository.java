package com.example.ex4spring.repo;

import com.example.ex4spring.model.CompanyEntity;

import java.util.Optional;

public interface CompanyRepository extends BaseUserRepositoryTemplate<CompanyEntity> {
    Optional<CompanyEntity> findByEmail(String email);
}