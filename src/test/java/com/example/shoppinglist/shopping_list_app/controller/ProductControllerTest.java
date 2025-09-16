package com.example.shoppinglist.shopping_list_app.controller;


import com.example.shoppinglist.shopping_list_app.dto.ProductDtoResp;
import com.example.shoppinglist.shopping_list_app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldReturnListOfDtoRespProducts() throws Exception {
        List<ProductDtoResp> productDtoResp = List.of(
                new ProductDtoResp("kawa", 1, 1),
                new ProductDtoResp("mleko", 2, 2)
        );

        when(productService.getAllProducts()).thenReturn(productDtoResp);

        mockMvc.perform(get("/api/products/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("kawa")))
                .andExpect(jsonPath("$[0].quantity", is(1)))
                .andExpect(jsonPath("$[1].name", is("mleko")))
                .andExpect(jsonPath("$[1].quantity", is(2)));
    }
}