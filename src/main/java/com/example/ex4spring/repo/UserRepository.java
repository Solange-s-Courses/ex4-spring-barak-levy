package com.example.ex4spring.repo;

import com.example.ex4spring.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends BaseUserRepositoryTemplate<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}