package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.exceptions.SuperApiException;
import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.rest.payload.request.UserRequest;
import com.superapp.firstdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// @CrossOrigin(origins = {"${app.security.cors.origin}"})
@RestController
public class UserRestController implements UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserDetails getUser(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        return userDetailsService.loadUserByUsername(attributes.get("username").toString());
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<User> registerUser(UserRequest userRequest) {
        if (userService.emailIsAvailable(userRequest.getEmail())) {
            return ResponseEntity.ok(userService.registerUser(userRequest));
        }
        throw new SuperApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
    }

    @Override
    public ResponseEntity<User> updateUser(User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @Override
    public ResponseEntity<User> deleteUser(Long id) {
        return ResponseEntity.ok(userService.deactivateUserById(id));
    }

    @Override
    public ResponseEntity<User> updateUserByEmployeeRole(User user) {
        return ResponseEntity.ok(userService.updateUserByEmployee(user));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByVaccine(Integer id) {
        return ResponseEntity.ok(userService.getUsersByVaccine(id));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByVaccineStatus(Boolean status) {
        return ResponseEntity.ok(userService.getUsersByVaccineStatus(status));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByDateRange(String startDate, String endDate) {
        return ResponseEntity.ok(userService.getUsersByDateRange(startDate, endDate));
    }
}
