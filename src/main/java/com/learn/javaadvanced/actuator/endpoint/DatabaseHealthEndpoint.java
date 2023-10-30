package com.learn.javaadvanced.actuator.endpoint;

import com.learn.javaadvanced.service.DatabaseHealthService;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Endpoint(id="database")
public class DatabaseHealthEndpoint {

    private final DatabaseHealthService databaseHealthService;

    public DatabaseHealthEndpoint(DatabaseHealthService databaseHealthService) {
        this.databaseHealthService = databaseHealthService;
    }

    @ReadOperation
    public Map<String, Boolean> databaseHealth() {
        boolean isDatabaseUp = databaseHealthService.checkDatabaseHealth();
        Map<String, Boolean> databaseHealth = new LinkedHashMap<>();
        databaseHealth.put("Is database up", isDatabaseUp);
        return databaseHealth;
    }
}
