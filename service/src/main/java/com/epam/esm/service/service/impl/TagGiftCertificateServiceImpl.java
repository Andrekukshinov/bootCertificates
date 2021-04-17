package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.model.TagGiftCertificateId;
import com.epam.esm.persistence.repository.TagGiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagGiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagGiftCertificateServiceImpl implements TagGiftCertificateService {

    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final TagGiftCertificateRepository tagCertificateRepository;

    @Autowired
    public TagGiftCertificateServiceImpl(ModelMapper modelMapper, TagRepository tagRepository, TagGiftCertificateRepository tagCertificateRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.tagCertificateRepository = tagCertificateRepository;
    }

    @Override
    public void saveCertificateTags(Long certificateId, Set<Tag> certificateTags) {
        Set<Tag> foundTags = getFoundTags(certificateTags);
        Set<Long> foundTagsIds = foundTags
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
        List<Long> savedAbsentTagsIds = getSavedAbsentTagsIds(certificateTags, foundTags);
        foundTagsIds.addAll(savedAbsentTagsIds);
        List<TagGiftCertificateId> ids = foundTagsIds
                .stream()
                .map(tagId -> new TagGiftCertificateId(certificateId, tagId))
                .collect(Collectors.toList());
        tagCertificateRepository.saveGiftCertificateTags(ids);
    }

    private Set<Tag> getFoundTags(Set<Tag> tags) {
        Set<String> tagNames = getSetTagNames(tags);
        return tagRepository.findTagsByNames(tagNames);
    }

    private List<Long> getSavedAbsentTagsIds(Set<Tag> tags, Set<Tag> foundTags) {
        Set<String> tagNames = getSetTagNames(foundTags);
        Set<Tag> absentTags = tags
                .stream()
                .filter(tag -> !tagNames.contains(tag.getName()))
                .collect(Collectors.toSet());
        List<Long> result = new ArrayList<>();
        for (Tag tag : absentTags) {
            result.add(tagRepository.save(tag));
        }
        return result;
    }

    private Set<String> getSetTagNames(Set<Tag> tags) {
        return tags
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteCertificateTags(Long certificateId) {
        tagCertificateRepository.deleteCertificateTags(certificateId);
    }

    @Override
    public void deleteTags(Long tagId) {
        tagCertificateRepository.deleteTagFromCertificates(tagId);
    }

    @Override
    public Set<TagDto> findCertificateTags(Long certificateId) {
        Set<Tag> certificateTagsDto = tagCertificateRepository.findCertificateTags(certificateId);
        return certificateTagsDto
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }

}
