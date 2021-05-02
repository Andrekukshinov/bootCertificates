package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.model.specification.EmptySpecification;
import com.epam.esm.persistence.model.specification.TagNameSpecification;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DeleteTagInUseException;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.InvalidEntityException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private static final String WRONG_TAG = "tag with id = %d not found";
    private static final String ALREADY_EXISTS_PATTERN = "tag with name %s already exists!";
    private static final String TAG_INVOLVED_MESSAGE = "Tag with id = %s is involved with certificates!";

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final Validator<Tag> validator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper, Validator<Tag> validator) {
        this.tagRepository = tagRepository;

        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public TagDto save(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        String name = tag.getName();
        Page<Tag> tagPage = tagRepository.find(List.of(new TagNameSpecification(tag.getName())), Pageable.unpaged());
        Optional<Tag> tagOptional = Optional.ofNullable(DataAccessUtils.singleResult(tagPage.getContent()));
        tagOptional.ifPresent((ignored) -> {
            throw new EntityAlreadyExistsException(String.format(ALREADY_EXISTS_PATTERN, name));
        });
        Tag saved = tagRepository.save(tag);
        return modelMapper.map(saved, TagDto.class);
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId) {
        Optional<Tag> forDeleting = tagRepository.findInCertificates(tagId);
        forDeleting.ifPresentOrElse(
                tag -> {
                    throw new DeleteTagInUseException(String.format(TAG_INVOLVED_MESSAGE, tagId));
                },
                () -> tagRepository.delete(tagId)
        );
    }

    @Override
    public TagDto getTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        Tag tag = tagOptional.orElseThrow(() -> new EntityNotFoundException(String.format(WRONG_TAG, id)));
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Page<TagDto> getAll(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.find(List.of(new EmptySpecification<>()),pageable);
        return tagPage.map(tag -> modelMapper.map(tag, TagDto.class));
    }

    @Override
    @Transactional
    public Set<Tag> saveAll(Set<Tag> tagToBeSaved) throws ValidationException {
        Set<Tag> result = new HashSet<>();
        for (Tag tag : tagToBeSaved) {
            validator.validate(tag);
            addSavedTag(result, tag);
        }
        return result;
    }

    @Override
    public TagDto getTopUserMostPopularTag() {
        return modelMapper.map(tagRepository.getTopUserMostPopularTag(), TagDto.class);
    }

    //fixme
    // as an option: split into set of tags (all with id and the rest) => findAllByIds => compare size before
    // repo call and after if size differs => iterate over bd tags and find extra id + throw exception
    // with invalid id
    // the other set (with name) save if absent, others to be found and persistent
    private void addSavedTag(Set<Tag> result, Tag tag) {
        Long tagId = tag.getId();
        String name = tag.getName();
        if (tagId != null) {
            Optional<Tag> optionalTag = tagRepository.findById(tagId);
            optionalTag.ifPresentOrElse(
                    result::add,
                    () -> {throw new InvalidEntityException("invalid tag with id = " + tagId);}
            );
        } else {
            TagNameSpecification specification = new TagNameSpecification(name);
            Page<Tag> tagPage = tagRepository.find(List.of(specification), Pageable.unpaged());
            Optional<Tag> optionalTag = Optional.ofNullable(DataAccessUtils.singleResult(tagPage.getContent()));
            optionalTag.ifPresentOrElse(
                    result::add,
                    () -> {
                        Tag saved = tagRepository.save(tag);
                        result.add(saved);
                    }
            );
        }
    }

//    @Override
//    @Transactional
//    public Set<TagDto> saveAll(Set<Tag> tagToBeSaved) {
////        reslut
//
//        Set<String> tagNames = tagToBeSaved
//                .stream()
//                .map(Tag::getName)
//                .collect(Collectors.toSet());
//        Set<Tag> repositoryTags = tagRepository.findTagsByNames(tagNames);
//
//
//
////        tags.fe()
////        if(id!=null)
////        {
////            tagRepository.findById(id)
////        } else {
////            tagRepository.findByName(id)
////        }
//
//        Set<String> repositoryTagsNames = repositoryTags
//                .stream()
//                .map(Tag::getName)
//                .collect(Collectors.toSet());
//
//        Set<Tag> absentTags = tagToBeSaved
//                .stream()
//                .filter(tag -> !repositoryTagsNames.contains(tag.getName()))
//                .collect(Collectors.toSet());
//        Set<Tag> savedTags = tagRepository.saveAll(absentTags);
//        Set<Tag> result = new HashSet<>(savedTags);
//        result.addAll(repositoryTags);
//        return result.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toSet());
//    }
}
//  @Override
//    @Transactional
//    public Set<TagDto> saveAll(Set<Tag> tagToBeSaved) {
//        Set<Tag> result = new HashSet<>();
//
//        for (Tag tag : tagToBeSaved) {
//            addSavedTag(result, tag);
//        }
//        return result
//                .stream()
//                .map(tag -> modelMapper.map(tag, TagDto.class))
//                .collect(Collectors.toSet());
//    }
//
//    private void addSavedTag(Set<Tag> result, Tag tag) {
//        Long tagId = tag.getId();
//        String name = tag.getName();
//        if (tagId != null) {
//            Optional<Tag> optionalTag = tagRepository.findById(tagId);
//            optionalTag.ifPresentOrElse(
//                    result::add,
//                    () -> {throw new InvalidEntityException("invalid tag with id = " + tagId);}
//            );
//        } else {
//            Optional<Tag> optionalTag = tagRepository.findByName(name);
//            optionalTag.ifPresentOrElse(
//                    result::add,
//                    () -> {
//                        Tag saved = tagRepository.save(tag);
//                        result.add(saved);
//                    }
//            );
//        }
//    }
