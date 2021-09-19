package com.superapp.firstdemo.dao;

import com.superapp.firstdemo.entities.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    boolean deleteUserById(Long id);

    User getUserById(Long id);

    boolean updateUser(User user);

    boolean registerUser(User user);

    List<User> getUsersByVaccine(Long id);

    List<User> getUsersByVaccineStatus(Boolean status);

    List<User> getUsersByDateRange(String dateStart, String dateEnd);

    List<User> authenticate(User user);

    boolean updateUserByEmployee(User user);
}
