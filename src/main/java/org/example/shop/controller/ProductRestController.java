package org.example.shop.controller;

import org.example.shop.model.Product;
import org.example.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // GET - список продуктів з фільтром та пагінацією
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Product> products = productService.findAll();

        if (name != null && !name.isBlank()) {
            products = productService.searchByName(name);
        }
        if (minPrice != null) {
            products.removeIf(p -> p.getPrice() < minPrice);
        }
        if (maxPrice != null) {
            products.removeIf(p -> p.getPrice() > maxPrice);
        }

        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No products found");
        }

        int fromIndex = Math.min(page * size, products.size());
        int toIndex = Math.min(fromIndex + size, products.size());
        return products.subList(fromIndex, toIndex);
    }

    // GET - отримати продукт за id
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // POST - створення продукту
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,
                    "ID must not be set for new product");
        }
        return productService.save(product);
    }

    // PUT - оновлення продукту
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Product product) {
        if (!id.equals(product.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "ID mismatch");
        }
        productService.save(product);
    }

    // DELETE - видалення продукту
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productService.deleteById(product.getId());
    }
}
