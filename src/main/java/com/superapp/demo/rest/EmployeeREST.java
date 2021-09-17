package com.superapp.demo.rest;

import com.superapp.demo.util.ConstantUtils;
import com.superapp.demo.config.JwtTokenProvider;
import com.superapp.demo.model.Employee;
import com.superapp.demo.service.EmployeeService;
import com.superapp.demo.service.RoleService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Is a RestController which has request mapping methods for RESTful requests
 *
 * @author alexjcm
 */
@RestController
@RequestMapping("/employees")
public class EmployeeREST {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RoleService roleService;

    private static final Logger logger = Logger.getLogger(EmployeeREST.class.getName());

    @GetMapping
    private ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    //---NEW ADD-----
    @GetMapping("/{id}")
    private ResponseEntity<List<Employee>> getAllEmployeesByVaccine(@PathVariable("id") Long idVaccine) {
        return ResponseEntity.ok(employeeService.findAllByVaccine(idVaccine));
    }
    //---------------

    @PostMapping
    private ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee employeeSaved = employeeService.save(employee);
            //return ResponseEntity.created(new URI("/employees/" + employeeSaved.getId())).body(employeeSaved);
            return ResponseEntity.ok(employeeSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    private ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Optional<Employee> optionalEmployee = employeeService.findById(employee.getId());
        if (optionalEmployee.isPresent()) {
            Employee updateEmployee = optionalEmployee.get();
            updateEmployee.setIdentCard(employee.getIdentCard());
            updateEmployee.setFirstName(employee.getFirstName());
            updateEmployee.setLastName(employee.getLastName());
            updateEmployee.setEmail(employee.getEmail());
            employeeService.save(updateEmployee);
            return ResponseEntity.ok(updateEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    private ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    /////////--------------LOGIN ----------
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody Employee employee) {
        JSONObject jsonObject = new JSONObject();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword()));
            if (authentication.isAuthenticated()) {
                String email = employee.getEmail();
                jsonObject.put("name", authentication.getName());
                jsonObject.put("authorities", authentication.getAuthorities());
                //jsonObject.put("token", tokenProvider.createToken(email, employeeRepository.findByEmail(email).getRole()));
                jsonObject.put("token", tokenProvider.createToken(email, employeeService.findByEmail(email).getRole()));
                return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
            }
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        JSONObject jsonObject = new JSONObject();
        try {
            employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
            //employee.setRole(roleRepository.findByName(ConstantUtils.USER.toString()));
            employee.setRole(roleService.findByName(ConstantUtils.USER.toString()));
            //Employee savedEmployee = employeeRepository.saveAndFlush(employee);
            Employee savedEmployee = employeeService.saveOrUpdate(employee);
            jsonObject.put("message", savedEmployee.getUsername() + " saved succesfully");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
