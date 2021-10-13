package com.superapp.firstdemo.service;

import com.superapp.firstdemo.model.Vaccine;
import com.superapp.firstdemo.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author alexjcm
 */
@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public Optional<Vaccine> getVaccineById(Integer id) {
        return vaccineRepository.findById(id);
    }

    public Vaccine findByName(String name) {
        return vaccineRepository.findByName(name);
    }

    public <S extends Vaccine> S addVaccine(S entity) {
        return vaccineRepository.save(entity);
    }

    public Vaccine updateVaccine(Vaccine vaccine) {
        return vaccineRepository.saveAndFlush(vaccine);
    }

    public Boolean deleteVaccine(Integer id) {
        if (vaccineRepository.existsById(id)) {
            vaccineRepository.deleteById(id);
            return true;
        }
        return false;
    }

}