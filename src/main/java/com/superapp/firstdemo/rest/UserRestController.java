package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.dao.UserDao;
import com.superapp.firstdemo.exceptions.SuperApiException;
import com.superapp.firstdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// @CrossOrigin(origins = {"${app.security.cors.origin}"})
@RestController
public class UserRestController implements UserRest {

    @Autowired
    private UserDao userDao;

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
        return ResponseEntity.ok(userDao.getAllUsers());
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers2() {
        return ResponseEntity.ok(userDao.getAllUsers());
    }


    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return ResponseEntity.ok(userDao.getUserById(id));
    }

    //////////////
    @Override
    public ResponseEntity<Boolean> registerUser(User user) {
        if (userDao.emailIsAvailable(user.getEmail())) {
            return ResponseEntity.ok(userDao.registerUser(user));
        }
        throw new SuperApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
    }
    /////////////

    @Override
    public ResponseEntity<Boolean> updateUser(User user) {
        return ResponseEntity.ok(userDao.updateUser(user));
    }

    @Override
    public ResponseEntity<Boolean> deleteUser(Long id) {
        return ResponseEntity.ok(userDao.deleteUserById(id));
    }

    @Override
    public ResponseEntity<Boolean> updateUserByEmployeeRole(User user) {
        return ResponseEntity.ok(userDao.updateUserByEmployee(user));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByVaccine(Long id) {
        return ResponseEntity.ok(userDao.getUsersByVaccine(id));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByVaccineStatus(Boolean status) {
        return ResponseEntity.ok(userDao.getUsersByVaccineStatus(status));
    }

    @Override
    public ResponseEntity<List<User>> getUsersByDateRange(Map<String, String> pathVarsMap) {
        String start = pathVarsMap.get("startDate");
        String end = pathVarsMap.get("endDate");
        return ResponseEntity.ok(userDao.getUsersByDateRange(start, end));
    }
}
