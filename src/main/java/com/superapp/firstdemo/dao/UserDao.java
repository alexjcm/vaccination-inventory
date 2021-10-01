package com.superapp.firstdemo.dao;

import java.util.List;

import com.superapp.firstdemo.model.User;

/**
 *
 */
public interface UserDao {

    List<User> getAllUsers();

    boolean deleteUserById(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    boolean updateUser(User user);

    boolean registerUser(User user);

    List<User> getUsersByVaccine(Long id);

    List<User> getUsersByVaccineStatus(Boolean status);

    List<User> getUsersByDateRange(String dateStart, String dateEnd);

    List<User> authenticate(User user);

    boolean updateUserByEmployee(User user);

    ///add
    Boolean usernameIsAvailable(String username);

    Boolean emailIsAvailable(String email);

}
