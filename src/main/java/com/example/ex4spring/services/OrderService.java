package com.example.ex4spring.services;

import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.model.OrderEntity;
import com.example.ex4spring.model.UserEntity;
import com.example.ex4spring.repo.ProductRepository;
import com.example.ex4spring.repo.OrderRepository;
import com.example.ex4spring.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    public List<OrderEntity> findByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    public List<OrderEntity> findBySellerCompanyId(Long companyId) {
        return orderRepository.findBySellerCompanyId(companyId);
    }

    @Transactional
    public Optional<OrderEntity> createOrder(Long productId, Long userId, int quantity) {
        Optional<ProductEntity> optionalProduct = productRepository.findByIdAndIsDeletedFalse(productId);
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalProduct.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }

        ProductEntity product = optionalProduct.get();
        UserEntity user = optionalUser.get();

        if (product.getQuantity() < quantity) {
            return Optional.empty(); // Not enough stock
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product); // update quantity

        OrderEntity order = new OrderEntity();
        order.setProduct(product);
        order.setBuyer(user);
        order.setQuantityPurchased(quantity);
        order.setTotalPrice(quantity * product.getPrice());
        order.setPurchaseDate(LocalDateTime.now());
        order.setSellerCompany(product.getCompany());

        return Optional.of(orderRepository.save(order));
    }

    public boolean hasUserPurchasedProduct(Long userId, Long productId) {
        List<OrderEntity> orders = orderRepository.findByBuyerId(userId);
        for (OrderEntity order : orders) {
            if (order.getProduct().getId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
}