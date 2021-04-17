package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.model.SearchSpecification;
import com.epam.esm.persistence.model.SortSpecification;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.service.dto.GiftCertificateTagDto;
import com.epam.esm.service.dto.GiftCertificatesNoTagDto;
import com.epam.esm.service.dto.SpecificationDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.service.TagGiftCertificateService;
import com.epam.esm.service.validation.SaveValidator;
import com.epam.esm.service.validation.UpdateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String WRONG_CERTIFICATE = "certificate with id = %d not found";

    private final GiftCertificateRepository certificateRepository;
    private final TagGiftCertificateService tagCertificateService;
    private final ModelMapper modelMapper;
    private final SaveValidator<GiftCertificateTagDto> saveValidator;
    private final UpdateValidator<GiftCertificateTagDto> updateValidator;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagGiftCertificateService tagCertificateService, ModelMapper modelMapper, SaveValidator<GiftCertificateTagDto> saveValidator, UpdateValidator<GiftCertificateTagDto> updateValidator) {
        this.certificateRepository = certificateRepository;
        this.tagCertificateService = tagCertificateService;
        this.modelMapper = modelMapper;
        this.saveValidator = saveValidator;
        this.updateValidator = updateValidator;
    }

    @Transactional
    @Override
    public void save(GiftCertificateTagDto certificateDto) throws ValidationException {
        saveValidator.validate(certificateDto);
        GiftCertificate certificate = modelMapper.map(certificateDto, GiftCertificate.class);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        Long certificateId = certificateRepository.save(certificate);
        Set<Tag> tags = certificate.getTags();
        if (tags != null && !tags.isEmpty()) {
            tagCertificateService.saveCertificateTags(certificateId, tags);
        }
    }

    @Override
    public GiftCertificateTagDto getCertificateWithTagsById(Long id) {
        Optional<GiftCertificate> certificateOptional = certificateRepository.findById(id);
        GiftCertificate certificate = certificateOptional
                .orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_CERTIFICATE, id)));
        Set<TagDto> certificateTagsDto = tagCertificateService.findCertificateTags(id);
        Set<Tag> tags = certificateTagsDto
                .stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
        certificate.setTags(tags);
        return modelMapper.map(certificate, GiftCertificateTagDto.class);
    }

    @Transactional
    @Override
    public void deleteCertificate(Long id) {
        tagCertificateService.deleteCertificateTags(id);
        certificateRepository.delete(id);
    }

    @Override
    @Transactional
    public void updateCertificate(GiftCertificateTagDto certificateDto, Long updateId) throws ValidationException {
        certificateRepository.findById(updateId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_CERTIFICATE, updateId)));
        certificateDto.setId(updateId);
        updateValidator.validate(certificateDto);
        GiftCertificate certificate = modelMapper.map(certificateDto, GiftCertificate.class);
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateRepository.update(certificate);
        Set<Tag> tags = certificate.getTags();
        Long certificateId = certificate.getId();
        if (tags != null && !tags.isEmpty()) {
            tagCertificateService.saveCertificateTags(certificateId, tags);
        } else if (tags != null) {
            tagCertificateService.deleteCertificateTags(certificateId);
        }
    }

    @Override
    public List<GiftCertificatesNoTagDto> getBySpecification(SpecificationDto specificationDto) {
        SearchSpecification searchSpecification = modelMapper.map(specificationDto, SearchSpecification.class);
        SortSpecification sortSpecification = modelMapper.map(specificationDto, SortSpecification.class);
        List<GiftCertificate> certificates = certificateRepository.findBySpecification(searchSpecification, sortSpecification);
        return certificates
                .stream()
                .map(certificate -> modelMapper.map(certificate, GiftCertificatesNoTagDto.class))
                .collect(Collectors.toList());
    }

}
