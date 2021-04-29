package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

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
    public Page<User> findAll(Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from);
        TypedQuery<User> exec = getPagedQuery(pageable, cb, query, from);
        Long lastPage = getLastPage(cb);

        List<User> resultList = exec.getResultList();
        return new PageImpl<>(resultList, pageable, lastPage);
    }

    private TypedQuery<User> getPagedQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<User> query, Root<User> from) {
        if (pageable.isPaged()) {
            long pageNumber = pageable.getOffset();
            int pageSize = pageable.getPageSize();
            query.orderBy((QueryUtils.toOrders(pageable.getSort(), from, cb)));
            TypedQuery<User> exec = manager.createQuery(query);
            exec.setFirstResult(Math.toIntExact(pageNumber));
            exec.setMaxResults((pageSize));
            return exec;
        }
        return  manager.createQuery(query);
    }

    private Long getLastPage(CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(User.class)));
        return manager.createQuery(countQuery).getSingleResult();
    }

}
