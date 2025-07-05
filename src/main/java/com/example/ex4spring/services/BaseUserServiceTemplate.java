package com.example.ex4spring.services;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.repo.BaseUserRepositoryTemplate;

import java.util.List;
import java.util.Optional;

public abstract class BaseUserServiceTemplate<T extends BaseUser, R extends BaseUserRepositoryTemplate<T>> {

    protected final R repository;

    public BaseUserServiceTemplate(R repository) {
        this.repository = repository;
    }

    public T save(T user) {
        return repository.save(user);
    }

    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<T> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void toggleAccess(Long id, boolean accessLocked) {
        T user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
        user.setAccessLocked(accessLocked);
        repository.save(user);
    }
}