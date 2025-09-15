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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Product product = new Product("Kawa", 5, 1);
        ProductDtoResp expectedResponse = new ProductDtoResp("Kawa", 5, 1);

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.findMaxPosition()).thenReturn(null); // brak produktów
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toProductDtoResp(product)).thenReturn(expectedResponse);

        // When
        ProductDtoResp result = productService.addProduct(request);

        assertEquals(1, result.positionOnTheList());

        // Verify że pozycja została ustawiona
        verify(productRepository).save(argThat(p -> p.getPositionOnTheList() == 1));

    }

    @Test
    void shouldReturnAllDtoResponseProduct() {
        List<Product> products = Arrays.asList(
                new Product("kawa", 1, 1),
                new Product("mleko", 2, 2)
        );
        List<ProductDtoResp> expectedResponse = Arrays.asList(
                new ProductDtoResp("kawa", 1, 1),
                new ProductDtoResp("mleko", 2, 2)
        );

        when(productRepository.findAllByOrderByPositionOnTheListAsc()).thenReturn(products);
        when(productMapper.toProductDtoRespList(any())).thenReturn(expectedResponse);

        List<ProductDtoResp> result = productService.getAllProducts();

        assertEquals(expectedResponse, result);
        verify(productRepository).findAllByOrderByPositionOnTheListAsc();
        verify(productMapper).toProductDtoRespList(products);
    }

    @Test
    void shouldDeleteProductThenDecrementPosistionAbove() {

        Product product = new Product("1", "Kawa", 1, 1);
        int deletedPosition = product.getPositionOnTheList();

        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        productService.deleteProductById("1");


        verify(productRepository).findById("1");
        verify(productRepository).deleteById("1");
        verify(productRepository).decrementPositionsAbove(deletedPosition);
    }

    @Test
    void shouldReturnProductDtoResp(){
        String productId = "1";
        Product product = new Product(productId, "kawa", 1, 1);
        ProductDtoResp expectedProductDto = new ProductDtoResp("kawa",1,1);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toProductDtoResp(product)).thenReturn(expectedProductDto);

        ProductDtoResp result = productService.getProductDto(productId);

        assertEquals(expectedProductDto,result);

        verify(productRepository).findById(productId);
        verify(productMapper).toProductDtoResp(product);
    }
}