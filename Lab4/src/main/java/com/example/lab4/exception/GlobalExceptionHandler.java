package com.example.lab4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PetsNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePetsNotFound(PetsNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Some pets not found");
        body.put("missingIds", ex.getMissingIds());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOwnerNotFound(OwnerNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Owner not found");
        body.put("missingOwnerId", ex.getMissingOwnerId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "User not found");
        body.put("missingUserId", ex.getMissingUserId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}