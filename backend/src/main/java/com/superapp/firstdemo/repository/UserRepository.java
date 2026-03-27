package com.superapp.firstdemo.repository;

import com.superapp.firstdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Persistence layer, which will act as a database repository. A Repository is
 * an interface that extends JpaRepository for CRUD methods and custom finder
 * methods. It will be autowired in ...Controller(...Rest).
 *
 * @author alexjcm
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuth0Id(@NotBlank String auth0Id);

    Optional<User> findByEmail(@NotBlank String email);

    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.vaccine.id = :id AND u.status = true")
    List<User> getUsersByVaccine(@Param("id") Integer id);

    @Query("SELECT u FROM User u WHERE u.isVaccinated = :status AND u.status = true")
    List<User> getUsersByVaccineStatus(@Param("status") Boolean status);

    @Query("SELECT u FROM User u WHERE u.dateOfVaccinated >= :date_start AND u.dateOfVaccinated <= :date_end AND u.status = true")
    List<User> getUsersByDateRange(@Param("date_start") Date dateStart, @Param("date_end") Date dateEnd);

}