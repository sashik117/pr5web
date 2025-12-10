package org.example.shop.controller;

import org.example.shop.model.Category;
import org.example.shop.model.Product;
import org.example.shop.service.CategoryService;
import org.example.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/", "/products"})
    public String listProducts(Model model,
                               @RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String q) {

        List<Product> products;

        if (q != null && !q.isBlank()) {
            products = productService.searchByName(q);
        } else if (categoryId != null) {
            Category cat = categoryService.findById(categoryId);
            products = productService.findByCategory(cat);
        } else {
            products = productService.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("categoryId", categoryId);
        return "products";
    }

    @GetMapping("/products/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product_form";
    }

    @PostMapping("/products")
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
