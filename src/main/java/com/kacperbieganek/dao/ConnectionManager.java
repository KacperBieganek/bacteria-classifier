package com.kacperbieganek.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DRIVER = "jdbc:sqlite:";

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);
    private static ConnectionManager cm;
    private Connection connection;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return cm == null ? cm = new ConnectionManager() : cm;
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection createConnection(String path) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DRIVER + path);
                LOG.info("Connection established to {}", path);
            } catch (SQLException e) {
                LOG.error("", e);
            }
        }
        return connection;
    }

    public void closeConnection(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("",e);
            }
        }
    }


}
