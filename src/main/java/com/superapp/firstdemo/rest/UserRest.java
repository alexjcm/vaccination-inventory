package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.dao.UserDao;
import com.superapp.firstdemo.security.JWTToken;
import com.superapp.firstdemo.entities.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@Api(tags = "Users Rest", description = "User management")
public class UserRest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTToken jwtToken;

    @ApiOperation(value = "Allows to obtain all the users")
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "Authorization") String token) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.getAllUsers());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();/////////
    }

    @ApiOperation(value = "Allows to obtain a user by its id")
    @PostMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.getUserById(id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();/////////
    }

    @ApiOperation(value = "Register a new user")
    @PostMapping(value = "/users")
    public ResponseEntity<Boolean> registerUser(@RequestHeader(value = "Authorization") String token, @RequestBody User user) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.registerUser(user));
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);/////////
    }

    @ApiOperation(value = "Allows you to delete a user by their id")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.deleteUserById(id));
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);/////////
    }

    @ApiOperation(value = "Modify a user by its respective id")
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Boolean> updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody User user) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.updateUser(user));
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);/////////
    }

    @ApiOperation(value = "Allows you to update vaccination data from the employee role.")
    @PutMapping(value = "/user_by_employee/{id}")
    public ResponseEntity<Boolean> updateUserByEmployeeRole(@RequestHeader(value = "Authorization") String token, @RequestBody User user) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.updateUserByEmployee(user));
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Gets all users by vaccine type")
    @GetMapping(value = "/users_by_vaccine_type/{id}")
    public ResponseEntity<List<User>> getUsersByVaccine(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.getUsersByVaccine(id));
        }
        return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Gets all the users according to their vaccination status.")
    @GetMapping(value = "/users_is_vaccinated/{status}")
    public ResponseEntity<List<User>> getUsersByVaccineStatus(@RequestHeader(value = "Authorization") String token, @PathVariable Boolean status) {
        if (jwtToken.verifyToken(token) != null) {
            return ResponseEntity.ok(userDao.getUsersByVaccineStatus(status));
        }
        return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Allows to obtain all users by vaccination date range.",
            notes = "The start and end dates to be sent should be in a format similar to this YYYY-mm-dd, for example: 2021-09-01s")
    @GetMapping(value = "/users_date_range/{startDate}/{endDate}")
    public ResponseEntity<List<User>> getUsersByDateRange(@RequestHeader(value = "Authorization") String token, @PathVariable Map<String, String> pathVarsMap) {
        if (jwtToken.verifyToken(token) != null) {
            String start = pathVarsMap.get("startDate");
            String end = pathVarsMap.get("endDate");
            return ResponseEntity.ok(userDao.getUsersByDateRange(start, end));
        }
        return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
    }
}
