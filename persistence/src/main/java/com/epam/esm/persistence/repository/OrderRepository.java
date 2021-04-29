package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface OrderRepository extends ReadOperationRepository<Order> {
    Order save(Order order);

    Page<Order> find(Specification<Order> orderSpecification, Pageable pageable);
}
