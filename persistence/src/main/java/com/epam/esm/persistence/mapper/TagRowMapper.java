package com.epam.esm.persistence.mapper;

import com.epam.esm.persistence.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        return new Tag(id, name);
    }
}
