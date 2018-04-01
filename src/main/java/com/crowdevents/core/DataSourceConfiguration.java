package com.crowdevents.core;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Profile("development")
    @Bean
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Profile("qa")
    @Bean
    public DataSource qaDataSource(@Value("${POSTGRES_HOSTNAME}") String host,
                                   @Value("${POSTGRES_PORT}") String port,
                                   @Value("${POSTGRES_DB}") String database,
                                   @Value("${POSTGRES_USER}") String user,
                                   @Value("${POSTGRES_PASSWORD}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", host, port, database));
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }
}