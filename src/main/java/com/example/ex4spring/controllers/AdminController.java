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


/**
 * Controller responsible for managing the admin.
 */
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

    /**
     * Displays a list of all products for administrative management.
     * Accessible only to authenticated admins.
     *
     * @param principal the currently logged-in admin
     * @param model the model to hold the product list
     * @return the view for managing company products
     */
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

    /**
     * Displays all orders in the system.
     * Accessible only to authenticated admins.
     *
     * @param principal the currently logged-in admin
     * @param message optional message to display in the view
     * @param model the model to hold the order list and message
     * @return the view showing all orders
     */
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

    /**
     * Displays a list of all users for admin management.
     *
     * @param principal the currently logged-in admin
     * @param model the model to hold the user list
     * @return the view for managing users
     */
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

    /**
     * Enables or disables access for a user (locks or unlocks the account).
     *
     * @param id the ID of the user to modify
     * @param accessLocked true to lock access, false to unlock
     * @param redirectAttributes used to pass error messages between redirects
     * @return redirection to the user management page
     */
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