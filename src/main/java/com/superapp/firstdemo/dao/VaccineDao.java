package com.superapp.firstdemo.dao;

import java.util.List;

import com.superapp.firstdemo.model.Vaccine;

public interface VaccineDao {

    List<Vaccine> getAllVaccines();

    Vaccine getVaccineById(Integer id);

    boolean addVaccine(Vaccine vaccine);

    boolean updateVaccine(Vaccine vaccine);

    boolean deleteVaccine(Integer id);

}
