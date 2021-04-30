package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.entity.enums.GiftCertificateStatus;
import com.epam.esm.persistence.exception.SortingException;
import com.epam.esm.persistence.model.specification.CertificatesStatusSpecification;
import com.epam.esm.persistence.model.specification.FindByIdInSpecification;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.mapping.PropertyReferenceException;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Repository
@Transactional
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String ACTIVE_CERTIFICATE_CREATE_DATE = "SELECT GC.createDate from GiftCertificate GC WHERE GC.id = :id AND GC.status ='ACTIVE'";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        manager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Specification<GiftCertificate> byId = new FindByIdInSpecification<>(List.of(id));
        Specification<GiftCertificate> specification = byId.and(new CertificatesStatusSpecification(GiftCertificateStatus.ACTIVE));
        Page<GiftCertificate> certificate = findBySpecification(specification, Pageable.unpaged());
        GiftCertificate giftCertificate = DataAccessUtils.singleResult(certificate.getContent());
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public int delete(Long id) {
        findById(id).ifPresent(certificate -> {
            certificate.setStatus(GiftCertificateStatus.DELETED);
            manager.merge(certificate);
        });
        //fixme
        return 1;
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        Query query = manager.createQuery(ACTIVE_CERTIFICATE_CREATE_DATE)
                .setParameter("id", certificate.getId());
        LocalDateTime createDate = (LocalDateTime) query.getSingleResult();
        certificate.setCreateDate(createDate);
        manager.merge(certificate);
        return certificate;
    }

    @Override
    public GiftCertificate partialUpdate(Long certificateId, GiftCertificate toBeUpdated) {
        GiftCertificate found = manager.find(GiftCertificate.class, certificateId);
        setFieldsToUpdate(toBeUpdated, found);
        manager.merge(found);
        return found;
    }

    private void setFieldsToUpdate(GiftCertificate source, GiftCertificate target) {
        LocalDateTime createDate = source.getCreateDate();
        if (Objects.nonNull(createDate)){
            target.setCreateDate(createDate);
        }
        LocalDateTime lastUpdateDate = source.getLastUpdateDate();
        if (Objects.nonNull(lastUpdateDate)){
            target.setLastUpdateDate(lastUpdateDate);
        }
        Set<Tag> tags = source.getTags();
        if (Objects.nonNull(tags)){
            target.setTags(tags);
        }
        String name = source.getName();
        if (Objects.nonNull(name)){
            target.setName(name);
        }
        String description = source.getDescription();
        if (Objects.nonNull(description)){
            target.setDescription(description);
        }
        BigDecimal price = source.getPrice();
        if (Objects.nonNull(price)){
            target.setPrice(price);
        }
        GiftCertificateStatus status = source.getStatus();
        if (Objects.nonNull(status)){
            target.setStatus(status);
        }
    }

    @Override
    public Page<GiftCertificate> findBySpecification(Specification<GiftCertificate> mySpecification, Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateFrom = query.from(GiftCertificate.class);
        Predicate predicate = mySpecification.toPredicate(giftCertificateFrom, query, cb);
        query.where(predicate);
        TypedQuery<GiftCertificate> exec = getPagedQuery(pageable, cb, query, giftCertificateFrom);
        Long lastPage = getLastPage(cb);
        List<GiftCertificate> content = exec.getResultList();
        Page<GiftCertificate> page = new PageImpl<>(content, pageable,lastPage);
        return page;
    }

    private Long getLastPage(CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(GiftCertificate.class)));
        return manager.createQuery(countQuery).getSingleResult();
    }


    private TypedQuery<GiftCertificate> getPagedQuery(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<GiftCertificate> query, Root<GiftCertificate> from) {
        if (pageable.isPaged()) {
            long pageNumber = pageable.getOffset();
            int pageSize = pageable.getPageSize();
            try {
                query.orderBy((QueryUtils.toOrders(pageable.getSort(), from, cb)));
            } catch (PropertyReferenceException e) {
                throw new SortingException(e.getMessage());
            }
            TypedQuery<GiftCertificate> exec = manager.createQuery(query);
            exec.setFirstResult(Math.toIntExact(pageNumber));
            exec.setMaxResults((pageSize));
            return exec;
        }
        return manager.createQuery(query);
    }

//    private List<Order> getOrders(MySpecification mySpecification, CriteriaBuilder cb, Root<GiftCertificate> fromCertificate) {
//        List<Order> orders = new ArrayList<>();
//        SortDirection nameSort = mySpecification.getNameSortDir();
//        if (nameSort != null) {
//            Order nameSortDir = (nameSort.equals(SortDirection.ASC)) ?
//                    cb.asc(fromCertificate.get("name")) :
//                    cb.desc(fromCertificate.get("name"));
//            orders.add(nameSortDir);
//        }
//        SortDirection createDateSort = mySpecification.getCreateDateSortDir();
//        if (createDateSort != null) {
//            Order createDateSortDir = (createDateSort.equals(SortDirection.ASC)) ?
//                    cb.asc(fromCertificate.get("createDate")) :
//                    cb.desc(fromCertificate.get("createDate"));
//            orders.add(createDateSortDir);
//        }
//        return orders;
//    }

}
