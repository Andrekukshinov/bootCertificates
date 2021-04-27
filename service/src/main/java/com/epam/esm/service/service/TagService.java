package com.epam.esm.service.service;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;

import java.util.Set;

/**
 * Interface for performing business logics for Tag and Tag Dtos
 */
public interface TagService {
    /**
     * Method that performs validation for given dto object and saving of tag
     *
     * @param tag dto to be validated and performed logics with
     * @throws ValidationException in case of validation error occur
     */
    TagDto save(TagDto tag) throws ValidationException;

    /**
     * Method that deletes object from system
     *
     * @param tagId object id to perform logics with
     */
    void deleteTag(Long tagId);

    /**
     * Method that returns tag dto based on received id
     *
     * @param id to find tag with
     * @return tag dto entity with specified id
     * @throws com.epam.esm.service.exception.EntityNotFoundException if entity with id not exists
     */
    TagDto getTag(Long id);

    /**
     * Method for returning list of all tag dtos that are present in the system
     * @return list of tag dtos
     */
    Set<TagDto> getAll();

    Set<Tag> saveAll(Set<Tag> tagToBeSaved);

    TagDto getTopUserMostPopularTag();
}
