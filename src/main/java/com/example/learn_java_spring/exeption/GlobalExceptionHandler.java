package com.example.learn_java_spring.exeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<Object> handleHttpStatusException(HttpStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("message", ex.getMessage());
        body.put("errorCode", ex.getErrorCode());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

//     @Valid @RequestBody (Bean Validation) – lỗi field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(Map.of("message", "Dữ liệu không hợp lệ", "errors", errors));
    }

    // fallback cho các lỗi không xác định
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("message", "Đã xảy ra lỗi trong quá trình xử lý");
        body.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}