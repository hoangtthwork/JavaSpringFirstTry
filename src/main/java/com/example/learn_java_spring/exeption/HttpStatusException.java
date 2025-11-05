package com.example.learn_java_spring.exeption;

import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    private final HttpStatus httpStatus; // 404 / 401 / 400...
    private final String errorCode;      // mã nội bộ bạn define (VD: APP_FORM_NOT_FOUND)

    public HttpStatusException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getErrorCode() { return errorCode; }
}