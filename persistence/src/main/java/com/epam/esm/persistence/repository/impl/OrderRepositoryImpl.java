package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Order;
import com.epam.esm.persistence.model.specification.FindByIdInSpecification;
import com.epam.esm.persistence.repository.OrderRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Order save(Order order) {
        manager.persist(order);
        return order;
    }

    @Override
    public Set<Order> find(Specification<Order> orderSpecification) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> giftCertificateFrom = query.from(Order.class);
        Predicate predicate = orderSpecification.toPredicate(giftCertificateFrom, query, cb);
        query.where(predicate);
        TypedQuery<Order> exec = manager.createQuery(query);
        return new HashSet<>(exec.getResultList());
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        Specification<Order> getById = new FindByIdInSpecification<>(List.of(orderId));
        return Optional.ofNullable(DataAccessUtils.singleResult(find(getById)));
    }
}
