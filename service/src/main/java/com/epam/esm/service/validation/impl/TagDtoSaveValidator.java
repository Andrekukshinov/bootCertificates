package com.epam.esm.service.validation.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.validation.SaveValidator;
import org.springframework.stereotype.Component;

@Component
public class TagDtoSaveValidator implements SaveValidator<TagDto> {

    private static final int MAX_NAME_LENGTH = 50;

    @Override
    public void validate(TagDto object) throws ValidationException {
        validateName(object.getName());
    }

    private void validateName(String name) throws ValidationException {
        if (name != null && name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException("name cannot be longer than 50 symbols!");
        }
    }
}
