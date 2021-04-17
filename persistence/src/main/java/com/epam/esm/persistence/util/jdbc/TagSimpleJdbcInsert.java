package com.epam.esm.persistence.util.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public class TagSimpleJdbcInsert extends SimpleJdbcInsert {
    private static final String TABLE_NAME = "tags";
    private static final String KEY = "id";

    public TagSimpleJdbcInsert(DataSource dataSource) {
        super(dataSource);
    }

    public TagSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @PostConstruct
    public void afterPropertiesSet() {
        withTableName(TABLE_NAME);
        setGeneratedKeyName(KEY);
    }

}
