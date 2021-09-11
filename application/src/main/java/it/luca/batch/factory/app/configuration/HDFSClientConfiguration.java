package it.luca.batch.factory.app.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "hadoop")
public class HDFSClientConfiguration {

    public static final String CONNECTION_TIMEOUT_MILLIS = "connectionTimeoutMillis";
    public static final String DEFAULT_PERMISSIONS = "defaultPermissions";
    public static final String MAX_RETRIES = "maxRetries";
    public static final String URI = "uri";
    public static final String USER = "user";

    private Map<String, String> configuration;

    public String get(String key) {

        return configuration.get(key);
    }
}
