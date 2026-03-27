package com.superapp.firstdemo.service;

import com.superapp.firstdemo.exceptions.ResourceNotFoundException;
import com.superapp.firstdemo.exceptions.SuperApiException;
import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.RoleName;
import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.repository.RoleRepository;
import com.superapp.firstdemo.repository.UserRepository;
import com.superapp.firstdemo.util.ValidatorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User deactivateUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setStatus(Boolean.FALSE);
        return userRepository.saveAndFlush(user);
    }

    /**
     * Synchronize Auth0 user with local database.
     */
    @Transactional
    public User getOrCreateUser(JwtAuthenticationToken token) {
        String auth0Id = token.getToken().getSubject();
        String email = token.getTokenAttributes().get("email") != null ? 
                      token.getTokenAttributes().get("email").toString() : null;
        String name = token.getTokenAttributes().get("name") != null ? 
                     token.getTokenAttributes().get("name").toString() : "Auth0 User";
        
        if (auth0Id == null) {
            throw new SuperApiException(HttpStatus.BAD_REQUEST, "Token 'sub' claim is missing");
        }

        return userRepository.findByAuth0Id(auth0Id).orElseGet(() -> {
            logger.info("Creating new local user for Auth0 ID: " + auth0Id);
            User newUser = new User();
            newUser.setAuth0Id(auth0Id);
            newUser.setEmail(email);
            
            // Extract first and last name if possible, otherwise use name as firstName
            if (name != null) {
                String[] nameParts = name.split(" ", 2);
                newUser.setFirstName(nameParts[0]);
                newUser.setLastName(nameParts.length > 1 ? nameParts[1] : "");
            } else {
                newUser.setFirstName("Auth0");
                newUser.setLastName("User");
            }

            newUser.setStatus(true);
            newUser.setIsVaccinated(false);
            newUser.setIdentCard(0); // Dummy identCard for new Auth0 users; should be updated later

            List<Role> roles = new ArrayList<>();
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
            if (userRole != null) {
                roles.add(userRole);
            }
            newUser.setRoles(roles);

            return userRepository.save(newUser);
        });
    }

    /**
     * Verify if the identification card, names and email of the user have the correct format.
     */
    private boolean verifyDataFormat(User user) {
        return (user.getIdentCard() == null || ValidatorsUtil.identCardIsValid(user.getIdentCard().toString()))
                && ValidatorsUtil.namesAreValid(user.getFirstName()) 
                && (user.getLastName() == null || ValidatorsUtil.namesAreValid(user.getLastName()))
                && ValidatorsUtil.emailIsValid(user.getEmail());
    }

    /**
     * Update a user by validating that the required data is valid.
     */
    public User updateUser(User user) {
        if (user.getEmail() == null || user.getFirstName() == null) {
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
     */
    public User updateUserByEmployee(User user) {
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        
        updatedUser.setDateOfBirth(user.getDateOfBirth());
        updatedUser.setHomeAddress(user.getHomeAddress());
        updatedUser.setCellPhoneNumber(user.getCellPhoneNumber());
        updatedUser.setIsVaccinated(user.getIsVaccinated());

        if (Boolean.TRUE.equals(updatedUser.getIsVaccinated())) {
            updatedUser.setVaccine(user.getVaccine());
            updatedUser.setDateOfVaccinated(user.getDateOfVaccinated());
            updatedUser.setNumberOfDoses(user.getNumberOfDoses());
        }
        return userRepository.saveAndFlush(updatedUser);
    }
}
