package com.superapp.demo.repository;

import com.superapp.demo.model.User;

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
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE email=:email")
    User findByEmail(@Param("email") String email);

    /*@Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);*/
}
