package com.example.actuatorlab.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "featureflags")
public class FeatureFlagsEndpoint {

    private final Map<String, Boolean> featureFlags = new HashMap<>();

    public FeatureFlagsEndpoint() {
        // Initialize with some default feature flags
        featureFlags.put("new-ui", false);
        featureFlags.put("beta-features", true);
        featureFlags.put("debug-mode", false);
    }

    @ReadOperation
    public Map<String, Boolean> getFeatureFlags() {
        return new HashMap<>(featureFlags);
    }

    @WriteOperation
    public void updateFeatureFlag(String name, Boolean enabled) {
        if (name != null && enabled != null) {
            featureFlags.put(name, enabled);
        }
    }
}