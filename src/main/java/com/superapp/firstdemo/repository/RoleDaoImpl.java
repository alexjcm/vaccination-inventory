package com.superapp.firstdemo.repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.superapp.firstdemo.dao.RoleDao;
import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.model.RoleName;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(RoleDaoImpl.class.getName());

    @Override
    public Role findByName(RoleName name) {
        String query = "From Role where name=:name";
        try {
//            return entityManager.createQuery(query).setParameter("name", name)
//                    .getResultList();
            Role role = (Role) entityManager.createQuery(query)
                    .setParameter("name", name)
                    .getResultList().get(0);
            logger.info("role ===> " + role);
            return role;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Role> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Role> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Role> S save(S s) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Role> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Role> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Role> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Role getOne(Integer integer) {
        return null;
    }

    @Override
    public Role getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Role> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Role> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Role> boolean exists(Example<S> example) {
        return false;
    }
}
