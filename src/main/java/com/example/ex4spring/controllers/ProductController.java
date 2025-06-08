package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.*;
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
    private final ReviewService reviewService;
    private final CompanyService companyService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProductController(ProductService productService, CompanyService companyService,
                             ReviewService reviewService, UserService userService,
                             OrderService orderService) {
        this.productService = productService;
        this.companyService = companyService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/management")
    public String showProductManagementPage(Model model) {
        model.addAttribute("isEdit", false);
        return "/products-management";
    }

    // Show product creation form
    @GetMapping("/add")
    public String showCreateProductForm(Model model) {
        model.addAttribute("isEdit", false);
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
            return "products";
        }

        ProductEntity product = new ProductEntity();
        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setCompany(company);

        productService.save(product);

        return "redirect:/products/my";
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

    @GetMapping("/product-page/{id}")
    public String viewProduct(@PathVariable Long id,
                              @RequestParam(value = "error", required = false) String error,
                              Principal principal, Model model) {
        ProductEntity product = productService.findById(id).orElse(null);
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "error";
        }

        if (error != null) {
            model.addAttribute("error", error);
        }

        List<ReviewEntity> reviews = reviewService.findByProductId(id);
        model.addAttribute("reviews", reviews);

        double averageRating = 0;
        if (!reviews.isEmpty()) {
            averageRating = reviews.stream()
                    .mapToInt(ReviewEntity::getRating)
                    .average()
                    .orElse(0);
        }
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("product", product);

        boolean canReview = false;

        // We are using a list in case for some reason there is more than 1 reviews from a user
        // than there won't be a problem loading more than 1. There should be only 1 review for
        // a product per user.
        List<ReviewEntity> userReviews = null;

        ReviewEntity userReview = null;

        if (principal != null) {
            UserEntity user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                canReview = orderService.hasUserPurchasedProduct(user.getId(), id);
                userReviews = reviewService.findAllByReviewerIdAndProductId(user.getId(), id);

                if (userReviews != null && !userReviews.isEmpty()) {
                    userReview = userReviews.get(0);
                }
            }
        }

        model.addAttribute("canReview", canReview);
        model.addAttribute("userReview", userReview);

        return "product-details";
    }

    @PostMapping("/product-page/{id}/review")
    public String addOrEditReview(@PathVariable Long id,
                                  @RequestParam int rating,
                                  @RequestParam String comment,
                                  Principal principal,
                                  Model model) {
        if (principal == null) {
            model.addAttribute("error", "You must be logged in to add a review.");
            return "error";
        }

        UserEntity user = userService.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        if (!orderService.hasUserPurchasedProduct(user.getId(), id)) {
            model.addAttribute("error", "You can only review products you have purchased.");
            return "error";
        }

        ProductEntity product = productService.findById(id).orElse(null);
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "error";
        }

        // Check if review already exists
        List<ReviewEntity> userReviews = reviewService.findAllByReviewerIdAndProductId(user.getId(), id);

        ReviewEntity review;
        if (!userReviews.isEmpty()) {
            // Edit existing review
            review = userReviews.get(0);
            review.setRating(rating);
            review.setComment(comment);
        } else {
            // Create new review
            review = new ReviewEntity();
            review.setProduct(product);
            review.setReviewer(user);
            review.setRating(rating);
            review.setComment(comment);
        }

        reviewService.save(review);
        return "redirect:/products/product-page/" + id;
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Principal principal, Model model) {
        ProductEntity product = productService.findById(id).orElse(null);

        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "error";
        }

        CompanyEntity company = companyService.findByEmail(principal.getName()).orElse(null);
        if (company == null || product.getCompany().getId() != (company.getId())) {
            model.addAttribute("error", "Unauthorized.");
            return "error";
        }

        model.addAttribute("product", product);
        model.addAttribute("isEdit", true);
        return "products-management";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String productName,
                                @RequestParam Category category,
                                @RequestParam double price,
                                @RequestParam int quantity,
                                @RequestParam String description,
                                Principal principal,
                                Model model) {

        ProductEntity product = productService.findById(id).orElse(null);

        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "error";
        }

        CompanyEntity company = companyService.findByEmail(principal.getName()).orElse(null);

        if (company == null || product.getCompany().getId() != (company.getId())) {
            model.addAttribute("error", "Unauthorized to update this product.");
            return "error";
        }

        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);

        productService.save(product);

        return "redirect:/products/my";
    }
}