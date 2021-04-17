package com.epam.esm.persistence.config;

import com.epam.esm.persistence.util.jdbc.GiftCertificateSimpleJdbcInsert;
import com.epam.esm.persistence.util.jdbc.TagGiftCertificateSimpleJdbcInsert;
import com.epam.esm.persistence.util.jdbc.TagSimpleJdbcInsert;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.epam.esm.persistence")
@EnableTransactionManagement
@PropertySource(value = "classpath:/connectionPool.properties")
public class PersistenceConfig {

    private static final String DRIVER_MYSQL = "driver.mysql";
    private static final String URL = "url";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String CONNECTIONS_MIN = "connections.min";
    private static final String CONNECTIONS_MAX = "connections.max";
    private static final String CLOSE_METHOD = "close";
    private static final String CONNECTION_POOL_PROPERTIES = "connectionPool.properties";

    @Bean
    public Resource getResource() {
        return new ClassPathResource(CONNECTION_POOL_PROPERTIES);
    }

    @Bean(destroyMethod = CLOSE_METHOD)
    @Profile("dev")
    public ComboPooledDataSource getMysqlDataSource(Resource resource) throws PropertyVetoException, IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty(DRIVER_MYSQL));
        dataSource.setJdbcUrl(properties.getProperty(URL));
        dataSource.setPassword(properties.getProperty(PASSWORD));
        dataSource.setUser(properties.getProperty(USERNAME));
        String minConnectionsString = properties.getProperty(CONNECTIONS_MIN);
        String maxConnectionsString = properties.getProperty(CONNECTIONS_MAX);
        dataSource.setMinPoolSize(Integer.parseInt(minConnectionsString));
        dataSource.setMaxPoolSize(Integer.parseInt(maxConnectionsString));
        return dataSource;
    }

    @Bean
    @Profile("dev")
    public JdbcTemplate mySqlJdbcTemplate(DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public TagSimpleJdbcInsert tagSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new TagSimpleJdbcInsert(jdbcTemplate);
    }

    @Bean
    public TagGiftCertificateSimpleJdbcInsert tagGiftCertificateSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new TagGiftCertificateSimpleJdbcInsert(jdbcTemplate);
    }

    @Bean
    public GiftCertificateSimpleJdbcInsert certificateSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateSimpleJdbcInsert(jdbcTemplate);
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource source) {
        return new DataSourceTransactionManager(source);
    }
}
