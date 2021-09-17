package com.superapp.demo.service;

import com.superapp.demo.model.Employee;
import com.superapp.demo.model.Vaccine;
import com.superapp.demo.model.Vaccine;
import com.superapp.demo.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public List<Vaccine> findAll() {
        return vaccineRepository.findAll();
    }

    public Optional<Vaccine> findById(Long id) {
        return vaccineRepository.findById(id);
    }

    public <S extends Vaccine> S save(S entity) {
        return vaccineRepository.save(entity);
    }

    public Boolean deleteById(Long id) {
        if (vaccineRepository.existsById(id)) {
            vaccineRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
