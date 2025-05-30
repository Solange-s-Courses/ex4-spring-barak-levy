package com.example.ex4spring.repo;

import com.example.ex4spring.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Add custom queries if needed
}
