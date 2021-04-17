package com.epam.esm.persistence.mapper;

import com.epam.esm.persistence.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TagRowMapperTest {

    private static final long ID = 1L;
    private static final String NAME = "";
    @Mock
    private ResultSet rs;

    @InjectMocks
    private TagRowMapper mapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapRowShouldReturnTagObject() throws SQLException {
        when(rs.getString(any())).thenReturn(NAME);
        when(rs.getLong(any())).thenReturn(ID);
        Tag expected = new Tag(ID, NAME);

        Tag actual = mapper.mapRow(rs, 0);

        assertThat(actual, is(expected));
    }

}
