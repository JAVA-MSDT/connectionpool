package com.javamsdt.connectionpool.db;


import com.javamsdt.connectionpool.util.DBData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class DBConnector {

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        try {
            Class.forName(DBData.DB_DRIVER);
            connection = DriverManager.getConnection(DBData.DB_URI + DBData.DB_NAME, DBData.LOGIN, DBData.PASSWORD);;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;

    }
}
