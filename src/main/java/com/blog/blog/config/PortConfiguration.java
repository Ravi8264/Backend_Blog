package com.blog.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PortConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private static final Logger logger = LoggerFactory.getLogger(PortConfiguration.class);

    private final Environment environment;

    public PortConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // Get PORT from environment
        String portValue = environment.getProperty("PORT");

        logger.info("PORT environment variable: {}", portValue);

        // Validate and set port
        int port = getValidPort(portValue);

        logger.info("Setting server port to: {}", port);
        factory.setPort(port);
    }

    private int getValidPort(String portValue) {
        // Default port
        int defaultPort = 8080;

        // Check if PORT is null or empty
        if (portValue == null || portValue.trim().isEmpty()) {
            logger.warn("PORT environment variable is null or empty, using default: {}", defaultPort);
            return defaultPort;
        }

        // Check for literal $PORT string
        if ("$PORT".equals(portValue) || "${PORT}".equals(portValue)) {
            logger.error("PORT contains literal placeholder '{}', using default: {}", portValue, defaultPort);
            return defaultPort;
        }

        // Try to parse as integer
        try {
            int port = Integer.parseInt(portValue.trim());

            // Validate port range
            if (port < 1 || port > 65535) {
                logger.error("PORT {} is out of valid range (1-65535), using default: {}", port, defaultPort);
                return defaultPort;
            }

            logger.info("Successfully parsed PORT: {}", port);
            return port;

        } catch (NumberFormatException e) {
            logger.error("Failed to parse PORT '{}' as integer: {}, using default: {}",
                    portValue, e.getMessage(), defaultPort);
            return defaultPort;
        }
    }
}
