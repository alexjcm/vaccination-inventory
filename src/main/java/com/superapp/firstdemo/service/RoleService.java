package com.superapp.firstdemo.service;

import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.RoleName;
import com.superapp.firstdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

}
