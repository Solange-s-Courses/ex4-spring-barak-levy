package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.*;
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

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    private final BaseUserService baseUserService;

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

    // Show confirmation page for a product after user clicks "Buy Now"
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

    // Place the order after confirmation
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

    // View user's orders
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
}