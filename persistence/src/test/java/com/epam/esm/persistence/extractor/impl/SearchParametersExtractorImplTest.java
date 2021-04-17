package com.epam.esm.persistence.extractor.impl;

import com.epam.esm.persistence.model.SearchSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class SearchParametersExtractorImplTest {

    private static final String NAME = "name";

    private static final SearchSpecification OBJECT = new SearchSpecification(NAME, NAME, NAME);

    @Test
    void testGetFieldsValuesShouldReturnArrayWithAllFields() {
        SearchParametersExtractorImpl extractor = new SearchParametersExtractorImpl();
        List<Object> actual = extractor.getValues(OBJECT);
        List<Object> expected = List.of(NAME, NAME, NAME);

        assertThat(actual, is(expected));
    }

    @Test
    void testGetFieldsValuesShouldReturnArrayWithNoFields() {
        SearchParametersExtractorImpl extractor = new SearchParametersExtractorImpl();
        List<Object> actual = extractor.getValues(new SearchSpecification());
        List<Object> expected = List.of();

        assertThat(actual, is(expected));
    }
}
