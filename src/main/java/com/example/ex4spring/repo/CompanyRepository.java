package com.example.ex4spring.repo;


import com.example.ex4spring.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    List<CompanyEntity> findByUserName(String userName);
}