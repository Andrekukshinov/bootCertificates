package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.persistence.model.specification.Specification;


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
     * @param specification to search and sort certificates with
     * @return list of found certificates
     */
    Page<GiftCertificate> findBySpecification(Specification<GiftCertificate> specification, Pageable pageable);

    GiftCertificate partialUpdate(Long certificateId, GiftCertificate toBeUpdated);
}
