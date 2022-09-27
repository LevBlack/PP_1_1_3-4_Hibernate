package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connect = Util.getConnection()) {
            try (Statement state = connect.createStatement()) {
                connect.setAutoCommit(false);

                state.executeUpdate("CREATE TABLE IF NOT EXISTS user(" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(30)," +
                        "lastName VARCHAR(50)," +
                        "age INT)");
                connect.commit();

            } catch (SQLException e) {
                connect.setAutoCommit(true);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Connection connect = Util.getConnection()) {
            try (Statement state = connect.createStatement()) {

                connect.setAutoCommit(false);
                state.executeUpdate("DROP TABLE IF EXISTS user");
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connect = Util.getConnection()) {
            try (Statement state = connect.createStatement()) {

                connect.setAutoCommit(false);
                state.executeUpdate("INSERT user(name,lastName,age) VALUES ('" + name + "','"
                        + lastName + "'," + age + ")");
                System.out.println("User с именем – " + name + " добавлен в базу данных");
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                connect.setAutoCommit(true);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connect = Util.getConnection()) {
            try (Statement state = connect.createStatement()) {

                connect.setAutoCommit(false);
                state.executeUpdate("DELETE FROM user WHERE Id = " + id);
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                connect.setAutoCommit(true);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getAllUsers() {
        try (Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {

            List<User> list = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {

                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                User user = new User(name, lastName, (byte) age);
                list.add(user);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connect = Util.getConnection()) {
            try (Statement state = connect.createStatement()) {

                connect.setAutoCommit(false);
                state.executeUpdate("DELETE from user");
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                connect.setAutoCommit(true);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
