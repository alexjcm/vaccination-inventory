package com.superapp.demo.service;

import com.superapp.demo.model.Employee;
import com.superapp.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Service layer. We mark beans with @Service to indicate that they're holding
 * the business logic.
 *
 * @author alexjcm
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAll(Sort sort) {
        return employeeRepository.findAll(sort);
    }

    public Page<Employee> findAll(Pageable pageable) {        
        return employeeRepository.findAll(pageable);
    }

    public <S extends Employee> S save(S entity) {
        return employeeRepository.save(entity);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Boolean deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Employee entity) {
        employeeRepository.delete(entity);
    }

    //NEW ADD
    public List<Employee> findAllByVaccine(Long id) {
        List<Employee> employeesRespuesta = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getVacinne().getId() == id) {
                employeesRespuesta.add(employees.get(i));
            }
        }
        return employeesRespuesta;
    }
    //

    public Employee saveOrUpdate(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

}
