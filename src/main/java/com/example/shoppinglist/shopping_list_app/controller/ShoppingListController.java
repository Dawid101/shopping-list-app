package com.example.shoppinglist.shopping_list_app.controller;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getListOfProduct() {
        List<Product> listOfProducts = productService.getAllProducts();
        return ResponseEntity.ok(listOfProducts);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
        try {
            productService.deleteProduct(product.getName());
            return ResponseEntity.ok().body("Product deleted");
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
