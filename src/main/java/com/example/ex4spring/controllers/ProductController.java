package com.example.ex4spring.controllers;

import com.example.ex4spring.model.Category;
import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.model.ProductEntity;
import com.example.ex4spring.services.CompanyService;
import com.example.ex4spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CompanyService companyService;

    @Autowired
    public ProductController(ProductService productService, CompanyService companyService) {
        this.productService = productService;
        this.companyService = companyService;
    }

    // Show product creation form
    @GetMapping("/add")
    public String showCreateProductForm() {
        return "create-product"; // Thymeleaf template name
    }

    // Handle product creation
    @PostMapping("/add")
    public String createProduct(@RequestParam String productName,
                                @RequestParam Category category,
                                @RequestParam double price,
                                @RequestParam int quantity,
                                @RequestParam String description,
                                Principal principal,
                                Model model) {

        // Find the company based on their email
        CompanyEntity company = companyService.findByEmail(principal.getName())
                .orElse(null);

        if (company == null) {
            model.addAttribute("error", "Only company users can create products.");
            return "create-product";
        }

        ProductEntity product = new ProductEntity();
        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setCompany(company);

        productService.save(product);

        return "redirect:/products/my"; // redirect to list of the company's products
    }

    // List products of the logged-in company
    @GetMapping("/my")
    public String listCompanyProducts(Principal principal, Model model) {
        CompanyEntity company = companyService.findByEmail(principal.getName())
                .orElse(null);

        if (company == null) {
            model.addAttribute("error", "Unauthorized access.");
            return "error";
        }

        model.addAttribute("products", productService.findByCompany(company));
        return "company-products"; // template that shows the product list
    }
}