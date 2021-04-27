package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<User> findById(Long id) {
        User value = manager.find(User.class, id);
        return Optional.ofNullable(value);
    }

    @Override
    public Set<User> findAll() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from);
        TypedQuery<User> exec = manager.createQuery(query);
        return new HashSet<>(exec.getResultList());
    }
}
