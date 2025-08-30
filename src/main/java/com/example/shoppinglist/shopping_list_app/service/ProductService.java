package com.example.shoppinglist.shopping_list_app.service;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoReq;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import com.example.shoppinglist.shopping_list_app.mapper.ProductMapper;
import com.example.shoppinglist.shopping_list_app.exception.ProductNotFoundException;
import com.example.shoppinglist.shopping_list_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDtoResp addProduct(ProductDtoReq productDtoReq) {
        Product product = productMapper.toEntity(productDtoReq);
        if (getAllProducts() == null) {
            product.setPlaceOnTheList(1);
        } else {
            product.setPlaceOnTheList(getAllProducts().size() + 1);
        }
        productRepository.save(product);
        return productMapper.toProductDtoResp(product);
    }

    public List<ProductDtoResp> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductDtoRespList(products);
    }

    public void deleteProductById(String id) {
        Product product = getProductById(id);
        if (product != null) {
            List<Product> products = productRepository.findAll();
            products.forEach(productOnTheList -> {
                if(productOnTheList.getPlaceOnTheList() != 1){
                    productOnTheList.setPlaceOnTheList(productOnTheList.getPlaceOnTheList() - 1);
                }
            });
            productRepository.deleteById(id);
        }
    }

    public ProductDtoResp getProductDto(String id) {
        Product product = getProductById(id);
        return productMapper.toProductDtoResp(product);
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }

}
