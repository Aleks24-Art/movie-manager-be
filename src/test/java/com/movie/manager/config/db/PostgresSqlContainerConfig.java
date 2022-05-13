package com.movie.manager.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

/**
 * Configuration for start postgresql database in docker container for testing.
 */
@Slf4j
@TestConfiguration
public class PostgresSqlContainerConfig {

    private static final String POSTGRES_DOCKER_IMAGE = "postgres:14.2";
    private static final String POSTGRESQL_DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private static final String JDBC_URL_FORMAT = "jdbc:postgresql://%s:%s/%s";
    private static final String POSTGRES_USERNAME = "postgres";
    private static final String POSTGRES_PASSWORD = "password";

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(POSTGRES_DOCKER_IMAGE)
                .withUsername(POSTGRES_USERNAME)
                .withPassword(POSTGRES_PASSWORD);
        POSTGRESQL_CONTAINER.start();
        log.info("Test container started successfully! \n"
                + "JDBC URL: " + getJdbcUrl());
    }

    /**
     * Create datasource-bean connected to test postgres container.
     *
     * @return {@link DataSource} bean
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName(POSTGRESQL_DRIVER_CLASS_NAME);
        datasource.setUrl(getJdbcUrl());
        datasource.setUsername(POSTGRESQL_CONTAINER.getUsername());
        datasource.setPassword(POSTGRESQL_CONTAINER.getPassword());
        return datasource;
    }

    private static String getJdbcUrl() {
        return String.format(JDBC_URL_FORMAT,
                POSTGRESQL_CONTAINER.getContainerIpAddress(),
                POSTGRESQL_CONTAINER.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                POSTGRESQL_CONTAINER.getDatabaseName());
    }
}
