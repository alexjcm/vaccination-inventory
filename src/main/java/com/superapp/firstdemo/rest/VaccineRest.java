package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.dao.VaccineDao;
import com.superapp.firstdemo.entities.Vaccine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("api/vaccines")
@Api(tags = "Vaccine Rest", description = "Vaccine management")
public class VaccineRest {

    @Autowired
    private VaccineDao vaccineDao;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        return ResponseEntity.ok(vaccineDao.getAllVaccines());
    }

    @ApiOperation(value = "You get a vaccine according to your id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaccineDao.getVaccineById(id));
    }

    @ApiOperation(value = "Allows you to add a new vaccine")
    @PostMapping
    public ResponseEntity<Boolean> addVaccine(@RequestBody Vaccine vaccine) {
        return ResponseEntity.ok(vaccineDao.addVaccine(vaccine));
    }

    @ApiOperation(value = "Remove a vaccine")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(vaccineDao.deleteVaccine(id));
    }

    @ApiOperation(value = "Modify a vaccine")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateUser(@RequestBody Vaccine vaccine) {
        return ResponseEntity.ok(vaccineDao.updateVaccine(vaccine));
    }
}
