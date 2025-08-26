package com.example.shoppinglist.shopping_list_app.service;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(String name) {
        productRepository.deleteProductByName(name);
    }
}
