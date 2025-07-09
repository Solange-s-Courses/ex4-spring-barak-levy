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

/**
 * Service class that handles business logic related to orders,
 * such as creation, retrieval, and validation.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Constructor that injects required repositories.
     *
     * @param orderRepository the repository for accessing orders
     * @param productRepository the repository for accessing products
     * @param userRepository the repository for accessing users
     */
    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all {@link OrderEntity}
     */
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Saves a given order to the database.
     *
     * @param order the order to save
     */
    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    /**
     * Retrieves all orders made by a specific buyer.
     *
     * @param buyerId the ID of the buyer
     * @return a list of orders made by the buyer
     */
    public List<OrderEntity> findByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    /**
     * Retrieves all orders that contain products sold by a specific seller company.
     *
     * @param companyId the ID of the seller company
     * @return a list of orders related to the company
     */
    public List<OrderEntity> findBySellerCompanyId(Long companyId) {
        return orderRepository.findBySellerCompanyId(companyId);
    }

    /**
     * Creates a new order for a specific product and user.
     * Reduces the product quantity accordingly.
     *
     * @param productId the ID of the product being purchased
     * @param userId the ID of the user making the purchase
     * @param quantity the number of units to purchase
     * @return an {@link Optional} containing the created order if successful, or empty if invalid
     */
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
        productRepository.save(product); // Update the product quantity

        OrderEntity order = new OrderEntity();
        order.setProduct(product);
        order.setBuyer(user);
        order.setQuantityPurchased(quantity);
        order.setTotalPrice(quantity * product.getPrice());
        order.setPurchaseDate(LocalDateTime.now());
        order.setSellerCompany(product.getCompany());

        return Optional.of(orderRepository.save(order));
    }

    /**
     * Checks if a specific user has purchased a specific product.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product
     * @return true if the user has purchased the product, false otherwise
     */
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