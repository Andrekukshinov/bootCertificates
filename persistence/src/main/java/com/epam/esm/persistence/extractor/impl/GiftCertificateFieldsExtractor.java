package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.extractor.FieldsExtractor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GiftCertificateFieldsExtractor extends FieldsExtractorHelper implements FieldsExtractor<GiftCertificate> {

    private static final String ID = "id";
    private static final String CREATE_DATE = "create_date";
    private static final String DESCRIPTION = "description";
    private static final String DURATION = "duration";
    private static final String NAME = "name";
    private static final String LAST_UPDATE = "last_update_date";
    private static final String PRICE = "price";

    @Override
    public Map<String, Object> getFieldsValuesMap(GiftCertificate certificate) {
        Map<String, Object> result = new HashMap<>();
        putToMapIfNotNull(result, certificate.getId(), ID);
        putToMapIfNotNull(result, certificate.getLastUpdateDate(), LAST_UPDATE);
        putToMapIfNotNull(result, certificate.getCreateDate(), CREATE_DATE);
        putToMapIfNotNull(result, certificate.getDuration(), DURATION);
        putToMapIfNotNull(result, certificate.getName(), NAME);
        putToMapIfNotNull(result, certificate.getDescription(), DESCRIPTION);
        putToMapIfNotNull(result, certificate.getPrice(), PRICE);
        return result;
    }
}
