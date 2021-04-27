package com.epam.esm.service.validation.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.certificate.GiftCertificateTagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.validation.SaveValidator;
import com.epam.esm.service.validation.UpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GiftCertificateTagDtoUpdateValidator extends AbstractGiftCertificateDtoValidator implements UpdateValidator<GiftCertificateTagDto> {

    private final SaveValidator<TagDto> tagDtoUpdateValidator;

    @Autowired
    public GiftCertificateTagDtoUpdateValidator(SaveValidator<TagDto> tagDtoUpdateValidator) {
        this.tagDtoUpdateValidator = tagDtoUpdateValidator;
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
    protected void validateCertificateTags(Set<TagDto> tags) throws ValidationException {
        if (tags == null) {
            return;
        }
        for (TagDto tag : tags) {
            tagDtoUpdateValidator.validate(tag);
        }
    }
}
