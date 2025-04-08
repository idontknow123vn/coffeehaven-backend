package com.altair12d.coffeehaven.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altair12d.coffeehaven.dto.request.CreateEmployeeRequest;
import com.altair12d.coffeehaven.dto.request.LoginRequest;
import com.altair12d.coffeehaven.dto.request.OtpRequest;
import com.altair12d.coffeehaven.enums.OtpPurpose;
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
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest request) {
        String email = request.getEmail();
        OtpPurpose purpose = OtpPurpose.valueOf(request.getPurpose().toUpperCase());
        String otp = otpService.generateOtp(request.getEmail(), purpose);
        emailService.sendOtpEmail(email, otp, purpose);
        return ResponseEntity.ok("OTP sent to email.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateEmployeeRequest request) {
        // String response = authService.register(request);
        String email = request.getEmail();
        String otp = request.getOtp();
        OtpPurpose purpose = OtpPurpose.REGISTER;
        if (!otpService.validateOtp(email, otp, purpose)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
        }
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
