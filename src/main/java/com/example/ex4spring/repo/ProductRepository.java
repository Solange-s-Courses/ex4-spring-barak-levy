package com.example.ex4spring.repo;

import com.example.ex4spring.model.Category;
import com.example.ex4spring.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCompanyId(Long companyId);
    List<ProductEntity> findByCategory(Category category);
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
    List<ProductEntity> findByCategoryAndNameContainingIgnoreCase(Category category, String name);
}
