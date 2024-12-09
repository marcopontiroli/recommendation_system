package com.conentwise.recommendationsystem.recommendationsystem.init;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SqlFileExecutor {

    private final DataSource dataSource;

    @Value("classpath:data-docker.sql")
    private Resource sqlFile;

    public SqlFileExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void executeSqlFileIfDatabaseIsEmpty() {
        try (Connection connection = dataSource.getConnection()) {
            if (isDatabaseEmpty(connection)) {
                System.out.println("Database is empty. Executing SQL script...");
                executeSqlFile(connection);
            } else {
                System.out.println("Database is not empty. Skipping SQL script execution.");
            }
        } catch (Exception e) {
            System.err.println("Error during SQL script execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isDatabaseEmpty(Connection connection) throws Exception {
        String[] tablesToCheck = {"genre", "movie", "user", "movies_genres", "interaction"};

        for (String table : tablesToCheck) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false;
                }
            }
        }
        return true; 
    }

    private void executeSqlFile(Connection connection) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(sqlFile.getInputStream()))) {
            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sql.append(line);
                if (line.endsWith(";")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sql.toString());
                    }
                    sql.setLength(0);
                }
            }

            System.out.println("SQL script executed successfully.");
        }
    }
}
