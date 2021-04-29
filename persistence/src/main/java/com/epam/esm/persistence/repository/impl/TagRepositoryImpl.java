package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class TagRepositoryImpl implements TagRepository {
    private static final String ALL_TAGS = "SELECT T FROM Tag T";
    private static final String FIND_IN_CERTIFICATES =
            " SELECT tags.id, tags.name FROM tags" +
            " INNER JOIN tags_gift_certificates tgc ON tags.id = tgc.tag_id" +
            " WHERE tags.id = :id";
    private static final String FIND_MOST_POPULAR_TOP_USER_TAG =
            "SELECT tg.name, tg.id, SUM(oc.quantity) total_amount\n" +
            "FROM tags AS tg\n" +
            "         INNER JOIN tags_gift_certificates tgc ON tg.id = tgc.tag_id\n" +
            "         INNER JOIN gift_certificates gc ON tgc.gift_certificate_id = gc.id\n" +
            "         INNER JOIN order_certificates oc ON gc.id = oc.certificate_id\n" +
            "         INNER JOIN orders o ON oc.order_id = o.id\n" +
            "         INNER JOIN users u ON o.user_id = u.id\n" +
            "WHERE u.id = (\n" +
            "    SELECT user_id AS uid\n" +
            "    FROM orders\n" +
            "    GROUP BY uid\n" +
            "    ORDER BY SUM(total_price) DESC\n" +
            "    LIMIT 0,1\n" +
            ")\n" +
            "GROUP BY tg.name,  tg.id\n" +
            "ORDER BY total_amount DESC\n" +
            "LIMIT 0,1;";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Tag save(Tag tag) {
        manager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(manager.find(Tag.class, id));
    }

    @Override
    public int delete(Long id) {
        Tag tag = manager.find(Tag.class, id);
        manager.remove(tag);
        //fixme
        return tag.getId() != 0 ? 1 : 0;
    }

    @Override
    public Optional<Tag> findInCertificates(Long id) {
        Query query = manager.createNativeQuery(FIND_IN_CERTIFICATES, Tag.class).setParameter("id", id);
        return Optional.ofNullable((Tag)DataAccessUtils.singleResult(query.getResultList()));
    }

    @Override
    public Set<Tag> findTagsByNames(Set<String> tagNames) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = cb.createQuery(Tag.class);
        Root<Tag> tag = query.from(Tag.class);
        query.select(tag).where(tag.get("name").in(tagNames));
        TypedQuery<Tag> typedQuery = manager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toSet());
    }


    @Override
    public Optional<Tag> findByName(String tagName) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = cb.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);

        Predicate name = cb.equal(root.get("name"), tagName);

        query.where(name);
        TypedQuery<Tag> typedQuery = manager.createQuery(query);
        return Optional.ofNullable(DataAccessUtils.singleResult(typedQuery.getResultList()));
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = cb.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        query.select(from);

        TypedQuery<Tag> exec = getPagedQuery(pageable, cb, query, from);

        Long lastPage = getLastPage(cb);

        List<Tag> tags = exec.getResultList();
        return new PageImpl<>(tags, pageable, lastPage);
    }

    private Long getLastPage(CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Tag.class)));
        return manager.createQuery(countQuery).getSingleResult();
    }

    private TypedQuery<Tag> getPagedQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<Tag> query, Root<Tag> root) {
        if (pageable.isPaged()) {
            long pageNumber = pageable.getOffset();
            int pageSize = pageable.getPageSize();
            query.orderBy((QueryUtils.toOrders(pageable.getSort(), root, cb)));
            TypedQuery<Tag> exec = manager.createQuery(query);
            exec.setFirstResult(Math.toIntExact(pageNumber));
            exec.setMaxResults((pageSize));
            return exec;
        }
        return manager.createQuery(query);
    }

    @Override
    public Set<Tag> saveAll(Set<Tag> tagsToBeSaved) {
        tagsToBeSaved.forEach(tag -> manager.persist(tag));
        return tagsToBeSaved;
    }

    @Override
    public Tag getTopUserMostPopularTag() {
        Query nativeQuery = manager.createNativeQuery(FIND_MOST_POPULAR_TOP_USER_TAG, Tag.class);
        return  (Tag)DataAccessUtils.singleResult(nativeQuery.getResultList());
    }
}
