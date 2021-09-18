package com.superapp.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v")
public class JwtController {

    @GetMapping("/version")
    public ResponseEntity<Object> version() {
        return ResponseEntity.ok("Version 1.0");
    }
}
