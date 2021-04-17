package com.epam.esm.persistence.mapper;

import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";
    private static final String PRICE = "price";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        String description = rs.getString(DESCRIPTION);
        Integer duration = rs.getInt(DURATION);
        LocalDateTime createDate = rs.getObject(CREATE_DATE, LocalDateTime.class);
        LocalDateTime lastUpdateDate = rs.getObject(LAST_UPDATE_DATE, LocalDateTime.class);
        BigDecimal price = rs.getBigDecimal(PRICE);

        return GiftCertificate.getBuilder()
                .setId(id)
                .setCreateDate(createDate)
                .setLastUpdateDate(lastUpdateDate)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setDuration(duration)
                .build();
    }
}
