package com.epam.esm.persistence.mapper;

import com.epam.esm.persistence.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GiftCertificateRowMapperTest {
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";
    private static final long LONG_VAL = 1L;
    private static final String STRING_VAL = "1";
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final BigDecimal PRICE = new BigDecimal(3);
    @Mock
    private ResultSet rs;

    @InjectMocks
    private GiftCertificateRowMapper mapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapRowShouldReturnCertificateObject() throws SQLException {
        when(rs.getString(any())).thenReturn(STRING_VAL);
        when(rs.getLong(any())).thenReturn(LONG_VAL);
        Integer shortVal = Integer.valueOf(STRING_VAL);
        when(rs.getInt(any())).thenReturn(shortVal);
        when(rs.getBigDecimal(any())).thenReturn(PRICE);
        when(rs.getObject(CREATE_DATE, LocalDateTime.class)).thenReturn(DATE);
        when(rs.getObject(LAST_UPDATE_DATE, LocalDateTime.class)).thenReturn(DATE);
        GiftCertificate expected = GiftCertificate.getBuilder()
                .setId(LONG_VAL)
                .setCreateDate(DATE)
                .setLastUpdateDate(DATE)
                .setName(STRING_VAL)
                .setDescription(STRING_VAL)
                .setPrice(PRICE)
                .setDuration(shortVal)
                .build();


        GiftCertificate actual = mapper.mapRow(rs, 0);


        assertThat(actual, is(expected));
    }
}
