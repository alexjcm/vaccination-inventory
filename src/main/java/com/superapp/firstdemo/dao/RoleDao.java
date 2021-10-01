package com.superapp.firstdemo.dao;

import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*public interface RoleDao {
    Role findByName(RoleName name);
}*/

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(RoleName name);
}
