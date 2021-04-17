package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.extractor.FieldsExtractor;
import com.epam.esm.persistence.model.TagGiftCertificateId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TagGiftCertificateIdFieldsExtractor extends FieldsExtractorHelper implements FieldsExtractor<TagGiftCertificateId> {

    private static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    private static final String TAG_ID = "tag_id";

    @Override
    public Map<String, Object> getFieldsValuesMap(TagGiftCertificateId tagGiftCertificateId) {
        Map<String, Object> result = new HashMap<>();
        putToMapIfNotNull(result, tagGiftCertificateId.getCertificateId(), GIFT_CERTIFICATE_ID);
        putToMapIfNotNull(result, tagGiftCertificateId.getTagId(), TAG_ID);
        return result;
    }
}
