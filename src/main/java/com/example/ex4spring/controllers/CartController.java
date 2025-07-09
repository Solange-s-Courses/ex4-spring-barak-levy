package com.example.ex4spring.controllers;

import com.example.ex4spring.model.CartItem;
import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);
        double total = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {

        ProductEntity product = productService.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/product-page/" + productId + "?error=Product not found";
        }

        List<CartItem> cart = getCart(session);

        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(
                    product.getId(),
                    product.getName(),
                    quantity,
                    product.getPrice(),
                    product.getQuantity()
            );
            cart.add(newItem);
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProductId().equals(productId));
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.clear();
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 HttpSession session,
                                 Model model) {
        if (quantity < 1) {
            model.addAttribute("error", "Quantity must be at least 1.");
            return "redirect:/cart";
        }

        ProductEntity product = productService.findById(productId).orElse(null);
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "redirect:/cart";
        }

        if (quantity > product.getQuantity()) {
            session.setAttribute("error", "Only " + product.getQuantity() + " units available for " + product.getName());
            return "redirect:/cart";
        }

        List<CartItem> cart = getCart(session);
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute("cart", cart);
        session.removeAttribute("error");
        return "redirect:/cart";
    }

    // Helper method to get cart from session
    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/confirm")
    public String showConfirmCartPage(Model model, HttpSession session) {
        List<CartItem> cartItems = getCart(session);
        double total = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart-confirm-buy";
    }
}