package com.example.shoppinglist.shopping_list_app.service;

import com.example.shoppinglist.shopping_list_app.domain.Product;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoReq;
import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import com.example.shoppinglist.shopping_list_app.mapper.ProductMapper;
import com.example.shoppinglist.shopping_list_app.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;

    @Test
    void shouldAddProductThenReturnDto() {
        ProductDtoReq request = new ProductDtoReq("Kawa", 5);
        Product product = new Product("Kawa", 5,1);
        ProductDtoResp expectedResponse = new ProductDtoResp("Kawa", 5, 1);

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.findMaxPosition()).thenReturn(null); // brak produktów
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toProductDtoResp(product)).thenReturn(expectedResponse);

        // When
        ProductDtoResp result = productService.addProduct(request);

        assertEquals(1,result.positionOnTheList());

        // Verify że pozycja została ustawiona
        verify(productRepository).save(argThat(p -> p.getPositionOnTheList() == 1));

    }
}