package com.superapp.firstdemo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.superapp.firstdemo.dao.VaccineDao;
import com.superapp.firstdemo.model.Vaccine;

@Repository
@Transactional
public class VaccineDaoImpl implements VaccineDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vaccine> getAllVaccines() {
        String query = "From Vaccine";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Vaccine getVaccineById(Integer id) {
        try {
            Vaccine vaccine = entityManager.find(Vaccine.class, id);
            return vaccine;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean addVaccine(Vaccine vaccine) {
        if (vaccine.getName() == null) {
            return false;
        } else {
            try {
                vaccine.setName(vaccine.getName());
                entityManager.persist(vaccine);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public boolean updateVaccine(Vaccine vaccine) {
        if (vaccine.getName() == null) {
            return false;
        } else {
            try {
                entityManager.merge(vaccine);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public boolean deleteVaccine(Integer id) {
        try {
            Vaccine vaccine = entityManager.find(Vaccine.class, id);
            entityManager.remove(vaccine);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
