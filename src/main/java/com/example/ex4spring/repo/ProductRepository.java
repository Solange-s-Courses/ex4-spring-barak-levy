package com.example.ex4spring.repo;

import com.example.ex4spring.model.Category;
import com.example.ex4spring.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCompanyIdAndIsDeletedFalse(Long companyId);

    List<ProductEntity> findByCategoryAndIsDeletedFalse(Category category);

    List<ProductEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);

    List<ProductEntity> findByCategoryAndNameContainingIgnoreCaseAndIsDeletedFalse(Category category, String name);

    List<ProductEntity> findByIsDeletedFalse(); // for findAll() but excluding deleted

    Optional<ProductEntity> findByIdAndIsDeletedFalse(Long id);
}
