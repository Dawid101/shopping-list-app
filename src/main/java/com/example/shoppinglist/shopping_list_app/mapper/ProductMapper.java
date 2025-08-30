package com.example.shoppinglist.shopping_list_app.mapper;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoReq;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public Product toEntity(ProductDtoReq productDtoReq) {
        Product product = new Product();
        product.setName(productDtoReq.name());
        product.setQuantity(productDtoReq.quantity());
        return product;
    }

    public ProductDtoResp toProductDtoResp(Product product) {
        String name;
        int quantity;
        int placeOnTheList;

        name = product.getName();
        quantity = product.getQuantity();
        placeOnTheList = product.getPlaceOnTheList();

        return new ProductDtoResp(name, quantity, placeOnTheList);
    }

    public List<ProductDtoResp> toProductDtoRespList(List<Product> products){
        return products.stream()
                .map(this::toProductDtoResp)
                .toList();
    }
}
