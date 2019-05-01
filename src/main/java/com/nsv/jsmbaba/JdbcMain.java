package com.nsv.jsmbaba;

import com.nsv.jsmbaba.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMain {
    public static void main(String[] args) {
        selectCustomers();

        System.out.println("-------------------------------------------");
        //insert customer
        Customer baba = Customer.builder()
                                .name("Baba")
                                .street("3 Capano Drive")
                                .city("Newark")
                                .state("DE")
                                .country("US")
                                .zipCode("19702")
                                .build();

        insertCustomer(baba);

        Customer jsm = Customer.builder()
                .name("JSM")
                .street("3 Capano Drive")
                .city("Newark")
                .state("DE")
                .country("US")
                .zipCode("19702")
                .build();

        insertCustomer(jsm);
        System.out.println("-------------------------------------------");
        selectCustomers();
        System.out.println("-------------------------------------------");
        callstoredprocusingcallablestmt(89);
        System.out.println("-------getAllCustomers------------------------------------");
        List<Customer> allCustomers = getAllCustomers();
        allCustomers.forEach((Customer customer) -> {
            System.out.println(customer);
        });
        System.out.println("-------getCustomerById------------------------------------");
        Customer customer = getCustomerById(89);
        System.out.println(customer);
    }

    public static void selectCustomers(){
        String query = "select * from customer";
        try {
            Connection connection = getMeADatabaseConnection();
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

    public static List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<Customer>();
        try {
            Connection connection = getMeADatabaseConnection();
            String sql = "select * from customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Customer customer = Customer.builder()
                        .customerId(resultSet.getInt("customerId"))
                        .name(resultSet.getString("name"))
                        .street(resultSet.getString("street"))
                        .city(resultSet.getString("city"))
                        .state(resultSet.getString("state"))
                        .country(resultSet.getString("country"))
                        .zipCode(resultSet.getString("zipcode"))
                        .build();

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static Customer getCustomerById(int id){
        Customer customer = null;
        try {
            Connection connection = getMeADatabaseConnection();
            String sql = "select * from customer where customerid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                customer = Customer.builder()
                        .customerId(resultSet.getInt("customerId"))
                        .name(resultSet.getString("name"))
                        .street(resultSet.getString("street"))
                        .city(resultSet.getString("city"))
                        .state(resultSet.getString("state"))
                        .country(resultSet.getString("country"))
                        .zipCode(resultSet.getString("zipcode"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


    public static void callstoredprocusingcallablestmt(int customerId){
        String sql = "call fetchCustomers(?)";
        try {
            Connection connection = getMeADatabaseConnection();
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt("id",customerId);
            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("NAME="+resultSet.getString("NAME")+" ; "+"STREET="+resultSet.getString("STREET"));
            }
            resultSet.close();
            callableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertCustomer(Customer customer){
        String sql = "insert into customer(name, street, city, state, country, zipcode) values(?,?,?,?,?,?)";
        try {
            Connection connection = getMeADatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getStreet());
            preparedStatement.setString(3,customer.getCity());
            preparedStatement.setString(4,customer.getState());
            preparedStatement.setString(5,customer.getCountry());
            preparedStatement.setString(6,customer.getZipCode());
            int noOfRowsUpdated = preparedStatement.executeUpdate();
            System.out.println("No of rows updated"+noOfRowsUpdated);

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
