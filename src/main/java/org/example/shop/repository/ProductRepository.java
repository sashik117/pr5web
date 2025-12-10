package org.example.shop.repository;

import org.example.shop.model.Product;
import org.example.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Повертає всі продукти з певної категорії
    List<Product> findAllByCategory(Category category);

    // Пошук продуктів за назвою (ігноруючи регістр)
    List<Product> findByNameContainingIgnoreCase(String name);
}
