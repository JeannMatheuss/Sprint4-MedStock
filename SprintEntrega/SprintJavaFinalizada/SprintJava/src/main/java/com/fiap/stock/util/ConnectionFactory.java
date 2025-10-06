package com.fiap.stock.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "oracle.fiap.com.br");
    private static final String DB_PORT = System.getenv().getOrDefault("DB_PORT", "1521");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "ORCL");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "rm555519");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "061205");

    private static final String URL = "jdbc:oracle:thin:@" + DB_HOST + ":" + DB_PORT + ":" + DB_NAME;

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Oracle não encontrado no classpath.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
}
