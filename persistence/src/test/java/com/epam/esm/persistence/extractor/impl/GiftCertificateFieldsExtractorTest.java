package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class GiftCertificateFieldsExtractorTest {

    private static final String ID = "id";
    private static final String CREATE_DATE = "create_date";
    private static final String DESCRIPTION = "description";
    private static final String DURATION = "duration";
    private static final String NAME = "name";
    private static final String LAST_UPDATE = "last_update_date";
    private static final String PRICE = "price";

    private static final long LONG_VAL = 1L;
    private static final String STRING_VAL = "1";
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final BigDecimal PRICE_VAL = new BigDecimal(3);

    private static final GiftCertificate OBJECT =
            GiftCertificate.getBuilder()
                    .setId(LONG_VAL)
                    .setCreateDate(DATE)
                    .setLastUpdateDate(DATE)
                    .setName(STRING_VAL)
                    .setDescription(STRING_VAL)
                    .setPrice(PRICE_VAL)
                    .setDuration(1)
                    .build();

    @Test
    void testGetFieldsValuesMapShouldBuildMapWihAllObjects() {
        GiftCertificateFieldsExtractor extractor = new GiftCertificateFieldsExtractor();
        Map<String, Object> actual = extractor.getFieldsValuesMap(OBJECT);
        Map<String, Object> expected = Map.of(
                ID, LONG_VAL,
                CREATE_DATE, DATE,
                DESCRIPTION, STRING_VAL,
                DURATION, Integer.valueOf(STRING_VAL),
                NAME, STRING_VAL,
                LAST_UPDATE, DATE,
                PRICE, PRICE_VAL
        );

        assertThat(actual, is(expected));
    }

    @Test
    void testGetFieldsValuesMapShouldBuildMapWihNoObjects() {
        GiftCertificateFieldsExtractor extractor = new GiftCertificateFieldsExtractor();
        Map<String, Object> actual = extractor.getFieldsValuesMap(new GiftCertificate());
        Map<String, Object> expected = Map.of();

        assertThat(actual, is(expected));
    }
}
