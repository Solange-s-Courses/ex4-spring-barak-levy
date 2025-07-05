package com.example.ex4spring.repo;

import com.example.ex4spring.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByEmail(String email);
}