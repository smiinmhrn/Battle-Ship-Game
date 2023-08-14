package MyApp.model;

import MyApp.controller.DataBase;

import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id = -1;
    private String userName;
    private String name;
    private String lastName;
    private int point;
    private String password;

    public User(int id, String userName, String name, String lastName, int point, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.point = point;
        this.password = password;
    }

    public User(String userName, String name, String lastName, int point, String password) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.point = point;
        this.password = password;
    }
    public static ArrayList<User> getAllUsers() throws SQLException {
        return DataBase.getAllUsers();
    }
    public void addOrUpdate() throws SQLException {
        if (this.id == -1) this.id = DataBase.creatUser(this);
        else DataBase.updateUser(this);
    }
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPassword() {
        return password;
    }
}
