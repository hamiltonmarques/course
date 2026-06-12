package com.ead.course.exception;

import com.ead.course.dtos.ResponseDTO;
import com.ead.course.exception.business.BusinessException;
import com.ead.course.exception.notfound.NotFoundException;
import com.ead.course.exception.validation.AlreadyExistsException;
import com.ead.course.validation.ValidationMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return ResponseDTO.badRequest(ex.getMessage());
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<?> handleExternalApi(ExternalApiException ex) {
        log.error("ExternalApiException Origin: {}, Status: {}, Message: {}",
                ex.getOriginApi(), ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getApiResponse());
    }

    @ExceptionHandler(ExternalApiUnavailableException.class)
    public ResponseEntity<?> handleExternalApiUnavailable(ExternalApiUnavailableException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseDTO.serviceUnavailable(ex.getMessage());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<?> handleMissingPathVariable(MissingPathVariableException ex) {
        Map<String, String> errors = Collections.singletonMap(ex.getVariableName(), "variable is required");
        return ResponseDTO.validationError("Path error", errors);
    }

    // Handles Bean validation (@NotBlank, @Min, etc.) in @RequestBody
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

    // Handles in complex Objects (AnyFilter filter, AnyDTO dto, etc.)
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

    // Handles in Arguments (@RequestParam, @PathVariable, etc.)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        String message = ValidationMessage.getInvalid(requiredType, ex.getValue());

        Map<String, String> errors = Collections.singletonMap(ex.getName(), message);

        return ResponseDTO.validationError("Field error", errors);
    }

    // Handles field type or JSON format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;

            String fieldName = extractFieldName(mie);
            Class<?> targetType = mie.getTargetType();

            Object rejectedValue = extractRejectedValue(mie);
            String message = ValidationMessage.getInvalid(targetType, rejectedValue);

            return ResponseDTO.validationError("Field error", Collections.singletonMap(fieldName, message));
        }

        return ResponseDTO.badRequest("Malformed JSON");
    }

    private Object extractRejectedValue(MismatchedInputException ex) {
        if (ex instanceof InvalidFormatException) {
            return ((InvalidFormatException) ex).getValue();
        }

        if (ex.getProcessor() instanceof JsonParser) {
            try {
                return ((JsonParser) ex.getProcessor()).getText();
            } catch (Exception e) {
                return "invalid format";
            }
        }

        return "invalid format";
    }

    private String extractFieldName(MismatchedInputException ex) {
        if (ex.getPath() == null || ex.getPath().isEmpty()) {
            return "payload";
        }
        return ex.getPath().stream()
                .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                .collect(Collectors.joining("."))
                .replace(".[", "[");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception ex) {
        // NEVER return ex.getMessage() in production because sensitive data may be leaked
        // always return a generic message for client
        // enable logs in production only
        // log.error("unexpected internal error", ex);
        log.error(ex.getClass().getName());
        log.error(ex.getMessage());

        return ResponseDTO.internalError("Unexpected internal error");
    }
}
