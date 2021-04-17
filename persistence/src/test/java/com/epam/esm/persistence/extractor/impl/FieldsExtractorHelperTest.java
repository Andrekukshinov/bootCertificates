package com.epam.esm.persistence.extractor.impl;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class FieldsExtractorHelperTest {

    @Test
    void testPutToMapIfNotNullShouldFillTheMap() {
        FieldsExtractorHelper extractor = new GiftCertificateFieldsExtractor();
        Map<String, Object> expected = Map.of("1", 1);
        Map<String, Object> actual = new HashMap<>();

        extractor.putToMapIfNotNull(actual, 1, "1");

        assertThat(actual, is(expected));
    }
}
