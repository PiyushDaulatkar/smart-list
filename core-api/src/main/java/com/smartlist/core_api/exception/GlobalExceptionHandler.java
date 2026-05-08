package com.smartlist.core_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {

        ErrorType errorType = ex.getErrorType();

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                // INSIGHT: INSTANT gives UTC Timestamp.
                Instant.now(),
                errorType.getStatus().value(),
                errorType.getStatus().getReasonPhrase(),
                errorType.getCode(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorType.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        ErrorType errorType = ErrorType.VALIDATION_ERROR;

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                Instant.now(),
                errorType.getStatus().value(),
                errorType.getStatus().getReasonPhrase(),
                errorType.getCode(),
                "Invalid request body",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorType errorType = ErrorType.INTERNAL_SERVER_ERROR;

        ApiErrorResponse error = new ApiErrorResponse(
                Instant.now(),
                errorType.getStatus().value(),
                errorType.getStatus().getReasonPhrase(),
                errorType.getCode(),
                "Something went wrong",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
