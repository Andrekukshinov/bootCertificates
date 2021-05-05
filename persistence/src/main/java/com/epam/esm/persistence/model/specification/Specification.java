package com.epam.esm.persistence.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);

    static <T> Specification<T> and(Specification<T> thisSpec, Specification<T> other) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        other.toPredicate(root, query, criteriaBuilder),
                        thisSpec.toPredicate(root, query, criteriaBuilder));

    }
}
