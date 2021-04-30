package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.dto.certificate.GiftCertificatesNoTagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    private static final LocalDateTime DATE = LocalDateTime.parse("2021-03-25T00:00:00");
    private static final String SPA = "spa";
    private static final String FAMILY_CERTIFICATE = "family certificate";

    private static final long CERTIFICATE_ID_DEFAULT_ID = 1L;
    private static final Tag PEOPLE_TAG = new Tag(CERTIFICATE_ID_DEFAULT_ID, "PEOPLE");
    private static final Set<Tag> TAGS = Set.of(PEOPLE_TAG);

    private static final TagDto PEOPLE_TAG_DTO = new TagDto(1L, "PEOPLE");
    private static final Set<TagDto> TAGS_DTO = Set.of(PEOPLE_TAG_DTO);

    private static final GiftCertificate FIRST = GiftCertificate.getBuilder()
            .setId(CERTIFICATE_ID_DEFAULT_ID)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .setTags(TAGS)
            .build();

    private static final GiftCertificate EMPTY_TAGS_CERTIFICATE = GiftCertificate.getBuilder()
            .setId(CERTIFICATE_ID_DEFAULT_ID)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .setTags(new HashSet<>())
            .build();

    private static final GiftCertificate NO_TAGS_CERTIFICATE = GiftCertificate.getBuilder()
            .setId(CERTIFICATE_ID_DEFAULT_ID)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .build();

    private static final GiftCertificateTagDto DTO = GiftCertificateTagDto.getBuilder()
            .setId(CERTIFICATE_ID_DEFAULT_ID)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .setTags(TAGS_DTO)
            .build();

    private static final GiftCertificatesNoTagDto NO_TAGS_DTO = GiftCertificatesNoTagDto.getBuilder()
            .setId(CERTIFICATE_ID_DEFAULT_ID)
            .setCreateDate(DATE)
            .setLastUpdateDate(DATE)
            .setName(SPA)
            .setDescription(FAMILY_CERTIFICATE)
            .setPrice(new BigDecimal(754))
            .setDuration(3)
            .build();

    @Mock
    private GiftCertificateRepository certificateRepository;
//    @Mock
//    private UpdateValidator<GiftCertificateTagDto> updateValidator;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TagService tagCertificateService;

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveShouldSaveCertificateWithTagsWhenEntityValid() throws ValidationException {
        when(modelMapper.map(any(), any())).thenReturn(FIRST);
        when(certificateRepository.save(any()))
                .thenAnswer((object) -> object.getArgument(0, GiftCertificate.class).getId());

        service.save(DTO);

        verify(tagCertificateService, times(1)).saveAll(any());
        verify(certificateRepository, times(1)).save(FIRST);
        verify(modelMapper, times(1)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testSaveShouldSaveCertificateNotTagsWhenEntityValid() throws ValidationException {
        when(modelMapper.map(any(), any())).thenReturn(NO_TAGS_CERTIFICATE);
        when(certificateRepository.save(any()))
                .thenAnswer((object) -> object.getArgument(0, GiftCertificate.class).getId());

        service.save(DTO);

        verify(tagCertificateService, times(0)).saveAll(any());
        verify(certificateRepository, times(1)).save(NO_TAGS_CERTIFICATE);
        verify(modelMapper, times(1)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testSaveShouldThrowExceptionWhenObjectInvalid() throws ValidationException {
        when(certificateRepository.findById(any())).thenReturn(Optional.of(FIRST));
        when(certificateRepository.save(any()))
                .thenAnswer((object) -> object.getArgument(0, GiftCertificate.class).getId());

        ValidationException validationException = assertThrows(ValidationException.class, () -> service.save(DTO));

        assertThat(validationException.getClass(), is(ValidationException.class));
        verify(tagCertificateService, times(0)).saveAll(any());
        verify(certificateRepository, times(0)).update(FIRST);
        verify(modelMapper, times(0)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testGetCertificateWithTagsByIdShouldReturnDtoObjectWhenFound() throws ValidationException {
        when(certificateRepository.findById(any())).thenReturn(Optional.of(FIRST));
        when(modelMapper.map(FIRST, GiftCertificateTagDto.class)).thenReturn(DTO);
        when(modelMapper.map(PEOPLE_TAG, Tag.class)).thenReturn(PEOPLE_TAG);
        when(tagCertificateService.saveAll(any())).thenReturn(TAGS);

        GiftCertificateTagDto found = service.getCertificateWithTagsById(CERTIFICATE_ID_DEFAULT_ID);

        assertThat(found, is(DTO));

    }

    @Test
    void testGetCertificateWithTagsByIdShouldThrowEntityNotFoundExceptionWhenNotFound() {
        when(certificateRepository.findById(CERTIFICATE_ID_DEFAULT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> service.getCertificateWithTagsById(CERTIFICATE_ID_DEFAULT_ID));

        assertThat(entityNotFoundException.getMessage(), is("certificate with id = 1 not found"));
    }

    @Test
    void testUpdateCertificateShouldInvokeSaveCertificateTagsWhenTagsFound() throws ValidationException {
        when(modelMapper.map(any(), any())).thenReturn(FIRST);
        when(certificateRepository.update(any())).thenReturn(FIRST);
        when(certificateRepository.findById(any())).thenReturn(Optional.of(FIRST));

        service.updateCertificate(DTO, CERTIFICATE_ID_DEFAULT_ID);

        verify(tagCertificateService, times(1)).saveAll(any());
        verify(certificateRepository, times(1)).update(FIRST);
        verify(modelMapper, times(1)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testUpdateCertificateShouldInvokeDeleteCertificateTagsWhenTagsNotFound() throws ValidationException {
        when(modelMapper.map(any(), any())).thenReturn(EMPTY_TAGS_CERTIFICATE);
        when(certificateRepository.findById(any())).thenReturn(Optional.of(FIRST));
        when(certificateRepository.update(any())).thenReturn(EMPTY_TAGS_CERTIFICATE);

        service.updateCertificate(DTO, CERTIFICATE_ID_DEFAULT_ID);

        verify(tagCertificateService, times(1)).saveAll(any());
        verify(certificateRepository, times(1)).update(EMPTY_TAGS_CERTIFICATE);
        verify(modelMapper, times(1)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testUpdateCertificateShouldThrowExceptionWhenObjectInvalid() throws ValidationException {
        when(certificateRepository.update(any()))
                .thenAnswer((object) -> object.getArgument(0, GiftCertificate.class).getId());
        when(certificateRepository.findById(any())).thenReturn(Optional.of(FIRST));

        assertThrows(ValidationException.class, () -> service.updateCertificate(DTO, CERTIFICATE_ID_DEFAULT_ID));

        verify(tagCertificateService, times(0)).saveAll(any());
        verify(certificateRepository, times(0)).update(FIRST);
        verify(modelMapper, times(0)).map(DTO, GiftCertificate.class);
    }

    @Test
    void testUpdateCertificateShouldThrowExceptionWhenIdInvalid() throws ValidationException {
        when(certificateRepository.update(any()))
                .thenAnswer((object) -> object.getArgument(0, GiftCertificate.class).getId());
        when(certificateRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateCertificate(DTO, CERTIFICATE_ID_DEFAULT_ID));

        verify(tagCertificateService, times(0)).saveAll(any());
        verify(certificateRepository, times(0)).update(FIRST);
        verify(certificateRepository, times(1)).findById(CERTIFICATE_ID_DEFAULT_ID);
        verify(modelMapper, times(0)).map(DTO, GiftCertificate.class);
    }

    //fixme
//    @Test
//    void testGetBySpecificationShouldReturnListOfDtoEntitiesWhenFound() {
//        DeprecatedSpecificationDto searchDeprecatedSpecificationDto = new DeprecatedSpecificationDto();
//        SearchSpecification search = new SearchSpecification("people", "e", "a");
//        SortSpecification sort = new SortSpecification(SortDirection.ASC, SortDirection.ASC);
//        when(modelMapper.map(searchDeprecatedSpecificationDto, SearchSpecification.class)).thenReturn(search);
//        when(modelMapper.map(searchDeprecatedSpecificationDto, SortSpecification.class)).thenReturn(sort);
//        when(modelMapper.map(FIRST, GiftCertificatesNoTagDto.class)).thenReturn(NO_TAGS_DTO);
//        when(certificateRepository.findBySpecification(search, sort)).thenReturn(List.of(FIRST));
//
//        List<GiftCertificatesNoTagDto> actual = service.getBySpecification(searchDeprecatedSpecificationDto);
//
//        assertThat(actual, is(List.of(NO_TAGS_DTO)));
//        verify(certificateRepository, times(1)).findBySpecification(search, sort);
//        verify(modelMapper, times(3)).map(any(), any());
//    }

    //fixme
//    @Test
//    void testDeleteCertificateShouldInvokeMethods() {
//        service.deleteCertificate(CERTIFICATE_ID_DEFAULT_ID);
//
//        verify(tagCertificateService, times(1)).deleteCertificateTags(1L);
//        verify(certificateRepository, times(1)).delete(1L);
//    }
}
