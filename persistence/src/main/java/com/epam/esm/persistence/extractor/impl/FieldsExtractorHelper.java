package com.epam.esm.persistence.extractor.impl;

import java.util.Map;

public abstract class FieldsExtractorHelper {
    protected void putToMapIfNotNull(Map<String, Object> result, Object objectToPut, String columnName) {
        if (objectToPut != null) {
            result.put(columnName, objectToPut);
        }
    }
}
