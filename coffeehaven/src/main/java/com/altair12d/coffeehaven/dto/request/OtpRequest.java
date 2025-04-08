package com.altair12d.coffeehaven.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import lombok.Getter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OtpRequest {
    private String purpose; // OTPPurpose enum value as a string
    private String email; // Email address for OTP verification
}
