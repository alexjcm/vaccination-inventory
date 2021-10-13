package com.superapp.firstdemo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.superapp.firstdemo.exceptions.ResourceNotFoundException;
import com.superapp.firstdemo.exceptions.SuperApiException;
import com.superapp.firstdemo.model.RoleName;
import com.superapp.firstdemo.repository.RoleRepository;
import com.superapp.firstdemo.repository.UserRepository;
import com.superapp.firstdemo.rest.payload.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.util.ValidatorsUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    public User deactivateUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setStatus(Boolean.FALSE);
        return userRepository.saveAndFlush(user);
    }

//    public List<User> authenticate(User user) {
//        List<User> resultQuery = new ArrayList<>();
//        boolean isReady = false;
//        try {
//            String query = "From User where username= :username and status=true";
//            resultQuery = entityManager.createQuery(query)
//                    .setParameter("username", user.getUsername())
//                    .getResultList();
//            if (!resultQuery.isEmpty()) {
//                isReady = passwordEncoder.matches(user.getPassword(), resultQuery.get(0).getPassword());
//                logger.info("===> isReady: " + isReady);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        if (isReady) {
//            return resultQuery;
//        }
//        return null;
//    }

    /**
     * Verify if the identification card, names and email of the user have the correct format.
     *
     * @param user
     * @return
     */
    private boolean verifyDataFormat(User user) {
        return ValidatorsUtil.identCardIsValid(user.getIdentCard().toString())
                && ValidatorsUtil.namesAreValid(user.getFirstName()) && ValidatorsUtil.namesAreValid(user.getLastName())
                && ValidatorsUtil.emailIsValid(user.getEmail());
    }

    /**
     * Registers the user by validating the required data, and automatically generates a username and password for
     * the user along with the user's role and status.
     *
     * @param userRequest
     * @return
     */
    public User registerUser(UserRequest userRequest) {
        if (userRequest.getIdentCard() == null || userRequest.getFirstName() == null || userRequest.getLastName() == null || userRequest.getEmail() == null) {
            throw new SuperApiException(HttpStatus.BAD_REQUEST, "Some new User data is missing");
        }
        User user = new User();
        user.setIdentCard(userRequest.getIdentCard());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        if (verifyDataFormat(user)) {
            user.setUsername(userRequest.getEmail()); // default username: the email
            user.setPassword(passwordEncoder.encode(userRequest.getIdentCard().toString())); //default password: the identCard
            user.setStatus(true);
            user.setIsVaccinated(false);

            List<Role> roles = new ArrayList<>();
            //roles.add(roleRepository.findByName(RoleName.ROLE_USER.toString()));
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            user.setRoles(roles);

            return userRepository.save(user);
        }

        throw new SuperApiException(HttpStatus.BAD_REQUEST, "The new user data is not in the correct format");
    }

    /**
     * Update a user by validating that the required data is valid.
     *
     * @param user
     * @return
     */

    public User updateUser(User user) {
        if (user.getIdentCard() == null || user.getEmail() == null || user.getLastName() == null || user.getFirstName() == null) {
            throw new SuperApiException(HttpStatus.BAD_REQUEST, "Some User data is missing");
        }
        if (verifyDataFormat(user)) {
            return userRepository.saveAndFlush(user);
        }

        throw new SuperApiException(HttpStatus.BAD_REQUEST, "The user data is not in the correct format");

    }

    public List<User> getUsersByVaccine(Integer id) {
        return userRepository.getUsersByVaccine(id);
    }

    public List<User> getUsersByVaccineStatus(Boolean status) {
        return userRepository.getUsersByVaccineStatus(status);
    }

    public List<User> getUsersByDateRange(String dateStart, String dateEnd) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
            return userRepository.getUsersByDateRange(date1, date2);
        } catch (ParseException e) {
            throw new SuperApiException(HttpStatus.BAD_REQUEST, "Dates are not in the correct format");
        }
    }

    /**
     * Allows to update the normal user information to be provided by employees.
     *
     * @param user
     * @return
     */
    public User updateUserByEmployee(User user) {
        if (ValidatorsUtil.isNumeric(user.getCellPhoneNumber().toString())) {
            User updatedUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
            updatedUser.setDateOfBirth(user.getDateOfBirth());
            updatedUser.setHomeAddress(user.getHomeAddress());
            updatedUser.setCellPhoneNumber(user.getCellPhoneNumber());
            updatedUser.setIsVaccinated(user.getIsVaccinated());

            if (updatedUser.getIsVaccinated()) {
                updatedUser.setVaccine(user.getVaccine());
                updatedUser.setDateOfVaccinated(user.getDateOfVaccinated());
                updatedUser.setNumberOfDoses(user.getNumberOfDoses());
            }
            return userRepository.saveAndFlush(updatedUser);
        }

        throw new SuperApiException(HttpStatus.BAD_REQUEST, "This  is not numeric" + user.getCellPhoneNumber());
    }

    public Boolean usernameIsAvailable(String username) {
        Boolean isAvailable = userRepository.existsByUsername(username);
        logger.info("Email isAvailable ===> " + isAvailable);
        return isAvailable;
    }

    public Boolean emailIsAvailable(String email) {
        Boolean isAvailable = userRepository.existsByEmail(email);
        logger.info("Email is available? ===> " + isAvailable);
        return isAvailable;
    }
}
