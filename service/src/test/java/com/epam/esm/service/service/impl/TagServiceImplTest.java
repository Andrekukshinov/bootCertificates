package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagGiftCertificateService;
import com.epam.esm.service.validation.SaveValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagServiceImplTest {
    private static final Long CERTIFICATE_ID_DEFAULT_ID = 1L;

    private static final Tag PEOPLE_TAG = new Tag(CERTIFICATE_ID_DEFAULT_ID, "PEOPLE");
    private static final Set<Tag> TAGS = Set.of(PEOPLE_TAG);

    private static final TagDto PEOPLE_TAG_DTO = new TagDto(1L, "PEOPLE");
    private static final Set<TagDto> TAGS_DTO = Set.of(PEOPLE_TAG_DTO);


    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagGiftCertificateService tagCertificateService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SaveValidator<TagDto> updateValidator;

    @InjectMocks
    private TagServiceImpl service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTagShouldVerifyRepositoryCallWhenObjectValid() throws ValidationException {
        when(tagRepository.findByName(any())).thenReturn(Optional.empty());
        when(modelMapper.map(any(), any())).thenReturn((PEOPLE_TAG));

        service.saveTag(PEOPLE_TAG_DTO);

        verify(tagRepository, times(1)).save(any());
        verify(tagRepository, times(1)).findByName(any());
    }

    @Test
    void testSaveTagShouldThrowValidationExceptionWhenInvalidObject() throws ValidationException {
        when(tagRepository.findByName(any())).thenReturn(Optional.empty());
        doThrow(ValidationException.class).when(updateValidator).validate(any());
        when(modelMapper.map(any(), any())).thenReturn(PEOPLE_TAG);

        assertThrows(ValidationException.class, () -> service.saveTag(PEOPLE_TAG_DTO));

        verify(tagRepository, times(0)).save(any());
        verify(tagRepository, times(0)).findByName(any());
    }

    @Test
    void testSaveTagShouldEntityAlreadyExistsExceptionWhenTagExists() throws ValidationException {
        when(tagRepository.findByName(any())).thenReturn(Optional.of(PEOPLE_TAG));
        when(modelMapper.map(any(), any())).thenReturn((PEOPLE_TAG));
        when(modelMapper.map(any(), any())).thenReturn(PEOPLE_TAG);

        assertThrows(EntityAlreadyExistsException.class, () -> service.saveTag(PEOPLE_TAG_DTO));

        verify(tagRepository, times(0)).save(any());
        verify(tagRepository, times(1)).findByName(any());
    }

    @Test
    void testDeleteTagShouldInvokeServiceAndRepository() {
        service.deleteTag(CERTIFICATE_ID_DEFAULT_ID);

        verify(tagRepository, times(1)).delete(any());
        verify(tagCertificateService, times(1)).deleteTags(any());
    }

    @Test
    void testGetTagShouldReturnTagWhenFound() {
        when(tagRepository.findById(any())).thenReturn(Optional.of(PEOPLE_TAG));
        when(modelMapper.map(any(), any())).thenReturn(PEOPLE_TAG_DTO);

        TagDto tag = service.getTag(CERTIFICATE_ID_DEFAULT_ID);

        assertThat(tag, is(PEOPLE_TAG_DTO));
        verify(tagRepository, times(1)).findById(any());
    }

    @Test
    void testGetTagShouldThrowEntityNotFoundExceptionWhenNotFound() {
        when(tagRepository.findById(any())).thenThrow(new EntityNotFoundException());
        when(modelMapper.map(any(), any())).thenReturn(PEOPLE_TAG_DTO);

        assertThrows(EntityNotFoundException.class, () -> service.getTag(CERTIFICATE_ID_DEFAULT_ID));

        verify(tagRepository, times(1)).findById(any());
    }
}
