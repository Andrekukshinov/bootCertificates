package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Order;
import com.epam.esm.persistence.model.specification.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends ReadOperationRepository<Order> {
    Order save(Order order);

    Page<Order> find(List<Specification<Order>> orderSpecification, Pageable pageable);
}
