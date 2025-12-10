package org.example.shop.controller;

import org.example.shop.model.Category;
import org.example.shop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }


    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categoryForm", new Category());
        return "categories";
    }


    @PostMapping("/categories")
    public String add(Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }
}