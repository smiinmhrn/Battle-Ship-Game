package MyApp.controller;

import MyApp.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private static Connection connection;
    private static Statement statement;
    private DataBase(){}
    public static void makeConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/battleship", "battleship_app", "1234");
        statement = connection.createStatement();
    }
    public static void closeConnection() throws SQLException {
        if (connection != null) {
            statement.close();
            connection.close();
        }
    }
    public static int creatUser(User user) throws SQLException {
        makeConnection();

        statement.execute(String.format("INSERT INTO users (username, name, lastname, point) VALUES ('%s', '%s', '%s', %d)",
                user.getUserName(), user.getName(), user.getLastName(), user.getPoint()),
                Statement.RETURN_GENERATED_KEYS) ;

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        int id = resultSet.getInt(1);
        closeConnection();

        return id;
    }
    public static void deleteUser(User user) throws SQLException {

        makeConnection();
        statement.execute(String.format("DELETE FROM users WHERE id = %d", user.getId()));
        closeConnection();
    }
    public static ArrayList<User> getAllUsers() throws SQLException {
        makeConnection();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("name"), resultSet.getString("lastname"),
                    resultSet.getInt("point")));
        }

        closeConnection();
        return users;
    }
    public static void updateUser(User user) throws SQLException {
        makeConnection();

        statement.execute(String.format("UPDATE users SET username = '%s', name = '%s', lastname = '%s', point = %d" +
                        "WHERE id = %d",
                        user.getUserName(), user.getName(), user.getLastName(), user.getPoint(), user.getId()));

        closeConnection();

    }

}
