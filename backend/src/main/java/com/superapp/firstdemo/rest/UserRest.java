package com.superapp.firstdemo.rest;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import com.superapp.firstdemo.model.User;

/**
 * Contains several endpoints to management users.
 * Protected by JWT authentication and authorized based on role.
 * <p>
 * The User API is defined by an interface with corresponding spring annotations. *
 */
@RequestMapping("api")
@Tag(name = "Users Rest", description = "User management")
public interface UserRest {

    @Operation(summary = "Returns the JWT claims of the requesting user.")
    @GetMapping(value = "/user/info")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    ResponseEntity<Map<String, Object>> getUser(Authentication authentication);

    @Operation(summary = "Synchronizes and returns the profile of the requesting user from the local database.")
    @GetMapping(value = "/me")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    ResponseEntity<User> getOrCreateUser(Authentication authentication);

    @Operation(summary = "Allows to obtain all the users")
    @GetMapping(value = "/users")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Allows to obtain a user by its id")
    @GetMapping(value = "/users/{id}")
    ResponseEntity<User> getUserById(@Parameter(description = "User Id to be searched") @PathVariable Long id);

    @Operation(summary = "Modify a user by its respective id")
    @PutMapping(value = "/users")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<User> updateUser(@Valid @RequestBody User user);

    @Operation(summary = "Allows you to delete a user by their id")
    @DeleteMapping(value = "/users/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<User> deleteUser(@PathVariable Long id);

    @Operation(summary = "Allows you to update vaccination data from the employee role.")
    @PutMapping(value = "/user_by_employee")
    //@PreAuthorize("hasRole('ROLE_USER')")
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
