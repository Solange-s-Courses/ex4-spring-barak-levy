package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CompanyService companyService;
    private final UserService userService;
    private final BaseUserService baseUserService;
    private final OrderService orderService;

    @Autowired
    public AdminController(AdminService adminService, ProductService productService, CompanyService companyService,
                             UserService userService, BaseUserService baseUserService,
                             OrderService orderService) {
        this.adminService = adminService;
        this.productService = productService;
        this.companyService = companyService;
        this.userService = userService;
        this.baseUserService = baseUserService;
        this.orderService = orderService;
    }

    // List of all products
    @GetMapping("/products-management")
    public String listProducts(Principal principal, Model model) {
        AdminEntity admin = adminService.findByEmail(principal.getName())
                .orElse(null);

        if (admin == null) {
            model.addAttribute("error", "Unauthorized access.");
            return "error";
        }

        model.addAttribute("products", productService.findAll());
        return "company-products";
    }

    // View all the orders
    @GetMapping("/all-orders")
    public String viewOrders(Principal principal,
                             @RequestParam(value = "message", required = false) String message,
                             Model model) {

        AdminEntity admin = adminService.findByEmail(principal.getName()).orElse(null);

        if (admin == null) {
            model.addAttribute("error", "Unauthorized access.");
            return "error";
        }

        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }

        List<OrderEntity> orders = new ArrayList<>();

        orders = orderService.findAll();

        model.addAttribute("orders", orders);

        return "orders";
    }

    // List of all the users
    @GetMapping("/users-management")
    public String listUsers(Principal principal, Model model) {
        AdminEntity admin = adminService.findByEmail(principal.getName())
                .orElse(null);

        if (admin == null) {
            model.addAttribute("error", "Unauthorized access.");
            return "error";
        }

        List<BaseUser> allUsers = new ArrayList<>();
        allUsers.addAll(userService.findAll());
        allUsers.addAll(companyService.findAll());

        model.addAttribute("users", allUsers);
        return "users-management";
    }

    @PostMapping("/toggle-user-access/{id}")
    public String toggleAccess(@PathVariable Long id,
                               @RequestParam boolean accessLocked,
                               RedirectAttributes redirectAttributes) {
        try {
            baseUserService.toggleAccess(id, accessLocked);
            return "redirect:/admin/users-management";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user access.");
            return "redirect:/admin/users-management";
        }
    }
}