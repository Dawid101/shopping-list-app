package com.example.shoppinglist.shopping_list_app.repository;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Transactional
    void deleteProductByName(String name);
}
