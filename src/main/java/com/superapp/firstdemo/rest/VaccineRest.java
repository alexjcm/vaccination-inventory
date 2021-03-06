package com.superapp.firstdemo.rest;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;

import com.superapp.firstdemo.model.Vaccine;

/**
 * The Vaccine API is defined by an interface with corresponding spring annotations.
 */
@RequestMapping("api/vaccines")
@Api(tags = "Vaccine Rest", description = "Vaccine management")
public interface VaccineRest {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<Vaccine>> getAllVaccines();

    @ApiOperation(value = "You get a vaccine according to your id")
    @GetMapping(value = "/{id}")
    ResponseEntity<Optional<Vaccine>> getVaccineById(@PathVariable Integer id);

    @ApiOperation(value = "Allows you to add a new vaccine")
    @PostMapping
    ResponseEntity<Vaccine> addVaccine(@Valid @RequestBody Vaccine vaccine);

    @ApiOperation(value = "Modify a vaccine")
    //@PutMapping(value = "/{id}")
    @PutMapping
    ResponseEntity<Vaccine> updateVaccine(@Valid @RequestBody Vaccine vaccine);

    @ApiOperation(value = "Remove a vaccine")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Boolean> deleteVaccine(@PathVariable Integer id);

}
