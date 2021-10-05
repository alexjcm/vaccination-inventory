package com.superapp.firstdemo.repository;

import com.superapp.firstdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
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

    /*@Query("SELECT u FROM User u WHERE u.username = :username")
       public User getUserByUsername(@Param("username") String username);*/
    Optional<User> findByUsername(@NotBlank String username);

    /* @Query("FROM User WHERE email=:email")
  User findByEmail(@Param("email") String email);*/
    Optional<User> findByEmail(@NotBlank String email);

    Boolean existsByUsername(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    @Query("FROM User WHERE vaccine_id=:id AND status=true")
    List<User> getUsersByVaccine(@Param("id") Integer id);

    @Query("From User where is_vaccinated=:status and status=true")
    List<User> getUsersByVaccineStatus(@Param("status") Boolean status);

    @Query("From User where date_of_vaccinated >= ?1 and date_of_vaccinated <= ?2 and status=true")
    List<User> getUsersByDateRange(@Param("1") Date dateStart, @Param("2") Date dateEnd);

}