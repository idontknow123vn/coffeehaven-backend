package com.altair12d.coffeehaven.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateEmployeeRequest {
    private String email; // Email address for authentication
    // @Size(min = 6, message = "INVALID_PASSWORD")
    private String password; // Password for authentication
    // @Size(min = 2, message = "USERNAME_INVALID")
    private String name; // Name of the user (for registration)
    private String phoneNumber; // Phone number of the user (for registration)
    private String role; // Role of the user (for registration)
    private int branchId; // Branch of the user (for registration)
    private String otp;
}
