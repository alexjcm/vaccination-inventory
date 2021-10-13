package com.superapp.firstdemo.repository;

import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("FROM Role WHERE name=:name")
    Role findByName(@Param("name") RoleName name);

}