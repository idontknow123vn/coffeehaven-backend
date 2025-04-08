package com.altair12d.coffeehaven.service;

import java.text.ParseException;

import com.altair12d.coffeehaven.dto.request.CreateEmployeeRequest;
import com.nimbusds.jose.JOSEException;

public interface AuthService {
    String login(String username, String password);
    String register(CreateEmployeeRequest request);
    boolean introspect(String token) throws JOSEException, ParseException;
    String refreshToken(String token);
}
