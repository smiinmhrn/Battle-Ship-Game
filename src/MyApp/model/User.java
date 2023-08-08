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

    public User(int id, String userName, String name, String lastName, int point) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.point = point;
    }
    public User(String userName, String name, String lastName, int point) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.point = point;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
