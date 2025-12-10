package org.example.shop.service;

import org.example.shop.model.Category;
import org.example.shop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    public List<Category> findAll() { return categoryRepository.findAll(); }
    public Category save(Category category) { return categoryRepository.save(category); }
    public Category findById(Long id) { return categoryRepository.findById(id).orElse(null); }
}