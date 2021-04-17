package com.epam.esm.service.service;

import com.epam.esm.service.dto.GiftCertificateTagDto;
import com.epam.esm.service.dto.GiftCertificatesNoTagDto;
import com.epam.esm.service.dto.SpecificationDto;
import com.epam.esm.service.exception.ValidationException;

import java.util.List;


/**
 * Interface for performing business logics for GiftCertificates and GiftCertificatesDtos
 */
public interface GiftCertificateService {
    /**
     * Method that performs validation for given dto object and saving of certificate
     *
     * @param certificate dto to be validated and performed logics with
     * @throws ValidationException in case of validation error occur
     */
    void save(GiftCertificateTagDto certificate) throws ValidationException;

    /**
     * Method that returns GiftCertificateTag dto based on received id
     *
     * @param id to find object with
     * @return GiftCertificateTag dto entity with specified id
     * @throws com.epam.esm.service.exception.EntityNotFoundException if entity with id not exists
     */
    GiftCertificateTagDto getCertificateWithTagsById(Long id);

    /**
     * Method that deletes certificate
     *
     * @param certificateId object id to perform logics with
     */
    void deleteCertificate(Long certificateId);

    /**
     * Method that performs validation for given dto object and performs update action
     *
     * @param certificateDto dto to be validated and performed logics with
     * @param updateId       certificate param to be updated by
     * @throws ValidationException in case of validation error occur
     */
    void updateCertificate(GiftCertificateTagDto certificateDto, Long updateId) throws ValidationException;

    /**
     * Method that returns list of GiftCertificateTag dto entities based on
     * received specification dto object
     *
     * @param searchSpecification to find object with
     * @return list of GiftCertificateTag dto entity with specified id
     * @throws com.epam.esm.service.exception.EntityNotFoundException if entity with id not exists
     */
    List<GiftCertificatesNoTagDto> getBySpecification(SpecificationDto searchSpecification);
}
