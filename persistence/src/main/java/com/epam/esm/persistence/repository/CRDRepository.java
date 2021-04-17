package com.epam.esm.persistence.repository;

import java.util.Optional;

/**
 * Interface for performing create read delete operations with entities from data source
 *
 * @param <T> object type to work with
 */
public interface CRDRepository<T> {

    /**
     * Method for saving entity with specified data source
     *
     * @param t object to be saved
     * @return saved id
     */
    Long save(T t);

    /**
     * Method for receiving object from data source if found or Optional.empty() if not
     *
     * @param id of object to be found with
     * @return Optional of object
     */
    Optional<T> findById(Long id);

    /**
     * Method for deleting object from data source
     *
     * @param id of object to be deleted by
     * @return amount of rows deleted from data source
     */
    int delete(Long id);
}
