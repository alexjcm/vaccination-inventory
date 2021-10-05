package com.superapp.firstdemo.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.superapp.firstdemo.rest.payload.request.UserRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.superapp.firstdemo.model.User;

/**
 * Contains several endpoints to management users.
 * Protected by JWT authentication and authorized based on role.
 * <p>
 * The User API is defined by an interface with corresponding spring annotations. *
 */
@RequestMapping("api")
@Api(tags = "Users Rest", description = "User management")
public interface UserRest {

    @Operation(summary = "A protected endpoint which returns the user details of the requesting user.")
    @GetMapping(value = "/me")
    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    UserDetails getUser(Authentication authentication);

    @Operation(summary = "Allows to obtain all the users")
    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Allows to obtain a user by its id")
    @PostMapping(value = "/users/{id}")
    ResponseEntity<User> getUserById(@Parameter(description = "User Id  to be searched") @PathVariable Long id);

    @Operation(summary = "Register a new user")
    @PostMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<User> registerUser(@Valid @RequestBody UserRequest userRequest);

    @Operation(summary = "Modify a user by its respective id")
    @PutMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<User> updateUser(@Valid @RequestBody User user);

    @Operation(summary = "Allows you to delete a user by their id")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<User> deleteUser(@PathVariable Long id);

    @Operation(summary = "Allows you to update vaccination data from the employee role.")
    @PutMapping(value = "/user_by_employee")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<User> updateUserByEmployeeRole(@RequestBody User user);

    @Operation(summary = "Gets all users by vaccine type")
    @GetMapping(value = "/users_by_vaccine_type/{id}")
    ResponseEntity<List<User>> getUsersByVaccine(@PathVariable Integer id);

    @Operation(summary = "Gets all the users according to their vaccination status.")
    @GetMapping(value = "/users_is_vaccinated/{status}")
    ResponseEntity<List<User>> getUsersByVaccineStatus(@PathVariable Boolean status);

    @Operation(summary = "Allows to obtain all users by vaccination date range.",
            description = "The start and end dates to be sent should be in a format similar to this YYYY-mm-dd, for example: 2021-09-01s")
    @GetMapping(value = "/users_date_range/{startDate}/{endDate}")
    ResponseEntity<List<User>> getUsersByDateRange(@PathVariable String startDate, @PathVariable String endDate);
}
