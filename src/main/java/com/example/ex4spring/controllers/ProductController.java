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
import java.util.List;

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

    @GetMapping("/management")
    public String showProductManagementPage() {
        return "/products-management";
    }

    // Show product creation form
    @GetMapping("/add")
    public String showCreateProductForm() {
        return "create-product";
    }

    @GetMapping("")
    public String showProductsPage(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String name,
            Model model) {

        List<ProductEntity> products;

        if (category == null && (name == null || name.trim().isEmpty())) {
            products = productService.findAll();
        } else if (category != null && (name == null || name.trim().isEmpty())) {
            products = productService.findByCategory(category);
        } else if (category == null && name != null && !name.trim().isEmpty()) {
            products = productService.findByName(name.trim());
        } else {
            products = productService.findByCategoryAndName(category, name.trim());
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", List.of(Category.values())); // if using enum
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchName", name);

        return "products";
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

        Long companyId = company.getId();

        model.addAttribute("products", productService.findByCompanyId(companyId));
        return "company-products"; // The page that shows the products list
    }

    @PostMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id, Principal principal) {
        ProductEntity product = productService.findById(id).orElseThrow();

        // if (product.getCompany().getEmail().equals(principal.getName())) {
            productService.softDeleteById(id);
        // }

        return "redirect:/products/my";
    }
}