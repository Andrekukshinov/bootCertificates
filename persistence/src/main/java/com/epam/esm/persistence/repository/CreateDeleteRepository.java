package com.epam.esm.persistence.repository;

/**
 * Interface for performing create read delete operations with entities from data source
 *
 * @param <T> object type to work with
 */
public interface CreateDeleteRepository<T> extends ReadOperationRepository<T> {

    /**
     * Method for saving entity with specified data source
     *
     * @param t object to be saved
     * @return saved id
     */
    T save(T t);


    /**
     * Method for deleting object from data source
     *
     * @param id of object to be deleted by
     * @return amount of rows deleted from data source
     */
    int delete(Long id);
}
