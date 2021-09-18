package com.superapp.demo.service;

import com.superapp.demo.security.JwtIO;
import com.superapp.demo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.superapp.demo.dto.JwtResponse;

@Service
public class AuthService {

    @Autowired
    private JwtIO jwtIO;
    @Autowired
    private DateUtils dateUtils;

    @Value("${jwt.token.expires}")
    private int EXPIRES_IN;

    public JwtResponse login(String clientId, String clientSecret) {
        JwtResponse jwt = JwtResponse.builder()
                .tokenType("bearer")
                .accessToken(jwtIO.generateToken("Kruger"))
                .issuedAt(dateUtils.getDateMillis() + "")
                .clientId(clientId)
                .expiresIn(EXPIRES_IN)
                .build();
        return jwt;
    }
}
