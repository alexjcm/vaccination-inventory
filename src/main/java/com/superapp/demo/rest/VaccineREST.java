package com.superapp.demo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.superapp.demo.model.Vaccine;
import com.superapp.demo.service.VaccineService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/vaccines")
public class VaccineREST {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    private ResponseEntity<List<Vaccine>> getAllVaccines() {
        return ResponseEntity.ok(vaccineService.findAll());
    }

    @PostMapping
    private ResponseEntity<Vaccine> createVaccine(@RequestBody Vaccine vaccine) {
        try {
            Vaccine vaccineSaved = vaccineService.save(vaccine);
            return ResponseEntity.ok(vaccineSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    private ResponseEntity<Vaccine> updateVaccine(@RequestBody Vaccine vaccine) {
        Optional<Vaccine> optionalVaccine = vaccineService.findById(vaccine.getId());
        if (optionalVaccine.isPresent()) {
            Vaccine updateVaccine = optionalVaccine.get();
            updateVaccine.setName(vaccine.getName());
            vaccineService.save(updateVaccine);
            return ResponseEntity.ok(updateVaccine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    private ResponseEntity<Boolean> deleteVaccine(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(vaccineService.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
