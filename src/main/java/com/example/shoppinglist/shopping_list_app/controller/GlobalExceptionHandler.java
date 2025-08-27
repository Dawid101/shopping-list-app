package com.example.shoppinglist.shopping_list_app.controller;

import com.example.shoppinglist.shopping_list_app.dto.ErrorResponse;
import com.example.shoppinglist.shopping_list_app.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception){
        ErrorResponse productNotFound = new ErrorResponse("PRODUCT_NOT_FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(productNotFound, HttpStatus.NOT_FOUND);
    }
}
