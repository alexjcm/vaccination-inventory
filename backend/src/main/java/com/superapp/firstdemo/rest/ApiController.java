package com.superapp.firstdemo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

      @GetMapping("/public")
      public ResponseEntity<Map<String, String>> publicEndpoint() {
          return ResponseEntity.ok(Map.of(
              "message", "This endpoint is public - no authentication required"
          ));
      }

      @GetMapping("/private")
      public ResponseEntity<Map<String, Object>> privateEndpoint(Authentication authentication) {
          String username = authentication.getName();
          Object authorities = authentication.getAuthorities();

          if (authentication instanceof JwtAuthenticationToken jwtToken) {
              return ResponseEntity.ok(Map.of(
                  "message", "This endpoint requires authentication",
                  "user", username,
                  "scopes", authorities,
                  "claims", jwtToken.getTokenAttributes()
              ));
          }

          return ResponseEntity.ok(Map.of(
              "message", "This endpoint requires authentication",
              "user", username,
              "scopes", authorities
          ));
      }

}
