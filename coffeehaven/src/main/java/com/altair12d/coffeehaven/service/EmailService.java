package com.altair12d.coffeehaven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.altair12d.coffeehaven.enums.OtpPurpose;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp, OtpPurpose purpose) {  
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
    
        switch (purpose) {
            case REGISTER:
                message.setSubject("OTP for Account Registration");
                message.setText("Welcome! Your OTP for registration is: " + otp + "\nValid for 5 minutes.");
                break;
            case FORGOT_PASSWORD:
                message.setSubject("OTP for Password Reset");
                message.setText("You requested to reset your password. Your OTP is: " + otp + "\nValid for 5 minutes.");
                break;
        }
    
        mailSender.send(message);
    }
}
