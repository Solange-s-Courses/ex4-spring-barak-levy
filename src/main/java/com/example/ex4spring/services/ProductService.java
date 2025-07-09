package com.example.ex4spring.services;

import com.example.ex4spring.model.Category;
import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing product-related business logic.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor that injects the product repository.
     *
     * @param productRepository the repository for accessing products
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all non-deleted products.
     *
     * @return list of active {@link ProductEntity}
     */
    public List<ProductEntity> findAll() {
        return productRepository.findByIsDeletedFalse();
    }

    /**
     * Finds a non-deleted product by its ID.
     *
     * @param id the product ID
     * @return an {@link Optional} containing the product if found
     */
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findByIdAndIsDeletedFalse(id);
    }

    /**
     * Saves or updates a product.
     *
     * @param product the product entity to save
     * @return the saved {@link ProductEntity}
     */
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    /**
     * Soft deletes a product by marking it as deleted.
     *
     * @param id the ID of the product to delete
     */
    public void softDeleteById(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setIsDeleted(true);
            productRepository.save(product);
        });
    }

    /**
     * Finds all products belonging to a specific company, excluding deleted ones.
     *
     * @param companyId the ID of the company
     * @return list of products for that company
     */
    public List<ProductEntity> findByCompanyId(Long companyId) {
        return productRepository.findByCompanyIdAndIsDeletedFalse(companyId);
    }

    /**
     * Finds all non-deleted products in a specific category.
     *
     * @param category the category to filter by
     * @return list of products in the category
     */
    public List<ProductEntity> findByCategory(Category category) {
        return productRepository.findByCategoryAndIsDeletedFalse(category);
    }

    /**
     * Finds all non-deleted products whose name contains a specific substring (case-insensitive).
     *
     * @param name the substring to match in the product name
     * @return list of matching products
     */
    public List<ProductEntity> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    /**
     * Finds all non-deleted products that match a specific category and partial name.
     *
     * @param category the category to filter by
     * @param name the name substring to match
     * @return list of matching products
     */
    public List<ProductEntity> findByCategoryAndName(Category category, String name) {
        return productRepository.findByCategoryAndNameContainingIgnoreCaseAndIsDeletedFalse(category, name);
    }

    /**
     * Dynamically filters products by optional category string and name.
     *
     * @param categoryStr the category as string (can be null or blank)
     * @param name the name substring to match (can be null or blank)
     * @return list of products matching the filters
     */
    public List<ProductEntity> getFilteredProducts(String categoryStr, String name) {
        boolean hasCategory = categoryStr != null && !categoryStr.isBlank();
        boolean hasName = name != null && !name.isBlank();

        if (!hasCategory && !hasName) {
            return productRepository.findByIsDeletedFalse();
        }

        if (hasCategory) {
            try {
                Category category = Category.valueOf(categoryStr.toUpperCase());
                if (hasName) {
                    return productRepository.findByCategoryAndNameContainingIgnoreCaseAndIsDeletedFalse(category, name);
                } else {
                    return productRepository.findByCategoryAndIsDeletedFalse(category);
                }
            } catch (IllegalArgumentException e) {
                // Invalid category string
                return List.of();
            }
        } else {
            return productRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
        }
    }
}