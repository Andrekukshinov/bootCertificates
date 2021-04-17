package com.epam.esm.persistence.config;

import com.epam.esm.persistence.util.jdbc.GiftCertificateSimpleJdbcInsert;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.epam.esm.persistence"})
@Profile("test")
public class TestConfiguration {
    @Bean
    public DataSource getTestDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/schema.sql")
                .addScript("classpath:sql/data.sql")
                .build();
    }

    @Bean
    public GiftCertificateSimpleJdbcInsert certificateSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateSimpleJdbcInsert(jdbcTemplate);
    }

    @Bean
    public JdbcTemplate h2JdbcTemplate(DataSource source) {
        return new JdbcTemplate(source);
    }

}
