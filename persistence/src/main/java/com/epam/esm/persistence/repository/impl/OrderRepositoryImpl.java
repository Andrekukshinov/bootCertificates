package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Order;
import com.epam.esm.persistence.model.specification.FindByIdInSpecification;
import com.epam.esm.persistence.repository.OrderRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

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
    public Page<Order> find(Specification<Order> orderSpecification, Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> giftCertificateFrom = query.from(Order.class);
        Predicate predicate = orderSpecification.toPredicate(giftCertificateFrom, query, cb);
        query.where(predicate);
        Long lastPage = getLastPage(cb);
        TypedQuery<Order> exec = getPagedQuery(pageable, cb, query, giftCertificateFrom);
        List<Order> resultList = exec.getResultList();
        return new PageImpl<>(resultList, pageable, lastPage);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        Specification<Order> getById = new FindByIdInSpecification<>(List.of(orderId));
        return Optional.ofNullable(DataAccessUtils.singleResult(find(getById, Pageable.unpaged()).getContent()));
    }

    private Long getLastPage(CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Order.class)));
        return manager.createQuery(countQuery).getSingleResult();
    }


    private TypedQuery<Order> getPagedQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<Order> query, Root<Order> from) {
        if (pageable.isPaged()) {
            long pageNumber = pageable.getOffset();
            int pageSize = pageable.getPageSize();
            query.orderBy((QueryUtils.toOrders(pageable.getSort(), from, cb)));
            TypedQuery<Order> exec = manager.createQuery(query);
            exec.setFirstResult(Math.toIntExact(pageNumber));
            exec.setMaxResults((pageSize));
            return exec;
        }
        return manager.createQuery(query);
    }
}
