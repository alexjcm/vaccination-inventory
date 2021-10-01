package com.superapp.firstdemo.rest;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.superapp.firstdemo.rest.payload.JwtResponse;

/**
 * The Auth API is defined by an interface with corresponding spring annotations.
 */
@RequestMapping("api")
@Api(tags = "Auth Rest", description = "Authentication")
public interface AuthenticationRest {

    // @ApiOperation(value = "Public endpoint which returns a signed JWT for valid user credentials ")
    @Operation(summary = "Public endpoint which returns a signed JWT for valid user credentials")
    @PostMapping(value = "/authenticate", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})

    JwtResponse authenticate(@RequestParam String username, @RequestParam String password);
}
