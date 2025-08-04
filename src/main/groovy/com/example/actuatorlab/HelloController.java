package com.example.actuatorlab;

import com.example.actuatorlab.endpoints.BusinessMetricsEndpoint;
import com.example.actuatorlab.endpoints.ApplicationManagementEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {

    private final BusinessMetricsEndpoint businessMetricsEndpoint;
    private final ApplicationManagementEndpoint appManagementEndpoint;

    public HelloController(BusinessMetricsEndpoint businessMetricsEndpoint,
                           ApplicationManagementEndpoint appManagementEndpoint) {
        this.businessMetricsEndpoint = businessMetricsEndpoint;
        this.appManagementEndpoint = appManagementEndpoint;
    }

    @GetMapping("/hello")
    public String hello() {
        // Simulate business activity
        businessMetricsEndpoint.incrementOrders();
        businessMetricsEndpoint.addRevenue(100);

        return "Hello, Spring Boot Actuator with Custom Endpoints!";
    }

    @PostMapping("/order")
    public String createOrder(@RequestBody Map<String, Object> order) {
        Long amount = Long.valueOf(order.get("amount").toString());
        businessMetricsEndpoint.incrementOrders();
        businessMetricsEndpoint.addRevenue(amount);

        return "Order created with amount: " + amount;
    }

    @GetMapping("/status")
    public Map<String, Object> getApplicationStatus() {
        return appManagementEndpoint.getStatus();
    }
}