package com.Conexao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Sergio
 */
public class ConexaoMySQL {
    private static String serverName = "localhost";
    private static String mydatabase = "salaobeleza";
    private static String username = "root";
    private static String password = "";
    private static HikariDataSource hikariDataSource = null;


    public static String getServerName() {
        return serverName;
    }

    public static void setServerName(String serverName) {
        ConexaoMySQL.serverName = serverName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConexaoMySQL.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ConexaoMySQL.username = username;
    }

    public ConexaoMySQL() {
    }

    public static Connection getConexaoMySQL() {
        try {
            if (hikariDataSource == null) {
                HikariConfig config = new HikariConfig();
                config.setMaximumPoolSize(40000);
                config.setMinimumIdle(300);
                config.setLeakDetectionThreshold(190000);
                config.setConnectionTimeout(30000);
                config.setIdleTimeout(0);
                config.setAutoCommit(true);

                config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
                config.addDataSourceProperty("serverName", serverName);
                config.addDataSourceProperty("port", "3306");
                config.addDataSourceProperty("databaseName", mydatabase);
                config.addDataSourceProperty("user", username);
                config.addDataSourceProperty("password", password);
                hikariDataSource = new HikariDataSource(config);
            }
            return hikariDataSource.getConnection();

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
