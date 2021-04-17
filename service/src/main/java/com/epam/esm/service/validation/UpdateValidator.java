package com.epam.esm.service.validation;

import com.epam.esm.service.exception.ValidationException;

/**
 * Interface for objects update validation
 *
 * @param <T> - object type to be validated
 */
public interface UpdateValidator<T> {
    /**
     * Method that performs validation on updating logic for received object
     *
     * @param object to be validated
     * @throws ValidationException in case of validation errors occur
     */
    void validate(T object) throws ValidationException;
}
