package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagGiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagGiftCertificateServiceImplTest {

    private static final long CERTIFICATE_ID_DEFAULT_ID = 1L;
    private static final Tag PEOPLE_TAG = new Tag(CERTIFICATE_ID_DEFAULT_ID, "PEOPLE");
    private static final Set<Tag> TAGS = Set.of(PEOPLE_TAG);
    private static final TagDto PEOPLE_TAG_DTO = new TagDto(1L, "PEOPLE");
    private static final Set<TagDto> TAGS_DTO = Set.of(PEOPLE_TAG_DTO);


    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagGiftCertificateRepository tagCertificateRepository;
    @InjectMocks
    private TagGiftCertificateServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCertificateTagsShouldVerifyTagRepositorySaveCalls() {
        when(tagRepository.findTagsByNames(any())).thenReturn(new HashSet<>());

        service.saveCertificateTags(CERTIFICATE_ID_DEFAULT_ID, TAGS);

        verify(tagRepository, times(1)).findTagsByNames(any());
        verify(tagRepository, times(1)).save(any());
        verify(tagCertificateRepository, times(1)).saveGiftCertificateTags(any());

    }

    @Test
    void testSaveCertificateTagsShouldVerifyTagRepositoryNoCalls() {
        when(tagRepository.findTagsByNames(any())).thenReturn(TAGS);

        service.saveCertificateTags(CERTIFICATE_ID_DEFAULT_ID, TAGS);

        verify(tagRepository, times(1)).findTagsByNames(any());
        verify(tagRepository, times(0)).save(any());
        verify(tagCertificateRepository, times(1)).saveGiftCertificateTags(any());

    }

    @Test
    void testDeleteTagsVerifyTagCertificateCall() {
        service.deleteTags(CERTIFICATE_ID_DEFAULT_ID);

        verify(tagCertificateRepository, times(1)).deleteTagFromCertificates(any());

    }

    @Test
    void testFindCertificateTagsShouldReturnSetDtoTags() {
        when(tagCertificateRepository.findCertificateTags(any())).thenReturn(TAGS);
        when(modelMapper.map(any(), any())).thenReturn(PEOPLE_TAG_DTO);

        Set<TagDto> certificateTags = service.findCertificateTags(CERTIFICATE_ID_DEFAULT_ID);

        assertThat(certificateTags, is(TAGS_DTO));
        verify(tagCertificateRepository, times(1)).findCertificateTags(any());

    }
}
