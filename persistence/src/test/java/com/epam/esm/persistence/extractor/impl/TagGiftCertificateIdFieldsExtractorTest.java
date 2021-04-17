package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.model.TagGiftCertificateId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class TagGiftCertificateIdFieldsExtractorTest {
    private static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    private static final String TAG_ID = "tag_id";

    private static final long ID = 1L;
    private static final TagGiftCertificateId OBJECT = new TagGiftCertificateId(ID, ID);

    @Test
    void testGetFieldsValuesMapShouldReturnMapWithAllFields() {
        TagGiftCertificateIdFieldsExtractor extractor = new TagGiftCertificateIdFieldsExtractor();
        Map<String, Object> actual = extractor.getFieldsValuesMap(OBJECT);
        Map<String, Object> expected = Map.of(
                GIFT_CERTIFICATE_ID, ID,
                TAG_ID, ID
        );

        assertThat(actual, is(expected));
    }
}
