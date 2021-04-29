package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.entity.enums.GiftCertificateStatus;
import com.epam.esm.persistence.model.specification.CertificateDescriptionSpecification;
import com.epam.esm.persistence.model.specification.CertificateNameSpecification;
import com.epam.esm.persistence.model.specification.CertificatesStatusSpecification;
import com.epam.esm.persistence.model.specification.EmptySpecification;
import com.epam.esm.persistence.model.specification.FindByIdInSpecification;
import com.epam.esm.persistence.model.specification.GiftCertificateTagNamesSpecification;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.model.RequestParams;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.validation.SaveValidator;
import com.epam.esm.service.validation.UpdateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String WRONG_CERTIFICATE = "certificate with id = %d not found";

    private final GiftCertificateRepository certificateRepository;
    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final SaveValidator<GiftCertificateTagDto> saveValidator;
    private final UpdateValidator<GiftCertificateTagDto> updateValidator;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagService tagService, ModelMapper modelMapper, SaveValidator<GiftCertificateTagDto> saveValidator, UpdateValidator<GiftCertificateTagDto> updateValidator) {
        this.certificateRepository = certificateRepository;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.saveValidator = saveValidator;
        this.updateValidator = updateValidator;
    }

    @Transactional
    @Override
    public GiftCertificateTagDto save(GiftCertificateTagDto certificateDto) throws ValidationException {
        saveValidator.validate(certificateDto);
        GiftCertificate certificate = modelMapper.map(certificateDto, GiftCertificate.class);
        saveCertificateTags(certificate);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificate.setStatus(GiftCertificateStatus.ACTIVE);
        GiftCertificate saved = certificateRepository.save(certificate);
        return modelMapper.map(saved, GiftCertificateTagDto.class);
    }

    @Override
    public GiftCertificateTagDto getCertificateWithTagsById(Long id) {
        Optional<GiftCertificate> certificateOptional = certificateRepository.findById(id);
        GiftCertificate certificate = certificateOptional
                .orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_CERTIFICATE, id)));
        return modelMapper.map(certificate, GiftCertificateTagDto.class);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = certificateRepository.findById(id);
        optionalGiftCertificate.ifPresent(giftCertificate -> certificateRepository.delete(id));
    }

    @Override
    @Transactional
    public GiftCertificateTagDto updateCertificate(GiftCertificateTagDto certificateDto, Long updateId) throws ValidationException {
        certificateRepository.findById(updateId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_CERTIFICATE, updateId)));
        certificateDto.setId(updateId);
        updateValidator.validate(certificateDto);
        GiftCertificate certificate = modelMapper.map(certificateDto, GiftCertificate.class);
        certificate.setLastUpdateDate(LocalDateTime.now());
        saveCertificateTags(certificate);
        GiftCertificate updated = certificateRepository.update(certificate);
        return modelMapper.map(updated, GiftCertificateTagDto.class);
    }

    private void saveCertificateTags(GiftCertificate certificate) {
        Set<Tag> certificateTags = certificate.getTags();
        if (certificateTags != null && !certificateTags.isEmpty()) {
            Set<Tag> savedTags = tagService.saveAll(certificateTags);
            certificate.setTags(savedTags);
        }
    }

    @Override
    public Page<GiftCertificateTagDto> getBySpecification(RequestParams params, Pageable pageable) {
        Page<GiftCertificate> certificates = getCertificatesBySpecification(params, pageable);
        return certificates.map(certificate -> modelMapper.map(certificate, GiftCertificateTagDto.class));
    }

    @Override
    public Page<GiftCertificate> getCertificatesBySpecification(RequestParams params, Pageable pageable) {
        Specification<GiftCertificate> specification = getGiftCertificateSpecification(params);
        return certificateRepository.findBySpecification(specification, pageable);
    }

//    @Override
//    public List<GiftCertificate> getActiveCertificatesBySpecification(RequestParams params) {
//        Specification<GiftCertificate> specification = getGiftCertificateSpecification(params);
//        List<GiftCertificate> certificates = certificateRepository.findBySpecification(specification);
//        return certificates;
//    }

    private Specification<GiftCertificate> getGiftCertificateSpecification(RequestParams params) {
        List<Specification<GiftCertificate>> specifications = new ArrayList<>();
        List<Long> ids = params.getIds();
        Specification<GiftCertificate> activeCertificates
                = new CertificatesStatusSpecification(GiftCertificateStatus.ACTIVE);
        specifications.add(activeCertificates);
        String description = params.getCertificateDescription();
        String name = params.getCertificateName();
        Set<String> tagNames = params.getTagNames();
        if(!Objects.isNull(tagNames)){
            Specification<GiftCertificate> tagNamesSpecification = new GiftCertificateTagNamesSpecification(tagNames);
            specifications.add(tagNamesSpecification);
        }
        if(!Objects.isNull(description)){
            Specification<GiftCertificate> descriptionSpecification = new CertificateDescriptionSpecification(description);
            specifications.add(descriptionSpecification);
        }
        if(!Objects.isNull(name)){
            Specification<GiftCertificate> certificateNameSpecification = new CertificateNameSpecification(name);
            specifications.add(certificateNameSpecification);
        }
        if(!Objects.isNull(ids)){
            Specification<GiftCertificate> idSpecification = new FindByIdInSpecification<>(ids);
            specifications.add(idSpecification);
        }
        return specifications
                .stream()
                .reduce(Specification::and)
                .orElse(new EmptySpecification<>());
    }

}
