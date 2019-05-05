package com.nsv.jsmbaba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import static com.nsv.jsmbaba.utility.JdbcUtility.getMeADatabaseConnection;

public class JdbcTxnMgmtSavePointMain {
    private static String sqlInsertIntoEmployee = "insert into employee(employeeId, name) values (?,?)";
    private static String sqlInsertIntoAddress = "insert into address(employeeId, address, city, country) values (?,?,?,?)";
    private static String sqlInsertIntoLogs = "insert into logs(id, message) values (?,?)";

    public static void main(String[] args) {

        Connection connection = getMeADatabaseConnection();
        Savepoint savepoint = null;
        try {
            System.out.println(connection.getAutoCommit());
            connection.setAutoCommit(false);
            System.out.println(connection.getAutoCommit());

            insertIntoEmployee(6,connection);
            insertIntoAddress(6,connection);
            savepoint = connection.setSavepoint("EmployeeAddress-Created");
            System.out.println("SavePoint EmployeeAddress-Created");

            insertIntoLogs(5,connection);

            connection.commit();
            System.out.println("Transaction Committed Successfully");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if(savepoint == null){
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Transaction Completely RolledBack Successfully");
            }else{
                try {
                    connection.rollback(savepoint);
                    connection.commit();
                    connection.close();
                }catch (SQLException e2){
                    e2.printStackTrace();
                }
                System.out.println("Transaction RolledBack to the SavePoint Successfully");
            }

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
        preparedStatement.setString(3, "New");
        preparedStatement.setString(4, "US");
        int recordsUpdated = preparedStatement.executeUpdate();
        System.out.println("Number Of Records Inserted in Address=" + recordsUpdated);
        preparedStatement.close();

    }

    public static void insertIntoLogs(int id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(sqlInsertIntoLogs);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, "Naga");
        int recordsUpdated = preparedStatement.executeUpdate();
        System.out.println("Number Of Records Inserted in Logs=" + recordsUpdated);
        preparedStatement.close();
    }

}
