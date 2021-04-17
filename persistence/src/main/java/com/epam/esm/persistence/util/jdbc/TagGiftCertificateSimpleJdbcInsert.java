package com.epam.esm.persistence.util.jdbc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class TagGiftCertificateSimpleJdbcInsert extends SimpleJdbcInsert implements InitializingBean {
    private static final String TABLE_NAME = "tags_gift_certificates";
    private static final String KEY = "id";

    public TagGiftCertificateSimpleJdbcInsert(DataSource dataSource) {
        super(dataSource);
    }

    public TagGiftCertificateSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void afterPropertiesSet() {
        this.withTableName(TABLE_NAME);
        this.setGeneratedKeyName(KEY);
    }
}
