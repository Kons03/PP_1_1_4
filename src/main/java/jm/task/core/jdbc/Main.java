package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.*;

public class Main {
    private static final UserService userv = new UserServiceImpl();
    private static final User user1 = new User("Sergey", "Nemchinkiy",  (byte) 33);
    private static final User user2 = new User("Galileo", "Galiley", (byte) 113);
    private static final User user3 = new User("Dmitro", "Gordon", (byte) 45);
    private static final User user4 = new User("Sid", "Vicious", (byte) 33);
    public static void main(String[] args) {
        userv.createUsersTable();
        userv.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userv.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userv.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userv.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userv.removeUserById(1);
        List<User> strings = userv.getAllUsers();
        for (User i : strings)
            System.out.println(i);

        userv.cleanUsersTable();
        userv.dropUsersTable();
        Util.closeConnection();


    }
}

