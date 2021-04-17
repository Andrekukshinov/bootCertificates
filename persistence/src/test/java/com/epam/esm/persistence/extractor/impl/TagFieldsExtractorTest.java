package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class TagFieldsExtractorTest {
    private static final String NAME = "name";
    private static final String TAG_ID = "id";

    private static final long ID = 1L;
    private static final Tag OBJECT = new Tag(ID, NAME);

    @Test
    void testGetFieldsValuesMapShouldReturnMapWithAllFields() {
        TagFieldsExtractor extractor = new TagFieldsExtractor();
        Map<String, Object> actual = extractor.getFieldsValuesMap(OBJECT);
        Map<String, Object> expected = Map.of(
                TAG_ID, ID,
                NAME, NAME
        );

        assertThat(actual, is(expected));
    }
}

