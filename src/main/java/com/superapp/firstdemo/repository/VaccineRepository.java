package com.superapp.firstdemo.repository;

import com.superapp.firstdemo.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {

    @Query("FROM Vaccine WHERE name=:name")
    Vaccine findByName(@Param("name") String name);
}