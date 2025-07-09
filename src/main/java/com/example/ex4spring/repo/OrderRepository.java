package com.example.ex4spring.repo;

import com.example.ex4spring.model.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link OrderEntity} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = {"product", "sellerCompany"})
    List<OrderEntity> findByBuyerId(Long buyerId);


    @EntityGraph(attributePaths = {"product", "sellerCompany", "buyer"})
    List<OrderEntity> findBySellerCompanyId(Long companyId);

    @EntityGraph(attributePaths = {"product", "sellerCompany"})
    List<OrderEntity> findByProductId(Long productId);
}