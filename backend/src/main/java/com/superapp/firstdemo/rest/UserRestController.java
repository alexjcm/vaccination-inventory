package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController implements UserRest {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<Map<String, Object>> getUser(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", attributes.get("sub"));
        claims.put("email", attributes.get("email"));
        claims.put("name", attributes.get("name"));
        
        return ResponseEntity.ok(claims);
    }

    @Override
    public ResponseEntity<User> getOrCreateUser(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        return ResponseEntity.ok(userService.getOrCreateUser(token));
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
