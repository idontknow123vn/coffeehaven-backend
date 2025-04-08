package com.altair12d.coffeehaven.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.altair12d.coffeehaven.dto.request.CreateEmployeeRequest;
import com.altair12d.coffeehaven.entity.Employee;
import com.altair12d.coffeehaven.repository.EmployeeRepos;
import com.altair12d.coffeehaven.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepos employeeRepos;
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.validDuration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshableDuration}")
    protected long REFRESHABLE_DURATION;

    @Override
    public String login(String username, String password) {
        // TODO Auto-generated method stub
        // Employee employee = employeeRepos.findByEmailAndPasswordHash(username, passwordEncoder.encode(password))
        //         .orElseThrow(() -> new RuntimeException("Tài khoản hoặc mật khẩu không đúng"));
        Employee employee = employeeRepos.findByEmail(username).orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        if (!passwordEncoder.matches(password, employee.getPasswordHash())) {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
        }
        if (employee.getStatus().equals("Inactive")) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }
        employee.setLastLogin(Instant.now());
        employeeRepos.save(employee);
        String token = generateToken(employee);
        return token;
    }

    @Override
    public String register(CreateEmployeeRequest request) {
        // TODO Auto-generated method stub
        
        Employee employee = employeeRepos.findByUsername(request.getName());
        if (employee != null) {
            throw new RuntimeException("Username already exists");
        }
        employee = employeeRepos.findByEmail(request.getEmail()).get();
        if (employee != null) {
            throw new RuntimeException("Email already exists");
        }
        employee = new Employee();
        employee.setUsername(request.getName());
        employee.setName(request.getName());
        employee.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        employee.setEmail(request.getEmail());
        employee.setRole("Counter_Staff");
        employee.setStatus("Active");

        employeeRepos.save(employee);

        return "Đã đăng ký thành công";
    }

    private String generateToken(Employee employee){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Map<String, Object> claims = Map.of("name", employee.getName(), "email",
        employee.getEmail(), "role", employee.getRole());
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(employee.getEmail())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", claims)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new RuntimeException("Expired token");

        return signedJWT;
    }

    public boolean introspect(String token) throws ParseException, JOSEException {
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (RuntimeException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String refreshToken(String token) {
        // TODO Auto-generated method stub
        try {
            SignedJWT signedJWT = verifyToken(token, true);
            String email = signedJWT.getJWTClaimsSet().getSubject();
            Employee employee = employeeRepos.findByEmail(email).get();
            if (employee == null) {
                throw new RuntimeException("Invalid token");
            }
            return generateToken(employee);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
