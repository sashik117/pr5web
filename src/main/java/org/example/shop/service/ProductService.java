package org.example.shop.service;

import org.example.shop.model.Category;
import org.example.shop.model.Product;
import org.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Повертає всі продукти
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Фільтрація по категорії
    public List<Product> findByCategory(Category category) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getCategory() != null && p.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Пошук по назві (ігноруючи регістр)
    public List<Product> searchByName(String name) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Зберігає або оновлює продукт
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Пошук за id
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Видалення за id
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
