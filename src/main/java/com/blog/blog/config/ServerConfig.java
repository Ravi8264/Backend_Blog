package com.blog.blog.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private final Environment environment;

    public ServerConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        String port = environment.getProperty("PORT");
        if (port != null && !port.equals("$PORT") && !port.isEmpty()) {
            try {
                factory.setPort(Integer.parseInt(port));
            } catch (NumberFormatException e) {
                // If PORT is not a valid number, use default 8080
                factory.setPort(8080);
            }
        } else {
            // Default port if PORT is not set or is "$PORT"
            factory.setPort(8080);
        }
    }
}
