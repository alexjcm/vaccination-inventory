package com.superapp.firstdemo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.superapp.firstdemo.service.VaccineService;
import com.superapp.firstdemo.model.Vaccine;

@RestController
public class VaccineRestController implements VaccineRest {

    @Autowired
    private VaccineService vaccineService;

    @Override
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        return ResponseEntity.ok(vaccineService.getAllVaccines());
    }

    @Override
    public ResponseEntity<Optional<Vaccine>> getVaccineById(Integer id) {
        return ResponseEntity.ok(vaccineService.getVaccineById(id));
    }

    @Override
    public ResponseEntity<Vaccine> addVaccine(Vaccine vaccine) {
        try {
            Vaccine vaccineSaved = vaccineService.addVaccine(vaccine);
            return ResponseEntity.ok(vaccineSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<Vaccine> updateVaccine(Vaccine vaccine) {
        return ResponseEntity.ok(vaccineService.updateVaccine(vaccine));
    }

    @Override
    public ResponseEntity<Boolean> deleteVaccine(Integer id) {
        return ResponseEntity.ok(vaccineService.deleteVaccine(id));
    }
}
