package com.superapp.firstdemo.rest;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.superapp.firstdemo.model.User;

/**
 * The User API is defined by an interface with corresponding spring annotations.
 */
@RequestMapping("api")
@Api(tags = "Users Rest", description = "User management")
public interface UserRest {

    @Operation(summary = "A protected endpoint which returns the user details of the requesting user.")
    @GetMapping(value = "/me")
        //@PreAuthorize("hasRole('USER')")
    UserDetails getUser(Authentication authentication);

    ///////////
    @Operation(summary = "Allows to obtain all the users")
    @GetMapping(value = "/users")
    //@PreAuthorize("hasRole('USER')")
    //@PreAuthorize("hasAuthority('USER')")
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Allows to obtain all the users2")
    @GetMapping(value = "/users2")
    //@PreAuthorize("hasRole('ROLE_USER')")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    ResponseEntity<List<User>> getAllUsers2();
    //////////////

    @Operation(summary = "Allows to obtain a user by its id")
    @PostMapping(value = "/users/{id}")
    ResponseEntity<User> getUserById(@Parameter(description = "Id del usuario to be searched") @PathVariable Long id);

    @Operation(summary = "Register a new user")
    @PostMapping(value = "/users")
        //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Boolean> registerUser(@Valid @RequestBody User user);

    @Operation(summary = "Modify a user by its respective id")
    @PutMapping(value = "/users/{id}")
        //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    ResponseEntity<Boolean> updateUser(@Valid @RequestBody User user);

    @Operation(summary = "Allows you to delete a user by their id")
    @DeleteMapping(value = "/users/{id}")
    ResponseEntity<Boolean> deleteUser(@PathVariable Long id);

    @Operation(summary = "Allows you to update vaccination data from the employee role.")
    @PutMapping(value = "/user_by_employee/{id}")
    ResponseEntity<Boolean> updateUserByEmployeeRole(@RequestBody User user);

    @Operation(summary = "Gets all users by vaccine type")
    @GetMapping(value = "/users_by_vaccine_type/{id}")
    ResponseEntity<List<User>> getUsersByVaccine(@PathVariable Long id);

    @Operation(summary = "Gets all the users according to their vaccination status.")
    @GetMapping(value = "/users_is_vaccinated/{status}")
    ResponseEntity<List<User>> getUsersByVaccineStatus(@PathVariable Boolean status);

    @Operation(summary = "Allows to obtain all users by vaccination date range.",
            description = "The start and end dates to be sent should be in a format similar to this YYYY-mm-dd, for example: 2021-09-01s")
    @GetMapping(value = "/users_date_range/{startDate}/{endDate}")
    ResponseEntity<List<User>> getUsersByDateRange(@PathVariable Map<String, String> pathVarsMap);
}
