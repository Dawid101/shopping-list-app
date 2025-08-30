package com.example.shoppinglist.shopping_list_app.controller;

import com.example.shoppinglist.shopping_list_app.dto.ProductDtoReq;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import com.example.shoppinglist.shopping_list_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDtoResp> addProduct(@RequestBody ProductDtoReq product) {
        ProductDtoResp savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDtoResp>> getListOfProduct() {
        List<ProductDtoResp> listOfProducts = productService.getAllProducts();
        return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ProductDtoResp> getProduct(@PathVariable String id){
        ProductDtoResp productDtoResp = productService.getProductDto(id);
        return ResponseEntity.ok(productDtoResp);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted");
    }
}
