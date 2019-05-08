package com.nsv.jsmbaba.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtility {

    public static Connection getMeADatabaseConnection(){
        String jdbcurl = "jdbc:mysql://127.0.0.1:3306/javatraining";
        String username = "root";
        String password = "root";
        Connection connection = null;
        try {
            //Loads the driver dynamically at run time by looking ar jar file
            Class.forName(com.mysql.jdbc.Driver.class.getName());
            connection = DriverManager.getConnection(jdbcurl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
