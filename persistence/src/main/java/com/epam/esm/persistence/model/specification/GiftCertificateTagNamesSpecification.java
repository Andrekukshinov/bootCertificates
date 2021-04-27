package com.epam.esm.persistence.model.specification;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

public class GiftCertificateTagNamesSpecification implements Specification<GiftCertificate> {

    private final Set<String> tagNames;

    public GiftCertificateTagNamesSpecification(@NonNull Set<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.having(criteriaBuilder.equal(criteriaBuilder.count(root), tagNames.size()));
        query.groupBy(root);
        Join<GiftCertificate, Tag> joinedTags = root.join("tags");
        return joinedTags.get("name").in(tagNames);
    }
}
