package com.project.project2javafx;

import java.sql.*;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "8889";
    private final String LOGIN = "root";
    private final String PASS = "root";
    private final String DB_NAME = "lesson9";

    Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws ClassNotFoundException, SQLException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean isExistsUser(String login, String email) {
        String sql = "SELECT `id` FROM `users` WHERE login = ? OR email = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void regUser(String login, String email, String pass) {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES (?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);
            prSt.setString(3, pass);
            prSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authDBUser(String login, String pass) {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ? AND `password` = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, pass);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
