package com.example.ex4spring.controllers;

import com.example.ex4spring.model.*;
import com.example.ex4spring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller responsible for handling all product related requests.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final CompanyService companyService;
    private final UserService userService;
    private final AdminService adminService;
    private final OrderService orderService;

    /**
     * Constructor-based dependency injection for required services.
     *
     * @param productService the product service
     * @param companyService the company service
     * @param reviewService the review service
     * @param userService the user service
     * @param adminService the admin service
     * @param orderService the order service
     */
    @Autowired
    public ProductController(ProductService productService, CompanyService companyService,
                             ReviewService reviewService, UserService userService,
                             AdminService adminService, OrderService orderService) {
        this.productService = productService;
        this.companyService = companyService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.adminService = adminService;
        this.orderService = orderService;
    }

    /**
     * Displays the product management page for companies/admins.
     *
     * @param model the model for view attributes
     * @return the product management view
     */
    @GetMapping("/management")
    public String showProductManagementPage(Model model) {
        model.addAttribute("isEdit", false);
        model.addAttribute("categories", Category.values());
        return "/products-management";
    }

    /**
     * Shows the form for creating a new product.
     *
     * @param model the model for view attributes
     * @return the create product view
     */
    @GetMapping("/add")
    public String showCreateProductForm(Model model) {
        model.addAttribute("isEdit", false);
        return "create-product";
    }

    /**
     * Displays a list of products optionally filtered by category and name.
     *
     * @param category optional product category to filter
     * @param name optional product name (or partial) to filter
     * @param model the model for view attributes
     * @return the products catalog view
     */
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
        model.addAttribute("categories", List.of(Category.values()));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchName", name);

        return "products";
    }

    /**
     * Handles creation of a new product by a company user.
     *
     * @param productName name of the new product
     * @param category category of the new product
     * @param price price of the new product
     * @param quantity available quantity of the product
     * @param description description of the product
     * @param imageUrl URL for the product image
     * @param principal security principal representing the logged-in user
     * @param model the model for view attributes
     * @return redirection to the logged-in company's product list on success, or error view otherwise
     */
    @PostMapping("/add")
    public String createProduct(@RequestParam String productName,
                                @RequestParam Category category,
                                @RequestParam double price,
                                @RequestParam int quantity,
                                @RequestParam String description,
                                @RequestParam String imageUrl,
                                Principal principal,
                                Model model) {

        CompanyEntity company = companyService.findByEmail(principal.getName())
                .orElse(null);

        if (company == null) {
            model.addAttribute("error", "Only company users can create products.");
            return "products";
        }

        model.addAttribute("categories", Category.values());

        ProductEntity product = new ProductEntity();
        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setCompany(company);
        product.setImageUrl(imageUrl);

        productService.save(product);

        return "redirect:/products/my-products";
    }

    /**
     * Lists all products belonging to the currently logged-in company.
     *
     * @param principal security principal representing the logged-in user
     * @param model the model for view attributes
     * @return the view showing the company's products or error view if unauthorized
     */
    @GetMapping("/my-products")
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

    /**
     * Soft deletes a product by its ID, authorized for the owning company or an admin.
     *
     * @param id product ID to delete
     * @param principal security principal representing the logged-in user
     * @return redirection to appropriate management page after deletion
     */
    @PostMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id, Principal principal) {
        ProductEntity product = productService.findById(id).orElseThrow();
        String email = principal.getName();

        boolean isCompanyOwner = product.getCompany().getEmail().equals(email);
        boolean isAdmin = adminService.findByEmail(email).isPresent();

        if (isCompanyOwner || isAdmin) {
            productService.softDeleteById(id);
        }

        // Redirect based on who deleted:
        if (isAdmin) {
            return "redirect:/admin/products-management";
        } else {
            return "redirect:/products/my-products";
        }
    }

    /**
     * Displays the detailed product page including reviews, average rating, and review submission ability.
     *
     * @param id product ID
     * @param error optional error message to display
     * @param principal security principal representing the logged-in user (nullable)
     * @param model the model for view attributes
     * @return the detailed product view or error view if product not found
     */
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

    /**
     * Adds a new review or edits an existing review for a product by the logged-in user.
     *
     * @param id product ID being reviewed
     * @param rating numeric rating given by the user
     * @param comment textual comment given by the user
     * @param principal security principal representing the logged-in user
     * @param model the model for view attributes
     * @return redirection back to the product details page on success, or error view on failure
     */
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

    /**
     * Shows the form for editing an existing product, accessible only by the owning company.
     *
     * @param id product ID to edit
     * @param principal security principal representing the logged-in user
     * @param model the model for view attributes
     * @return the product management view pre-filled for editing, or error view if unauthorized or not found
     */
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
        model.addAttribute("categories", Category.values());
        return "products-management";
    }

    /**
     * Handles updating an existing product's details, authorized only for the owning company.
     *
     * @param id product ID to update
     * @param productName updated product name
     * @param category updated category
     * @param price updated price
     * @param quantity updated quantity
     * @param description updated description
     * @param imageUrl updated image URL
     * @param principal security principal representing the logged-in user
     * @param model the model for view attributes
     * @return redirection to the company's product list on success, or error view otherwise
     */
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String productName,
                                @RequestParam Category category,
                                @RequestParam double price,
                                @RequestParam int quantity,
                                @RequestParam String description,
                                @RequestParam String imageUrl,
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

        model.addAttribute("categories", Category.values());

        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setImageUrl(imageUrl);

        productService.save(product);

        return "redirect:/products/my-products";
    }
}