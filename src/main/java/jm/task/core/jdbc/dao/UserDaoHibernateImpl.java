package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

public class UserDaoHibernateImpl implements UserDao {

    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS user"
                + "(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                "name CHAR(45) NOT NULL, " +
                "lastName CHAR(45) NOT NULL," +
                "age tinyint," +
                "PRIMARY KEY (id))";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            session.createSQLQuery("alter table user AUTO_INCREMENT=0");
            System.out.println("Таблица создана.");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Таблица не создана!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS user";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(dropTable).executeUpdate();
            System.out.println("Таблица удалена.");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Таблица не удалена.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("Пользователь " + name + " " + lastName + " внесен в базу данных.");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось внести указанного пользователя!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = id").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        System.out.println("Получен список пользователей.");
        return session.createQuery("from User").list();
    }

    @Override
    public void cleanUsersTable() {
        List<User> list = getAllUsers();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
