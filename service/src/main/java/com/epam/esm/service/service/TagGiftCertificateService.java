package com.epam.esm.service.service;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.service.dto.TagDto;

import java.util.Set;

/**
 * Interface for performing business logics for interacting with relations of tag and certificate
 */
public interface TagGiftCertificateService {

    /**
     * Method that performs save action for given certificate id and set of this certificate tags
     *
     * @param certificateId   to be saved
     * @param certificateTags tags from certificate with id = certificateId to be saved
     */
    void saveCertificateTags(Long certificateId, Set<Tag> certificateTags);

    /**
     * Method that performs delete action of tags the from certificate with specified id
     *
     * @param certificateId id of certificate object to delete tags
     */
    void deleteCertificateTags(Long certificateId);

    /**
     * Method that deletes tag with specified id from all certificates
     *
     * @param tagId id objects to be deleted from certificates
     */
    void deleteTags(Long tagId);

    /**
     * Method for receiving all certificate tags by specified id
     *
     * @param certificateId to look for tags with
     * @return set of certificate tags
     */
    Set<TagDto> findCertificateTags(Long certificateId);

}
