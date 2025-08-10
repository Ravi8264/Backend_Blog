package com.blog.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PortConfiguration implements EnvironmentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PortConfiguration.class);

    private final Environment environment;

    public PortConfiguration(Environment environment) {
        this.environment = environment;
    }

    public PortConfiguration() {
        this.environment = null; // For EnvironmentPostProcessor
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String portValue = System.getenv("PORT");

        logger.info("=== Environment Post-Processing: Port Validation ===");
        logger.info("PORT environment variable: {}", portValue);

        // Check if PORT contains literal $PORT string and fix it
        if (portValue != null && ("$PORT".equals(portValue) || portValue.contains("$PORT"))) {
            logger.warn("DETECTED: PORT environment variable contains literal '$PORT' string");
            logger.warn("Fixing by setting server.port to default value 8080");

            // Create a property source with the corrected port value
            Map<String, Object> correctedProperties = new HashMap<>();
            correctedProperties.put("server.port", "8080");

            MapPropertySource correctedPropertySource = new MapPropertySource("correctedPortProperties",
                    correctedProperties);
            environment.getPropertySources().addFirst(correctedPropertySource);

            logger.info("Applied corrected server.port=8080 to override problematic PORT environment variable");
        }

        logger.info("=======================================================");
    }

    @EventListener(ApplicationStartedEvent.class)
    public void logPortInformation() {
        if (environment == null)
            return;

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
