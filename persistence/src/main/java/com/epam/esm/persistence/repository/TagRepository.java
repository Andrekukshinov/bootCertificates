package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.persistence.model.specification.Specification;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for executing operations with Tag entity within data source
 */
public interface TagRepository extends CreateDeleteRepository<Tag> {
    /**
     * Method for returning set of tags found by names from data source
     *
     * @param tagNames names to look for with
     * @return set of found tags
     */
    Set<Tag> findTagsByNames(Set<String> tagNames);


    Set<Tag> saveAll(Set<Tag> tagsToBeSaved);

    Optional<Tag> findInCertificates(Long id);

    Tag getTopUserMostPopularTag();

    Page<Tag> find(Specification<Tag> orderSpecification, Pageable pageable);

}
