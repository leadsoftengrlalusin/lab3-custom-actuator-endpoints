package com.example.actuatorlab.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "appstats")
public class ApplicationStatsEndpoint {

    private final Map<String, Object> stats = new HashMap<>();

    public ApplicationStatsEndpoint() {
        // Initialize with some default stats
        stats.put("startTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        stats.put("version", "1.0.0");
        stats.put("environment", "development");
    }

    @ReadOperation
    public Map<String, Object> getStats() {
        // Add current timestamp
        stats.put("lastUpdated", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        stats.put("uptime", System.currentTimeMillis());

        return stats;
    }
}