package com.nsv.jsmbaba;

import com.nsv.jsmbaba.domain.*;

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
        System.out.println("**********JDBC - Many To Many Association-BEGIN**********");
        List<Task> allTasksForAReviewer = getAllTasksForAReviewer(1);
        allTasksForAReviewer.forEach(task -> {
            System.out.println(task.getTaskName());
        });

        System.out.println("**********JDBC - Many To Many Association-BEGIN**********");
        List<Reviewer> reviewers =  getAllReviewersForATask(2);
        reviewers.forEach(reviewer -> {
            System.out.println(reviewer.getReviewerName());
        });

    }

    private static List<Reviewer> getAllReviewersForATask(int taskId) {
        List<Reviewer> reviewers = new ArrayList<>();
        try {
            Connection connection = getMeADatabaseConnection();
            String sql = "select t.name as tname , r.name AS rname from reviewer r inner join reviewer_task rt on r.reviewer_id = rt.reviewer_id inner join task t on rt.task_id=t.task_id where t.task_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Reviewer reviewer = new Reviewer();
                reviewer.setReviewerName(resultSet.getString("rname"));
                reviewers.add(reviewer);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return reviewers;
    }


    private static List<Task> getAllTasksForAReviewer(int reviewerId) {
        List<Task> tasks= new ArrayList<>();
        try {
            Connection connection = getMeADatabaseConnection();
            String sql = "select r.name AS rname, t.name as tname from reviewer r inner join reviewer_task rt on r.reviewer_id = rt.reviewer_id inner join task t on rt.task_id=t.task_id where r.reviewer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reviewerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Task task = new Task();
                task.setTaskName(resultSet.getString("tname"));
                tasks.add(task);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException exception){
            exception.printStackTrace();

        }
        return tasks;
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

    public static Customer getCustomerWithPhoneInformation(int id) throws SQLException {
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

