package com.superapp.demo.service;

import com.superapp.demo.model.User;
import com.superapp.demo.repository.UserRepository;

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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    //NEW ADD
    public List<User> findAllByVaccine(Long id) {
        List<User> usersRespuesta = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getVacinne().getId() == id) {
                usersRespuesta.add(users.get(i));
            }
        }
        return usersRespuesta;
    }
    //

    public User saveOrUpdate(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
