package com.ead.course.dtos;

import com.ead.course.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseDTO {

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.CREATED.value(), message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), message, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflictError(String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), message, null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalError(String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> validationError(String message, Map<String, String> errors) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}