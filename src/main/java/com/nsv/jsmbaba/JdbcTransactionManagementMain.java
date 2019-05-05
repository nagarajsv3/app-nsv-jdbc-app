package com.nsv.jsmbaba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.nsv.jsmbaba.utility.JdbcUtility.getMeADatabaseConnection;

public class JdbcTransactionManagementMain {

    private static String sqlInsertIntoEmployee = "insert into employee(employeeId, name) values (?,?)";
    private static String sqlInsertIntoAddress = "insert into address(employeeId, address, city, country) values (?,?,?,?)";

    public static void main(String[] args) {

        Connection connection = getMeADatabaseConnection();
        try {
            System.out.println(connection.getAutoCommit());
            connection.setAutoCommit(false);
            System.out.println(connection.getAutoCommit());
            insertIntoEmployee(2,connection);
            insertIntoAddress(2,connection);
            connection.commit();
            System.out.println("Transaction Committed Successfully");
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
                System.out.println("Transaction RolledBack Successfully");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }


    public static void insertIntoEmployee(int id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(sqlInsertIntoEmployee);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, "Naga");
        int recordsUpdated = preparedStatement.executeUpdate();
        System.out.println("Number Of Records Inserted in Employee=" + recordsUpdated);
        preparedStatement.close();
    }

    public static void insertIntoAddress(int id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(sqlInsertIntoAddress);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, "3 Capano");
        preparedStatement.setString(3, "Newark");
        preparedStatement.setString(4, "US");
        int recordsUpdated = preparedStatement.executeUpdate();
        System.out.println("Number Of Records Inserted in Address=" + recordsUpdated);
        preparedStatement.close();

    }
}
