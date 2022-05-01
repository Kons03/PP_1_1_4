package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private final static String hostName = "localhost";
    private final static String dbName = "mydbtest";
    private final static String userName = "root";
    private final static String password = "myroot10";

    public static Connection getMySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

            return DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Ошибка при подключении к БД!");
        }
        return null;
    }
    public static void closeConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

            DriverManager.getConnection(connectionURL, userName,
                    password).close();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Ошибка при закрытии соединения!");
        }
    }
}
