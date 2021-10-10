package com.superapp.firstdemo.rest.payload.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Login response object containing the JWT
 *
 * @author imesha
 */
@Data
@RequiredArgsConstructor
public class JwtResponse {

    @NonNull
    private String token;

    @NonNull
    private String tokenType;
}
