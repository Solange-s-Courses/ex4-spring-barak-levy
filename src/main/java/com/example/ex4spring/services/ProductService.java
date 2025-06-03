package com.example.ex4spring.services;

import com.example.ex4spring.model.Category;
import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductEntity> findByCompanyId(Long companyId) {
        return productRepository.findByCompanyId(companyId);
    }

    public List<ProductEntity> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductEntity> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ProductEntity> findByCategoryAndName(Category category, String name) {
        return productRepository.findByCategoryAndNameContainingIgnoreCase(category, name);
    }
}