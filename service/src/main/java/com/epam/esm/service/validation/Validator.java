package com.epam.esm.service.validation;

import com.epam.esm.service.exception.ValidationException;

public interface Validator<T> {
    void validate(T object) throws ValidationException;
}
