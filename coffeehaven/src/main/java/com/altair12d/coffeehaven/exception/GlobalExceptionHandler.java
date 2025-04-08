package com.altair12d.coffeehaven.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.altair12d.coffeehaven.common.ApiResponse;

import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(500)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(400)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("An unexpected error occurred")
                .statusCode(500)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(403)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(ex.getErrorCode().getMessage())
                .statusCode(ex.getErrorCode().getCode())
                .build();
        return new ResponseEntity<>(response, ex.getErrorCode().getStatusCode());
    }

    // @ExceptionHandler(value = MethodArgumentNotValidException.class)
    // public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
    //     String enumKey = exception.getFieldError().getDefaultMessage();

    //     ErrorCode errorCode = ErrorCode.INVALID_KEY;
    //     Map<String, Object> attributes = null;
    //     try {
    //         errorCode = ErrorCode.valueOf(enumKey);

    //         var constraintViolation =
    //                 exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

    //         attributes = constraintViolation.getConstraintDescriptor().getAttributes();

    //     } catch (IllegalArgumentException e) {

    //     }

    //     ApiResponse apiResponse = new ApiResponse();

    //     apiResponse.setStatusCode(errorCode.getCode());
    //     apiResponse.setMessage(
    //             Objects.nonNull(attributes)
    //                     ? mapAttribute(errorCode.getMessage(), attributes)
    //                     : errorCode.getMessage());

    //     return ResponseEntity.badRequest().body(apiResponse);
    // }
}
