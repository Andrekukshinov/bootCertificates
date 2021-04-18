package com.epam.esm.web;

import com.epam.esm.persistence.config.PersistenceConfig;
import com.epam.esm.service.config.ServiceConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.esm.web")
public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);


    public static void main(String[] args) {
        LOGGER.error("test");
        SpringApplication.run(new Class<?>[] {Application.class, ServiceConfiguration.class, PersistenceConfig.class}, args);
    }
}
