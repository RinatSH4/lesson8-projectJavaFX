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
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistsUser(String login) {
        String sql = "SELECT `id` FROM `users` WHERE login = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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
            e.printStackTrace();
        }
        return false;
    }

    public String getEmail(String login) {
        String sql = "SELECT `email` FROM `users` WHERE `login` = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);

            ResultSet res = prSt.executeQuery();
            res.next();
            return res.getString("email");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

    public void setUpdate(String login, String email, String pass) {
        String sql = "UPDATE `users` SET `email` = ?, `password` = ? WHERE `users`.`login` = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, email);
            prSt.setString(2, pass);
            prSt.setString(3, login);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        String sql = "SELECT `intro`, `title` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        return statement.executeQuery(sql);
    }

    public void addArticle(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES (?, ?, ?, 10)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setString(2, intro);
        prSt.setString(3, text);
        prSt.executeUpdate();
    }

    public ResultSet getArticleText(String articalTitle) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `articles` WHERE `title` = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, articalTitle);
        return prSt.executeQuery();
    }

//    public String getArticleId(String articalTitle) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM `articles` WHERE `title` = ?";
//        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
//        prSt.setString(1, articalTitle);
//
//        ResultSet res = prSt.executeQuery();
//        res.next();
//        return res.getString("id");
//    }
}
//    При подключении может возникнуть ошибка,
//    что будет связана с разными часовыми поясами.
//    Чтобы её исправить пропишите к строке подключения дополнительные опции:

// jdbc:mysql://localhost/db
// ?useUnicode=true&useJDBCCompliantTimezoneShift=
// true&useLegacyDatetimeCode=false&serverTimezone=UTC
