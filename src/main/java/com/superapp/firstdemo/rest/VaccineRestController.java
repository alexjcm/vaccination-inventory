package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.dao.VaccineDao;
import com.superapp.firstdemo.model.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VaccineRestController implements VaccineRest {

    @Autowired
    private VaccineDao vaccineDao;

    @Override
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        return ResponseEntity.ok(vaccineDao.getAllVaccines());
    }

    @Override
    public ResponseEntity<Vaccine> getVaccineById(Integer id) {
        return ResponseEntity.ok(vaccineDao.getVaccineById(id));
    }

    @Override
    public ResponseEntity<Boolean> addVaccine(Vaccine vaccine) {
        return ResponseEntity.ok(vaccineDao.addVaccine(vaccine));
    }

    @Override
    public ResponseEntity<Boolean> deleteUser(Integer id) {
        return ResponseEntity.ok(vaccineDao.deleteVaccine(id));
    }

    @Override
    public ResponseEntity<Boolean> updateUser(Vaccine vaccine) {
        return ResponseEntity.ok(vaccineDao.updateVaccine(vaccine));
    }
}
