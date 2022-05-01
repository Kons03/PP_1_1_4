package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private final String table = "new_table";
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getMySQLConnection();
    }

    public void createUsersTable() {
        try {
            connection.setAutoCommit(false);
            String str = "CREATE TABLE `mydbtest`.`"+table+"` ( " +
                    " `id` INT NOT NULL AUTO_INCREMENT, " +
                    " `name` VARCHAR(50) NULL, " +
                    " `lastName` VARCHAR(50) NULL, " +
                    " `age` INT(3) NULL, " +
                    " PRIMARY KEY (`id`));";
            PreparedStatement stm = connection.prepareStatement(str);
            stm.executeUpdate();
            connection.commit();

            System.out.println("Таблица создана.");

        } catch (SQLException ignore) {

        }
    }

    public void dropUsersTable() {
        try {
            connection.setAutoCommit(false);
            String str = "DROP TABLE " + table + ";";
            PreparedStatement stm = connection.prepareStatement(str);
            stm.executeUpdate();
            connection.commit();

            System.out.println("Таблица удалена.");

        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            String str = "insert into " + table + " (name, lastName, age) values(?,?,?);";
            PreparedStatement stm = connection.prepareStatement(str);
            stm.setString(1, name);
            stm.setString(2, lastName);
            stm.setByte(3, age);
            stm.executeUpdate();
            connection.commit();

            System.out.println("User с именем " + name + " добавлен в базу данных.");

        } catch (SQLException ignore) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            String str = "DELETE FROM " + table + " WHERE id = ?;";
            PreparedStatement stm = connection.prepareStatement(str);
            stm.setLong(1, id);
            stm.executeUpdate();
            connection.commit();

            System.out.println("User с id №" + id + " удален");

        } catch (SQLException ignore) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
    List<User> list = new ArrayList<>();
        try {
        connection.setAutoCommit(false);
        String str = "select id, name, lastName, age from " + table +";";
        PreparedStatement stm = connection.prepareStatement(str);
        ResultSet rs = stm.executeQuery();
        connection.commit();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("lastName"));
            user.setAge(rs.getByte("age"));
            list.add(user);
        }

    } catch (SQLException ignore) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return list;
    }
    public void cleanUsersTable() {
        try {
            connection.setAutoCommit(false);
            String str = "DELETE FROM " + table + ";";
            PreparedStatement stm = connection.prepareStatement(str);
            stm.executeUpdate();
            connection.commit();

            System.out.println("Данные в таблице удалены.");

        } catch (SQLException ignore) {

        }
    }
}
