package com.ead.course.exception;

import com.ead.course.dtos.ResponseDTO;
import com.ead.course.exception.notfound.NotFoundException;
import com.ead.course.exception.validation.AlreadyExistsException;
import com.ead.course.validation.ValidationMessage;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return ResponseDTO.notFound(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExists(AlreadyExistsException ex) {
        return ResponseDTO.conflictError(ex.getMessage());
    }

    // Handles when bean validation fails. Ex.: @NotBlank, @Min(18), @Email, @Size
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        return ResponseDTO.validationError("Field error", errors);
    }

    // Handles when field type fails in Objects
    // Ex.: UserFilter filter, UserRequest request, UserDTO dto
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String message = error.getDefaultMessage();

                    if (error.getCode() != null && error.getCode().contains("typeMismatch")) {
                        Class<?> fieldType = ex.getBindingResult().getFieldType(error.getField());
                        message = ValidationMessage.getInvalid(fieldType, error.getRejectedValue());
                    }

                    errors.put(error.getField(), message);
                });

        return ResponseDTO.validationError("Field error", errors);
    }

    // Handles when field type fails in Arguments
    // Ex.: @RequestParam UserStatusEnum status, @PathVariable UUID id, @RequestParam Integer age
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        String message = ValidationMessage.getInvalid(requiredType, ex.getValue());

        Map<String, String> errors = Map.of(ex.getName(), message);

        return ResponseDTO.validationError("Field error", errors);
    }

    // Handles when field type or JSON format is invalid in @RequestBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormat = (InvalidFormatException) cause;

            String fieldName = invalidFormat.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            Class<?> targetType = invalidFormat.getTargetType();
            String message = ValidationMessage.getInvalid(targetType, invalidFormat.getValue());

            Map<String, String> errors = new HashMap<>();
            errors.put(fieldName, message);
            return ResponseDTO.validationError("Field error", errors);
        }

        return ResponseDTO.badRequest("Malformed JSON");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception ex) {
        // NEVER return ex.getMessage() in production because sensitive data may be leaked
        // always return a generic message for client
        // enable logs in production only
        // log.error("unexpected internal error", ex);
        System.out.println(ex.getMessage());

        return ResponseDTO.internalError("Unexpected internal error");
    }
}
