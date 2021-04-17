package com.epam.esm.persistence.extractor;

import com.epam.esm.persistence.model.SearchSpecification;

import java.util.List;

/**
 * Interface for extracting search specification values
 */
public interface SearchParametersExtractor {
    /**
     * Method for extracting values from search specification
     *
     * @param specification to extract fields from
     * @return list of specification field values
     */
    List<Object> getValues(SearchSpecification specification);
}
