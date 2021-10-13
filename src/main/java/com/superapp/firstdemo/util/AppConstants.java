package com.superapp.firstdemo.util;

public class AppConstants {

    public static final String CUSTOM_TIMEZONE = "UTC";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORITIES_CLAIM_NAME = "roles";

    public static final String[] AUTH_WHITELIST = {
            // Public endpoints
            "/error",
            "/api/authenticate",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

}
