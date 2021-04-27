package com.epam.esm.persistence.repository;

import java.util.Optional;

public interface ReadOperationRepository<T> {

    /**
     * Method for receiving object from data source if found or Optional.empty() if not
     *
     * @param id of object to be found with
     * @return Optional of object
     */
    Optional<T> findById(Long id);
}
