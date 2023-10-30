package com.learn.javaadvanced.configuration;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean
    @ConditionalOnProperty(
            name = "custom",
            havingValue = "false")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        log.info("Used dataSource");
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    @ConditionalOnProperty(
            name = "custom",
            havingValue = "true")
    @ConditionalOnMissingBean
    public DataSource dataSourceCustom(Environment env) {
        log.info("Used dataSourceCustom");
        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username(env.getProperty("user") != null
                ? env.getProperty("user") : "");
        dataSourceBuilder.password(env.getProperty("pass") != null
                ? env.getProperty("pass") : "");
        return dataSourceBuilder.build();
    }

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        flyway.migrate();

        return flyway;
    }
}
