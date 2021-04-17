package com.epam.esm.service.convertor;

import com.epam.esm.persistence.model.enums.SortDirection;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringToSortDirectionConvertorTest {

    @Test
    void testConvertShouldConvertWhenValid() {
        StringToSortDirectionConvertor convertor = new StringToSortDirectionConvertor();

        SortDirection asc = convertor.convert("ASC");

        assertThat(asc, is(SortDirection.ASC));
    }

    @Test
    void testConvertShouldThrowIllegalArgumentExceptionWhenInvalid() {
        StringToSortDirectionConvertor convertor = new StringToSortDirectionConvertor();

        assertThrows(IllegalArgumentException.class, () -> convertor.convert("ASCY"));

    }

}
