package com.example.actuatorlab.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "businessmetrics")
public class BusinessMetricsEndpoint {

    private final AtomicLong totalOrders = new AtomicLong(0);
    private final AtomicLong totalRevenue = new AtomicLong(0);
    private final AtomicLong activeUsers = new AtomicLong(0);

    public void incrementOrders() {
        totalOrders.incrementAndGet();
    }

    public void addRevenue(long amount) {
        totalRevenue.addAndGet(amount);
    }

    public void setActiveUsers(long count) {
        activeUsers.set(count);
    }

    @ReadOperation
    public Map<String, Object> getAllMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalOrders", totalOrders.get());
        metrics.put("totalRevenue", totalRevenue.get());
        metrics.put("activeUsers", activeUsers.get());
        metrics.put("averageOrderValue",
                totalOrders.get() > 0 ? (double) totalRevenue.get() / totalOrders.get() : 0.0);
        return metrics;
    }

    @ReadOperation
    public Object getMetric(@Selector String metricName) {
        return switch (metricName.toLowerCase()) {
            case "orders" -> totalOrders.get();
            case "revenue" -> totalRevenue.get();
            case "users" -> activeUsers.get();
            case "average" -> totalOrders.get() > 0 ?
                    (double) totalRevenue.get() / totalOrders.get() : 0.0;
            default -> Map.of("error", "Unknown metric: " + metricName);
        };
    }
}