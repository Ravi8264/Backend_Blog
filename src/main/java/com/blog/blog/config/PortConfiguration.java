package com.blog.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@Configuration
public class PortConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PortConfiguration.class);

    private final Environment environment;

    public PortConfiguration(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void logPortInformation() {
        // Log port information for debugging after startup
        String portValue = environment.getProperty("PORT");
        String serverPort = environment.getProperty("server.port");

        logger.info("=== Final Port Configuration ===");
        logger.info("PORT environment variable: {}", portValue);
        logger.info("server.port property: {}", serverPort);
        logger.info("Active profiles: {}", String.join(", ", environment.getActiveProfiles()));
        logger.info("===============================");
    }
}
