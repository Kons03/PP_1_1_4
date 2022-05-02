package jm.task.core.jdbc.util;

import java.sql.*;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import jm.task.core.jdbc.model.User;
import java.util.*;

public class Util {
    private final static String hostName = "localhost";
    private final static String dbName = "mydbtest";
    private final static String userName = "root";
    private final static String password = "myroot10";
    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public Util() {
        try {
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            System.out.println("Соединение не удалось.");
        }
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
                properties.put(Environment.DRIVER, driver);
                properties.put(Environment.URL, connectionURL);
                properties.put(Environment.USER, userName);
                properties.put(Environment.PASS, password);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Ошибка подключения!");
            }
        }
        return sessionFactory;
    }

    public static Connection getMySQLConnection() {
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e){
            System.out.println("Ошибка при закрытии соединения!");
        }
    }
}
