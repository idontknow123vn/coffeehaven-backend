package com.altair12d.coffeehaven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được xác minh.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Mã được sử dụng không đúng.", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại.", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên người dùng ít nhất {min} ký tự.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu ít nhất {min} ký tự.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại.", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTED(1007, "Email đã được sử dụng.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Tài khoản và mật khẩu không đúng.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền truy cập.", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1008, "Token đã hết hạn.", HttpStatus.UNAUTHORIZED),
    INVALID_DOB(1008, "Tuổi của bạn phải ít nhất {min} tuổi.", HttpStatus.BAD_REQUEST),
    INACTIVE_USER(1009, "Tài khoản của bạn đã bị khóa.", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}