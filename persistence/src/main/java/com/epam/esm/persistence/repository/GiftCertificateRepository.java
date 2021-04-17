package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.model.SearchSpecification;
import com.epam.esm.persistence.model.SortSpecification;

import java.util.List;


/**
 * Interface for executing operations with GiftCertificate entity within data source
 */
public interface GiftCertificateRepository extends CRDRepository<GiftCertificate> {
    /**
     * Method for updating certificate entity in the data source
     *
     * @param certificate object to be updated
     * @return amount of updated rows
     */
    int update(GiftCertificate certificate);

    /**
     * Method for returning list of certificates based on received specifications from data source
     *
     * @param searchSpecification to search certificate with
     * @param sortSpecification   to sort certificate with
     * @return list of found certificates
     */
    List<GiftCertificate> findBySpecification(SearchSpecification searchSpecification, SortSpecification sortSpecification);
}
