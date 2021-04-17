package com.epam.esm.persistence.extractor;

import java.util.Map;

/**
 * Interface for extracting map of field values from object
 *
 * @param <T> object type to extract from
 */
public interface FieldsExtractor<T> {
    /**
     * Method that extracts field values of object and sets to map where key is column name from db
     *
     * @param t object to be extracted with
     * @return map of column name and object field value
     */
    Map<String, Object> getFieldsValuesMap(T t);
}
