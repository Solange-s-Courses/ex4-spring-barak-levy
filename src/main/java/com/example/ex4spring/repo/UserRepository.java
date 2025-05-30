package com.example.ex4spring.repo;

import com.example.ex4spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUserName(String userName);
}
