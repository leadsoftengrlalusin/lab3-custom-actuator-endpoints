package com.example.actuatorlab.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Endpoint(id = "appmanagement")
public class ApplicationManagementEndpoint {

    private final Map<String, String> configuration = new ConcurrentHashMap<>();
    private boolean maintenanceMode = false;

    public ApplicationManagementEndpoint() {
        configuration.put("cache.ttl", "3600");
        configuration.put("max.connections", "100");
        configuration.put("timeout", "30");
    }

    @ReadOperation
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("maintenanceMode", maintenanceMode);
        status.put("configuration", new HashMap<>(configuration));
        status.put("timestamp", System.currentTimeMillis());
        return status;
    }

    @WriteOperation
    public void setMaintenanceMode(boolean enabled) {
        this.maintenanceMode = enabled;
    }

    @WriteOperation
    public void updateConfiguration(String key, String value) {
        if (key != null && value != null) {
            configuration.put(key, value);
        }
    }
}