package com.learn.javaadvanced.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseHealthService {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkDatabaseHealth() {
        try {
            jdbcTemplate.execute("SELECT 1 FROM dual");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
