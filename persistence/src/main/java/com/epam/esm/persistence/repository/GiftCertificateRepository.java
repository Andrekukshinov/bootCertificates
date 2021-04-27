package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


/**
 * Interface for executing operations with GiftCertificate entity within data source
 */
public interface GiftCertificateRepository extends CreateDeleteRepository<GiftCertificate> {
    /**
     * Method for updating certificate entity in the data source
     *
     * @param certificate object to be updated
     * @return amount of updated rows
     */
    GiftCertificate update(GiftCertificate certificate);


    /**
     * Method for returning list of certificates based on received specifications from data source
     *
     * @param mySpecification to search and sort certificates with
     * @return list of found certificates
     */
    List<GiftCertificate> findBySpecification(Specification<GiftCertificate> mySpecification);
}
