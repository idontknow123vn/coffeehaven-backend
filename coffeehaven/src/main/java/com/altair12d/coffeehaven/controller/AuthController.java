package com.altair12d.coffeehaven.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altair12d.coffeehaven.common.ApiResponse;
import com.altair12d.coffeehaven.dto.request.CreateEmployeeRequest;
import com.altair12d.coffeehaven.dto.request.LoginRequest;
import com.altair12d.coffeehaven.dto.request.OtpRequest;
import com.altair12d.coffeehaven.dto.response.AuthResponse;
import com.altair12d.coffeehaven.enums.OtpPurpose;
import com.altair12d.coffeehaven.exception.AppException;
import com.altair12d.coffeehaven.exception.ErrorCode;
import com.altair12d.coffeehaven.service.AuthService;
import com.altair12d.coffeehaven.service.EmailService;
import com.altair12d.coffeehaven.service.OtpService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;
    private final OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestBody OtpRequest request) {
        String email = request.getEmail();
        OtpPurpose purpose = OtpPurpose.valueOf(request.getPurpose().toUpperCase());
        String otp = otpService.generateOtp(request.getEmail(), purpose);
        emailService.sendOtpEmail(email, otp, purpose);
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .message("Đã gửi mã OTP đến email của bạn.")
                .statusCode(200)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody CreateEmployeeRequest request) {
        // String response = authService.register(request);
        String email = request.getEmail();
        String otp = request.getOtp();
        OtpPurpose purpose = OtpPurpose.REGISTER;
        if (!otpService.validateOtp(email, otp, purpose)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        String result = authService.register(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .message("Đăng ký thành công.")
                .statusCode(200)
                .build());
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công.")
                .statusCode(200)
                .data(AuthResponse.builder()
                        .accessToken(token)
                        .build())
                .build());
    }
}
