package com.epam.esm.service.validation.impl;

import com.epam.esm.service.dto.GiftCertificateTagDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.validation.SaveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class GiftCertificateTagDtoSaveValidator extends AbstractGiftCertificateDtoValidator implements SaveValidator<GiftCertificateTagDto> {

    private final SaveValidator<TagDto> tagDtoSaveValidator;

    @Autowired
    public GiftCertificateTagDtoSaveValidator(SaveValidator<TagDto> tagDtoSaveValidator) {
        this.tagDtoSaveValidator = tagDtoSaveValidator;
    }

    @Override
    public void validate(GiftCertificateTagDto object) throws ValidationException {
        validateNameValue(object.getName());
        validateDescriptionValue(object.getDescription());
        validateCreateDateValue(object.getCreateDate());
        validateLastUpdateDateValue(object.getLastUpdateDate());
        validateDurationValue(object.getDuration());
        validatePriceValue(object.getPrice());
        validateCertificateTags(object.getTags());
    }

    @Override
    protected void validateNameValue(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("name cannot be null");
        }
        super.validateNameValue(name);
    }

    @Override
    protected void validateDescriptionValue(String description) throws ValidationException {
        if (description == null) {
            throw new ValidationException("description cannot be null");
        }
        super.validateDescriptionValue(description);
    }

    @Override
    protected void validateDurationValue(Integer duration) throws ValidationException {
        if (duration == null) {
            throw new ValidationException("duration cannot be null");
        }
        super.validateDurationValue(duration);
    }

    @Override
    protected void validatePriceValue(BigDecimal price) throws ValidationException {
        if (price == null) {
            throw new ValidationException("price cannot be null");
        }
        super.validatePriceValue(price);
    }

    @Override
    protected void validateCertificateTags(Set<TagDto> tags) throws ValidationException {
        if (tags == null) {
            return;
        }
        for (TagDto tag : tags) {
            tagDtoSaveValidator.validate(tag);
        }
    }
}
