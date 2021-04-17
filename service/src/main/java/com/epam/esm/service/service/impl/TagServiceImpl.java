package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagGiftCertificateService;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.validation.SaveValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private static final String WRONG_TAG = "tag with id = %d not found";
    private static final String ALREADY_EXISTS_PATTERN = "tag with name %s already exists!";

    private final TagRepository tagRepository;
    private final TagGiftCertificateService tagCertificateService;
    private final ModelMapper modelMapper;
    private final SaveValidator<TagDto> saveValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagGiftCertificateService tagCertificateService, ModelMapper modelMapper, SaveValidator<TagDto> saveValidator) {
        this.tagRepository = tagRepository;
        this.tagCertificateService = tagCertificateService;
        this.modelMapper = modelMapper;
        this.saveValidator = saveValidator;
    }

    @Override
    public void saveTag(TagDto tagDto) throws ValidationException {
        saveValidator.validate(tagDto);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        String name = tag.getName();
        Optional<Tag> tagOptional = tagRepository.findByName(name);
        tagOptional.ifPresent((ignored) -> {
            throw new EntityAlreadyExistsException(String.format(ALREADY_EXISTS_PATTERN, name));
        });
        tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId) {
        tagCertificateService.deleteTags(tagId);
        tagRepository.delete(tagId);
    }

    @Override
    public TagDto getTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        Tag tag = tagOptional.orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_TAG, id)));
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Set<TagDto> getAll() {
        return tagRepository.findAll()
                .stream()
                .map((tag) -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }
}
