package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.extractor.SearchParametersExtractor;
import com.epam.esm.persistence.model.SearchSpecification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SearchParametersExtractorImpl implements SearchParametersExtractor {
    @Override
    public List<Object> getValues(SearchSpecification specification) {
        List<Object> result = new ArrayList<>();
        addToListIfNotNull(result, specification.getCertificateName());
        addToListIfNotNull(result, specification.getCertificateDescription());
        addToListIfNotNull(result, specification.getTagName());
        return result;
    }

    private void addToListIfNotNull(List<Object> result, Object objectToPut) {
        if (Objects.nonNull(objectToPut)) {
            result.add(objectToPut);
        }
    }
}
