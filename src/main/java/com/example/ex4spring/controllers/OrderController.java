package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling order-related operations such as confirming purchases,
 * placing orders, viewing order history, and buying items from the cart.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    private final BaseUserService baseUserService;

    /**
     * Constructor for injecting necessary services.
     */
    @Autowired
    public OrderController(OrderService orderService,
                           ProductService productService,
                           UserService userService,
                           BaseUserService baseUserService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.baseUserService = baseUserService;
    }

    /**
     * Displays a confirmation page for a single product purchase initiated by the "Buy Now" button.
     *
     * @param productId ID of the product to buy.
     * @param quantity Quantity of the product.
     * @param redirectAttributes Redirect attributes for passing error messages.
     * @param model Model for sending product data to the view.
     * @return View name for confirmation page or redirect on error.
     */
    @PostMapping("/confirm")
    public String confirmPurchase(@RequestParam Long productId,
                                  @RequestParam int quantity,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        ProductEntity product = productService.findById(productId).orElse(null);
        if (product == null || product.getQuantity() < quantity) {
            redirectAttributes.addFlashAttribute("error", "Not enough stock for this product.");
            return "redirect:/products/product-page/" + productId;
        }

        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);
        model.addAttribute("totalPrice", product.getPrice() * quantity);
        return "confirm-buy";
    }

    /**
     * Handles the placement of the order after confirmation.
     *
     * @param productId ID of the product to buy.
     * @param quantity Quantity to purchase.
     * @param redirectAttributes Redirect attributes for success or failure messages.
     * @param principal Represents the authenticated user.
     * @param model Model for error messages.
     * @return Redirects to user's order list or product page on error.
     */
    @PostMapping("/buy")
    @Transactional
    public String placeOrder(@RequestParam Long productId,
                             @RequestParam int quantity,
                             RedirectAttributes redirectAttributes,
                             Principal principal,
                             Model model) {
        ProductEntity product = productService.findById(productId).orElse(null);
        if (product == null || product.getQuantity() < quantity) {
            model.addAttribute("error", "Not enough stock or product doesn't exist.");
            return "redirect:/products/product-page/" + productId;
        }

        UserEntity buyer = userService.findByEmail(principal.getName()).orElse(null);
        if (buyer == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        OrderEntity order = new OrderEntity();
        order.setProduct(product);
        order.setBuyer(buyer);
        order.setQuantityPurchased(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setPurchaseDate(LocalDateTime.now());
        order.setSellerCompany(product.getCompany());

        orderService.save(order);

        // Reduce quantity in stock
        product.setQuantity(product.getQuantity() - quantity);
        productService.save(product);

        redirectAttributes.addFlashAttribute("message",
                "Purchase " + product.getName() + " successful.");
        return "redirect:/orders/my-orders";
    }

    /**
     * Displays the list of orders for the currently logged-in user.
     *
     * @param principal Authenticated user's identity.
     * @param message Optional message from previous actions.
     * @param model Model to hold order list and messages.
     * @return View name for the orders list.
     */
    @GetMapping("/my-orders")
    public String viewMyOrders(Principal principal,
                               @RequestParam(value = "message", required = false) String message,
                               Model model) {
        BaseUser user = baseUserService.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        if (message != null) {
            model.addAttribute("message", message);
        }

        List<OrderEntity> orders = new ArrayList<>();

        if (user.getRole() == UserRole.USER) {
            orders = orderService.findByBuyerId(user.getId());
        } else if (user.getRole() == UserRole.COMPANY) {
            orders = orderService.findBySellerCompanyId(user.getId());
        }

        model.addAttribute("orders", orders);
        return "orders";
    }

    /**
     * Processes the purchase of all items in the shopping cart.
     *
     * @param principal Authenticated user placing the order.
     * @param session HTTP session containing the cart.
     * @param redirectAttributes Redirect attributes to show success/failure.
     * @param model Model for error handling.
     * @return Redirect to order list or cart page depending on outcome.
     */
    @PostMapping("/buy-from-cart")
    @Transactional
    public String confirmCartPurchase(Principal principal,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        UserEntity buyer = userService.findByEmail(principal.getName()).orElse(null);
        if (buyer == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        // Retrieve cart from session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/cart/view";
        }

        List<String> failedItems = new ArrayList<>();

        for (CartItem item : cart) {
            ProductEntity product = productService.findById(item.getProductId()).orElse(null);
            if (product == null || product.getQuantity() < item.getQuantity()) {
                failedItems.add(product != null ? product.getName() : "Unknown product");
                continue;
            }

            OrderEntity order = new OrderEntity();
            order.setProduct(product);
            order.setBuyer(buyer);
            order.setQuantityPurchased(item.getQuantity());
            order.setTotalPrice(product.getPrice() * item.getQuantity());
            order.setPurchaseDate(LocalDateTime.now());
            order.setSellerCompany(product.getCompany());

            orderService.save(order);

            // Decrease stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
        }

        // Clear the cart in session after purchase
        session.removeAttribute("cart");

        if (!failedItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Failed to purchase: " + String.join(", ", failedItems));
        } else {
            redirectAttributes.addFlashAttribute("message", "Purchase successful for all cart items.");
        }

        return "redirect:/orders/my-orders";
    }
}