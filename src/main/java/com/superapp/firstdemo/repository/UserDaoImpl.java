package com.superapp.firstdemo.repository;

import com.superapp.firstdemo.dao.UserDao;
import com.superapp.firstdemo.entities.Role;
import com.superapp.firstdemo.entities.User;
import com.superapp.firstdemo.util.PasswordUtil;
import com.superapp.firstdemo.util.ValidatorsUtil;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public List<User> getAllUsers() {
        String query = "From User where status=true";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            user.setStatus(Boolean.FALSE);
            entityManager.merge(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> authenticate(User user) {
        List<User> resultQuery = new ArrayList<>();
        boolean isReady = false;
        try {
            String query = "From User where username= :username and status=true";
            resultQuery = entityManager.createQuery(query)
                    .setParameter("username", user.getUsername())
                    .getResultList();
            if (!resultQuery.isEmpty()) {
                isReady = resultQuery.get(0).getPassword().equals(PasswordUtil.digestPassword(user.getPassword()));
                logger.info("===> isReady: " + isReady);
            }
        } catch (Exception e) {
            return null;
        }
        if (isReady) {
            return resultQuery;
        }
        return null;
    }

    /**
     * Update a user by validating that the required data is valid.
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(User user) {
        if (user.getIdentCard() == null || user.getEmail() == null || user.getLastName() == null || user.getFirstName() == null) {
            return false;
        }
        if (verifyDataFormat(user)) {
            try {
                entityManager.merge(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean verifyDataFormat(User user) {
        return ValidatorsUtil.identCardIsValid(user.getIdentCard().toString())
                && ValidatorsUtil.namesAreValid(user.getFirstName()) && ValidatorsUtil.namesAreValid(user.getLastName())
                && ValidatorsUtil.emailIsValid(user.getEmail());
    }

    /**
     * Registers the user by validating the required data, and automatically generates a username and password for
     * the user along with the user's role and status.
     *
     * @param user
     * @return
     */
    @Override
    public boolean registerUser(User user) {
        if (user.getIdentCard() == null || user.getEmail() == null || user.getLastName() == null || user.getFirstName() == null) {
            return false;
        }
        if (verifyDataFormat(user)) {
            user.setUsername(user.getEmail()); // username is the email
            user.setPassword(PasswordUtil.digestPassword(user.getIdentCard().toString())); // identCard is password
            user.setStatus(true);
            user.setIsVaccinated(false); // default: false
            Role role = new Role();
            role.setId(1); // role: user
            user.setRole(role);
            try {
                logger.info("===> " + user.getFirstName());
                entityManager.persist(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<User> getUsersByVaccine(Long id) {
        String query = "From User where vaccine_id=:id and status=true";
        try {
            return entityManager.createQuery(query).setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getUsersByVaccineStatus(Boolean status) {
        String query = "From User where is_vaccinated=:status and status=true";
        try {
            return entityManager.createQuery(query)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getUsersByDateRange(String dateStart, String dateEnd) {
        String query = "From User where date_of_vaccinated >= ?1 and date_of_vaccinated <= ?2 and status=true";
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
            return entityManager.createQuery(query)
                    .setParameter(1, date1)
                    .setParameter(2, date2)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Allows to update the normal user information to be provided by employees.
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUserByEmployee(User user) {
        if (ValidatorsUtil.isNumeric(user.getCellPhoneNumber().toString())) {
            try {
                User updatedUser = entityManager.find(User.class, user.getId());
                updatedUser.setDateOfBirth(user.getDateOfBirth());
                updatedUser.setHomeAddress(user.getHomeAddress());
                updatedUser.setCellPhoneNumber(user.getCellPhoneNumber());
                updatedUser.setIsVaccinated(user.getIsVaccinated());
                if (updatedUser.getIsVaccinated()) {
                    updatedUser.setVaccine(user.getVaccine());
                    updatedUser.setDateOfVaccinated(user.getDateOfVaccinated());
                    updatedUser.setNumberOfDoses(user.getNumberOfDoses());
                }
                entityManager.merge(updatedUser);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
