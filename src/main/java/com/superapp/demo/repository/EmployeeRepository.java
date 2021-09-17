package com.superapp.demo.repository;

import com.superapp.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Persistence layer, which will act as a database repository. A Repository is
 * an interface that extends JpaRepository for CRUD methods and custom finder
 * methods. It will be autowired in ...Controller(...Rest).
 *
 * @author alexjcm
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("FROM Employee WHERE email=:email")
    Employee findByEmail(@Param("email") String email);

    /*@Query("SELECT u FROM Employee u WHERE u.username = :username")
    public Employee getUserByUsername(@Param("username") String username);*/
}
