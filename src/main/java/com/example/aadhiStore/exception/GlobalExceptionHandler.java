package com.example.aadhiStore.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StockExceptions.class)
    public ResponseEntity<String> handleStockExceptions(StockExceptions e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
