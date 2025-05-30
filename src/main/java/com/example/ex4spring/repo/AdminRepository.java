package com.example.ex4spring.repo;


import com.example.ex4spring.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    List<AdminEntity> findByUserName(String userName);
}