package com.example.payment.exceptions;

import com.example.payment.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
        ex.printStackTrace(); // optional: log full stacktrace
        return new ResponseEntity<>(ApiResponse.error("Internal Server Error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ApiResponse.error("Invalid input: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Handle HttpClientErrorException (like 4xx errors from Khalti)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpClientError(HttpClientErrorException ex) {
        String errorMessage = ex.getResponseBodyAsString();
        return new ResponseEntity<>(ApiResponse.error("Khalti API Error: " + errorMessage), ex.getStatusCode());
    }

    // Handle HttpServerErrorException (like 5xx errors from Khalti)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpServerError(HttpServerErrorException ex) {
        String errorMessage = ex.getResponseBodyAsString();
        return new ResponseEntity<>(ApiResponse.error("Khalti Server Error: " + errorMessage), ex.getStatusCode());
    }

    // Handle ResourceNotFoundException (custom)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
