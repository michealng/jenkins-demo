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

        LOGGER.info("******************************************************************************");
    }
}