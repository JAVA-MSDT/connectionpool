package com.javamsdt.connectionpool;

import com.javamsdt.connectionpool.db.ConnectionPool;
import com.javamsdt.connectionpool.util.DBData;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;

/**
 *@Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Runner {
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        // Before you start running the program you need to make sure that you have a sql installed in your machine.
        // you nee to add your oqn password for the database in the class DBData in the util package.
        Connection connection;
        Statement statement = null;
        try {
            Class.forName(DBData.DB_DRIVER);
            // all this project is just to test this connection pool
             connection = ConnectionPool.getInstance().getConnection(); // the connection pool class.
            statement = connection.createStatement();
           // statement.executeUpdate("DROP DATABASE " + DBData.DB_NAME); // if you run the project and the data created successfully
                                                                        // You need to un comment this line.
            statement.executeUpdate("CREATE DATABASE " + DBData.DB_NAME);
            System.out.println("Creating .....");
            statement.executeUpdate("USE " + DBData.DB_NAME);
            System.out.println("database using....");

            updateData(DBData.CREATE_SQL_LOCATION, statement);
            System.out.println("Creating tables Done Successfully!");

            updateData(DBData.INSERT_INTO_TABLE, statement);
            System.out.println("Data Inserted Successfully..!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        printTable(query(statement, "SELECT * FROM user_table"));
    }

    // reading the data from a sql script file and execute the statement which located in the file itself
    public static void updateData(String sqlLocation, Statement statement) throws SQLException {

        File file = new File(sqlLocation);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            StringBuilder buffer = new StringBuilder();
            String read = reader.readLine();
            while (read != null) {
                buffer.append(read);
                read = reader.readLine();
            }
            String[] state = buffer.toString().split(";");
            for (String st : state) {
                statement.executeUpdate(st);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }


    // to print the table.
    public static void printTable(ResultSet myResult) {
        try {
            System.out.println();
            ResultSetMetaData rsmd = myResult.getMetaData();

            int i;
            for (i = 1; i <= rsmd.getColumnCount(); ++i) {
                System.out.print("" + rsmd.getColumnName(i) + "\t");
            }
            while (myResult.next()) {
                System.out.println("\n");
                for (i = 1; i <= rsmd.getColumnCount(); ++i) {
                    System.out.print(" " + myResult.getString(i) + "\t");
                }
            }
        } catch (SQLException var4) {
            logger.log(Level.INFO, "Sql Exception");
        }

    }

// get Query
    public static ResultSet query(Statement statement, String sqlStatement){
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
