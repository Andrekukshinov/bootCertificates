package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public interface OrderRepository extends ReadOperationRepository<Order> {
    Order save(Order order);

    Set<Order> find(Specification<Order> orderSpecification);
}
