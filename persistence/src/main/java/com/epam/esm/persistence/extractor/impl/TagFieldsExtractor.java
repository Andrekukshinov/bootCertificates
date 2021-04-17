package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.extractor.FieldsExtractor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TagFieldsExtractor extends FieldsExtractorHelper implements FieldsExtractor<Tag> {

    private static final String NAME = "name";
    private static final String ID = "id";

    @Override
    public Map<String, Object> getFieldsValuesMap(Tag tag) {
        Map<String, Object> result = new HashMap<>();
        putToMapIfNotNull(result, tag.getName(), NAME);
        putToMapIfNotNull(result, tag.getId(), ID);
        return result;
    }
}
