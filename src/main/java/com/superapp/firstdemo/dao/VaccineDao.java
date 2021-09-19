package com.superapp.firstdemo.dao;

import com.superapp.firstdemo.entities.Vaccine;

import java.util.List;

public interface VaccineDao {

    List<Vaccine> getAllVaccines();

    Vaccine getVaccineById(Integer id);

    boolean addVaccine(Vaccine vaccine);

    boolean updateVaccine(Vaccine vaccine);

    boolean deleteVaccine(Integer id);

}
