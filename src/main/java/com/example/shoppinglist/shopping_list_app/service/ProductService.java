package com.example.shoppinglist.shopping_list_app.service;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoReq;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import com.example.shoppinglist.shopping_list_app.mapper.ProductMapper;
import com.example.shoppinglist.shopping_list_app.exception.ProductNotFoundException;
import com.example.shoppinglist.shopping_list_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDtoResp addProduct(ProductDtoReq productDtoReq) {
        Product product = productMapper.toEntity(productDtoReq);
        Integer maxPosition = productRepository.findMaxPosition();
        int newPosition = (maxPosition != null) ? maxPosition + 1 : 1;
        product.setPositionOnTheList(newPosition);
        productRepository.save(product);
        return productMapper.toProductDtoResp(product);
    }

    public List<ProductDtoResp> getAllProducts() {
        List<Product> products = productRepository.findAllByOrderByPositionOnTheListAsc();
        return productMapper.toProductDtoRespList(products);
    }

    public void deleteProductById(String id) {
        Product productToDelete = getProductById(id);
        int deletedPosition = productToDelete.getPositionOnTheList();
        productRepository.deleteById(id);
        productRepository.decrementPositionsAbove(deletedPosition);
    }

    public ProductDtoResp getProductDto(String id) {
        Product product = getProductById(id);
        return productMapper.toProductDtoResp(product);
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }

}
