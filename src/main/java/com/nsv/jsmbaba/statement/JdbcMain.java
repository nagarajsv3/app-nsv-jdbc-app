package com.nsv.jsmbaba.statement;

import java.sql.*;

public class JdbcMain {
    public static void main(String[] args) {

        String jdbcurl = "jdbc:mysql://127.0.0.1:3306/javatraining";
        String username = "root";
        String password = "root";
        String query = "select * from customer";
        try {
            Connection connection = DriverManager.getConnection(jdbcurl, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                System.out.println("customerId="+resultSet.getString("customerId")+" ; "+"name="+resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
