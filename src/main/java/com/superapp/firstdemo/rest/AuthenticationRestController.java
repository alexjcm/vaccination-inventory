package com.superapp.firstdemo.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.superapp.firstdemo.util.AppConstants.AUTHORITIES_CLAIM_NAME;

import com.superapp.firstdemo.security.JwtHelper;
import com.superapp.firstdemo.rest.payload.response.JwtResponse;

/**
 * The auth controller to handle login requests.
 * The Auth API interface is implemented by a Spring @RestController.
 *
 * @author
 */
@RestController
public class AuthenticationRestController implements AuthenticationRest {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(AuthenticationRestController.class.getName());

    @Override
    public JwtResponse authenticate(String username, String password) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User: " + username + " not found");
        }

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            Map<String, String> claims = new HashMap<>();
            claims.put("username", username);
            claims.put(AUTHORITIES_CLAIM_NAME, authorities);
            //claims.put("userId", String.valueOf(1));

            String token = jwtHelper.createTokenJwt(username, claims);
            return new JwtResponse(token, "Bearer");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User: " + username + " not authenticated");
    }
}
