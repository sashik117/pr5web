package org.example.shop.service;

import org.example.shop.model.Product;
import org.example.shop.model.Category;
import org.example.shop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) { this.productRepository = productRepository; }

    public Page<Product> findAll(Pageable pageable) { return productRepository.findAll(pageable); }
    public Page<Product> findByCategory(Category category, Pageable pageable) { return productRepository.findAllByCategory(category, pageable); }
    public Page<Product> searchByName(String name, Pageable pageable) { return productRepository.findByNameContainingIgnoreCase(name, pageable); }
    public Product save(Product p) { return productRepository.save(p); }
    public Product findById(Long id) { return productRepository.findById(id).orElse(null); }
    public void deleteById(Long id) { productRepository.deleteById(id); }
}