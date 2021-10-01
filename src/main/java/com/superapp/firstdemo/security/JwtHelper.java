package com.superapp.firstdemo.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A helper class to create a signed JWT given the claims.
 *
 * @author alexjcm
 */
@Slf4j
@Component
public class JwtHelper {

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.ttlSecs}")
    private Long EXPIRATION_TIME_SECS;

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

    private static final Logger logger = Logger.getLogger(JwtHelper.class.getName());


    public String createTokenJwt(String subject, Map<String, String> claims) {
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
        // Add claims
        claims.forEach(jwtBuilder::withClaim);

        // Add expiredAt
        Date currentTime = Date.from(Instant.now());
        // Date.from(Instant.now().plusSeconds(EXPIRATION_TIME_SECS)) ===> Sun Sep 26 12:09:12 ECT 2021
        return jwtBuilder
                .withIssuer(ISSUER)
                .withIssuedAt(currentTime)
                .withNotBefore(currentTime) // nbf
                .withExpiresAt(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME_SECS)))
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
