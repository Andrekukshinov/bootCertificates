package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.enums.GiftCertificateStatus;
import com.epam.esm.persistence.model.specification.CertificatesStatusSpecification;
import com.epam.esm.persistence.model.specification.FindByIdInSpecification;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.domain.Specification;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
        List<GiftCertificate> certificate = findBySpecification(specification);
        GiftCertificate giftCertificate = DataAccessUtils.singleResult(certificate);
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

    // for patch
    //GiftCertificate dbCertificate = manager.find(GiftCertificate.class, certificate.getId());
    //        manager.detach(dbCertificate);
    //        LocalDateTime createDate = certificate.getCreateDate();
    //        if (Objects.nonNull(createDate)){
    //            dbCertificate.setCreateDate(createDate);
    //        }
    //        return 0;

    @Override
    public List<GiftCertificate> findBySpecification(Specification<GiftCertificate> mySpecification) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateFrom = query.from(GiftCertificate.class);
        Predicate predicate = mySpecification.toPredicate(giftCertificateFrom, query, cb);
        query.where(predicate);
        TypedQuery<GiftCertificate> exec = manager.createQuery(query);
        return exec.getResultList();
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
