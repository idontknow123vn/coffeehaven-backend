package com.altair12d.coffeehaven.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.altair12d.coffeehaven.enums.OtpPurpose;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> expiryStorage = new HashMap<>();

    private String buildKey(String email, OtpPurpose purpose) {
        return email + "_" + purpose.name();
    }

    public String generateOtp(String email, OtpPurpose purpose) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        String key = buildKey(email, purpose);
        otpStorage.put(key, otp);
        expiryStorage.put(key, LocalDateTime.now().plusMinutes(5));
        return otp;
    }

    public boolean validateOtp(String email, String otpInput, OtpPurpose purpose) {
        String key = buildKey(email, purpose);
        if (!otpStorage.containsKey(key)) return false;

        String validOtp = otpStorage.get(key);
        LocalDateTime expiry = expiryStorage.get(key);

        if (LocalDateTime.now().isAfter(expiry)) {
            otpStorage.remove(key);
            expiryStorage.remove(key);
            return false;
        }

        boolean match = validOtp.equals(otpInput);
        if (match) {
            otpStorage.remove(key);
            expiryStorage.remove(key);
        }
        return match;
    }
}
