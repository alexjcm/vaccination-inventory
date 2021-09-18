package com.superapp.demo.security;

import com.superapp.demo.util.GsonUtils;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
public class JwtIO {

    @Value("${jwt.token.secret}")
    private String SECRET;
    @Value("${jwt.timezone}")
    private String TIMEZONE;
    @Value("${jwt.token.expires}")
    private int EXPIRES_IN;
    @Value("${jwt.issuer}")
    private String ISSUER;

    public String generateToken(Object src) {

        String subject = GsonUtils.serializable(src);

        Signer signer = HMACSigner.newSHA256Signer(SECRET);

        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE);

        ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone.toZoneId()).plusSeconds(EXPIRES_IN);

        JWT jwt = new JWT()
                .setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(timeZone.toZoneId()))
                .setSubject(subject)
                .setExpiration(zonedDateTime);

        return JWT.getEncoder().encode(jwt, signer);
    }

    public boolean validateToken(String encodedJWT) {

        boolean result = false;

        try {
            JWT jwt = jwt(encodedJWT);
            result = jwt.isExpired();
        } catch (Exception e) {
            result = true;
        }

        return result;
    }

    public String getPayload(String encodedJWT) {

        JWT jwt = jwt(encodedJWT);

        return jwt.subject;
    }

    public JWT jwt(String encodedJWT) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        return JWT.getDecoder().decode(encodedJWT, verifier);
    }

}
