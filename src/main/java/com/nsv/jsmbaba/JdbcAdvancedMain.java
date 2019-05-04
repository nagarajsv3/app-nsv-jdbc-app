package com.nsv.jsmbaba;

import com.nsv.jsmbaba.domain.Customer;
import com.nsv.jsmbaba.domain.PhoneInformation;

import java.sql.*;

public class JdbcAdvancedMain {

    public static void main(String[] args) throws SQLException {
        Customer customer = getCustomerWithPhoneInformation(1);
        System.out.println(customer);

    }

    static Customer getCustomerWithPhoneInformation(int id) throws SQLException {
        String sql= "select c.name, c.street, c.zipcode, ph.cell, ph.work, ph.home from customer c\n" +
                "inner join phoneinfo ph\n" +
                "on c.customerId = ph.customerId\n" +
                "where c.customerId = ?;";

        Connection connection = getMeADatabaseConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer = new Customer();
        while(resultSet.next()){

            customer.setName(resultSet.getString("name"));
            customer.setStreet(resultSet.getString("street"));
            customer.setZipCode(resultSet.getString("zipcode"));
            PhoneInformation phoneInformation = new PhoneInformation();
            phoneInformation.setCell(resultSet.getString("cell"));
            phoneInformation.setHome(resultSet.getString("home"));
            phoneInformation.setWork(resultSet.getString("work"));
            customer.setPhoneInformation(phoneInformation);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
        return customer;


    }

    public static Connection getMeADatabaseConnection(){
        String jdbcurl = "jdbc:mysql://127.0.0.1:3306/javatraining";
        String username = "root";
        String password = "root";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcurl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
