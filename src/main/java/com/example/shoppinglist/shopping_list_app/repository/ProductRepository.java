package com.example.shoppinglist.shopping_list_app.repository;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
