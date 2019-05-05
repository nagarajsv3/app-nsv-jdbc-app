Transaction Management & SavePoint
https://www.journaldev.com/2483/java-jdbc-transaction-management-savepoint

connection.setAutoCommit(false);
connection.commit();
connection.rollback();

Savepoint savepoint = connection.setSavePoint("EmployeeCreated");
connection.rollback(savepoint);
connection.commit();