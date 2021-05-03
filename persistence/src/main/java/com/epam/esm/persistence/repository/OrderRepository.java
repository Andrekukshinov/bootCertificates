package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Order;
import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.persistence.model.specification.Specification;

public interface OrderRepository extends ReadOperationRepository<Order> {
    Order save(Order order);

    Page<Order> find(Specification<Order> orderSpecification, Pageable pageable);
}
