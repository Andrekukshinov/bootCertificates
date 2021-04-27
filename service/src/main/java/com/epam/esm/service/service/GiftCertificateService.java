package com.epam.esm.service.service;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.model.RequestParams;

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
    GiftCertificateTagDto save(GiftCertificateTagDto certificate) throws ValidationException;

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
    GiftCertificateTagDto updateCertificate(GiftCertificateTagDto certificateDto, Long updateId) throws ValidationException;

    /**
     * Method that returns list of GiftCertificateTag dto entities based on
     * received specification dto object
     *
     * @param specification to find object with
     * @return list of GiftCertificateTag dto entity with specified id
     * @throws com.epam.esm.service.exception.EntityNotFoundException if entity with id not exists
     */
    List<GiftCertificateTagDto> getBySpecification(RequestParams params);

    List<GiftCertificate> getCertificatesBySpecification(RequestParams params);
    List<GiftCertificate> getActiveCertificatesBySpecification(RequestParams params);
}
