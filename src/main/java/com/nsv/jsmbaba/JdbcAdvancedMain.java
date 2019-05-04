package com.nsv.jsmbaba;

import com.nsv.jsmbaba.domain.Customer;
import com.nsv.jsmbaba.domain.Order;
import com.nsv.jsmbaba.domain.OrderStatus;
import com.nsv.jsmbaba.domain.PhoneInformation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAdvancedMain {


    public static void main(String[] args) throws SQLException {
        System.out.println("Adding a sysout");
        System.out.println("**********JDBC - One To One Association-BEGIN**********");
        Customer customer = getCustomerWithPhoneInformation(1);
        System.out.println(customer);
        System.out.println("**********JDBC - One To Many Association-BEGIN**********");
        Customer customerWithOrders = getCustomerWithOrders(1);
        System.out.println("Name:"+customerWithOrders.getName());
        System.out.println("City:"+customerWithOrders.getCity());
        customerWithOrders.getOrders().forEach(order -> {
            System.out.println("Item:"+order.getItem()+" Status:"+order.getOrderStatus());
        });
    }

    private static Customer getCustomerWithOrders(int id) {
        Customer customer = new Customer();
        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = getMeADatabaseConnection();
            String sql = "select c.name, c.city, c.state, c.zipcode, o.orderId, o.item, o.order_status from customer c  inner join ordersplaced o on c.customerId = o.customerId where c.customerId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(customer.getName() == null) {
                    customer.setName(resultSet.getString("name"));
                    customer.setCity(resultSet.getString("city"));
                }
                Order order = new Order();
                order.setItem(resultSet.getString("item"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                orders.add(order);
            }
            customer.setOrders(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
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

    public static Connection getMeADatabaseConnection1(){
        String jdbcurl = "jdbc:mysql://127.0.0.1:3306/javatraining";
        String username = "root";
        String password = "root1";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcurl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

