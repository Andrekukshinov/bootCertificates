package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.model.TagGiftCertificateId;

import java.util.List;
import java.util.Set;

/**
 * Interface for interaction with many-to-many relation table
 */
public interface TagGiftCertificateRepository {
    /**
     * Method for saving relation between tag and certificate entity
     *
     * @param tagGiftCertificateIds id relation to be saved
     */
    void saveGiftCertificateTags(List<TagGiftCertificateId> tagGiftCertificateIds);

    /**
     * Method for deleting all tags of specified certificate
     *
     * @param certificateId id to delete tags with
     */
    void deleteCertificateTags(Long certificateId);

    /**
     * Method for finding certificate tags from data source
     *
     * @param certificateId id tags to be found with
     * @return set of certificate tags
     */
    Set<Tag> findCertificateTags(Long certificateId);

    /**
     * Method for deleting tag from all certificates in data source
     *
     * @param tagId id to be delted with
     */
    void deleteTagFromCertificates(Long tagId);

}
