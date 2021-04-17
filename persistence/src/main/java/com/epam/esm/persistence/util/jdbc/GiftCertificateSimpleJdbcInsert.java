package com.epam.esm.persistence.util.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public class GiftCertificateSimpleJdbcInsert extends SimpleJdbcInsert {
    private static final String TABLE_NAME = "gift_certificates";
    private static final String KEY = "id";

    public GiftCertificateSimpleJdbcInsert(DataSource dataSource) {
        super(dataSource);
    }

    public GiftCertificateSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @PostConstruct
    public void afterPropertiesSet() {
        withTableName(TABLE_NAME);
        setGeneratedKeyName(KEY);
    }
}
