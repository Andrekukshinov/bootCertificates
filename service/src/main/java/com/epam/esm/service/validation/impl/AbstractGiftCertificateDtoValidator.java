package com.epam.esm.service.validation.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public abstract class AbstractGiftCertificateDtoValidator {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    protected void validateNameValue(String name) throws ValidationException {
        if (name != null && (name.length() > MAX_NAME_LENGTH || name.isEmpty())) {
            throw new ValidationException("name cannot be longer than 50 symbols!");
        }
    }

    protected void validateDescriptionValue(String description) throws ValidationException {
        if (description != null && (description.length() > MAX_DESCRIPTION_LENGTH || description.isEmpty())) {
            throw new ValidationException("description cannot be longer than 255 symbols!");
        }
    }

    protected void validateCreateDateValue(LocalDateTime createDate) throws ValidationException {
        if (createDate != null) {
            throw new ValidationException("create date cannot be specified!");
        }
    }

    protected void validateLastUpdateDateValue(LocalDateTime lastUpdateDate) throws ValidationException {
        if (lastUpdateDate != null) {
            throw new ValidationException("last update date cannot be specified!");
        }
    }

    protected void validateDurationValue(Integer duration) throws ValidationException {
        if (duration != null && duration < 0) {
            throw new ValidationException("duration cannot be negative!");
        }
    }

    protected void validatePriceValue(BigDecimal price) throws ValidationException {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("price cannot be negative!");
        }
    }

    protected abstract void validateCertificateTags(Set<TagDto> tags) throws ValidationException;
}
