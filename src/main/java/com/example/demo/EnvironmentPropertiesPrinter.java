package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EnvironmentPropertiesPrinter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentPropertiesPrinter.class);
    private final Environment env;

    public EnvironmentPropertiesPrinter(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void logApplicationProperties() {
        LOGGER.info("************************* PROPERTIES(ENVIRONMENT) ******************************");

        LOGGER.info("{}={}", "spring.data.mongodb.uri", env.getProperty("spring.data.mongodb.uri"));

        LOGGER.info("{}={}", "spring.data.mongodb.host", env.getProperty("spring.data.mongodb.host"));

        LOGGER.info("{}={}", "spring.data.mongodb.port", env.getProperty("spring.data.mongodb.port"));

        LOGGER.info("{}={}", "spring.data.mongodb.database", env.getProperty("spring.data.mongodb.database"));

        LOGGER.info("{}={}", "spring.data.mongodb.username", env.getProperty("spring.data.mongodb.username"));

        LOGGER.info("{}={}", "spring.data.mongodb.password", env.getProperty("spring.data.mongodb.password"));

        LOGGER.info("******************************************************************************");
    }
}