package com.superapp.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.superapp.demo.model.Employee;
import com.superapp.demo.repository.EmployeeRepository;

//@Service
@Service("userDetailsService")
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email);
        // se puede cambiar a username en lugar del email
        if (employee == null) {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(),
                getGrantedAuthority(employee));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(Employee employee) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (employee.getRole().getName().equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
