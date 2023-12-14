package com.project.project2javafx.contlollers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.project.project2javafx.HelloApplication;
import com.project.project2javafx.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import com.project.project2javafx.DB;
import javafx.stage.Stage;

public class RegController {

    @FXML
    private Label labelInfo;

    @FXML
    private Button auth_button;

    @FXML
    private TextField auth_login;

    @FXML
    private PasswordField auth_password;

    @FXML
    private Button reg_button;

    @FXML
    private TextField reg_login;

    @FXML
    private TextField reg_mail;

    @FXML
    private PasswordField reg_password;

    @FXML
    private CheckBox reg_rights;

    private DB db = new DB();

    private static String authLogin;


    @FXML
    void initialize() {
        reg_button.setOnAction(event -> {
            registrationUser();
        });

        auth_button.setOnAction(event -> {
            try {
                authUser(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static String getLogin() {
        return authLogin;
    }
    private void authUser(ActionEvent event) throws IOException {
        String login = auth_login.getCharacters().toString();
        String pass = auth_password.getCharacters().toString();
        authLogin = login;

        auth_login.setStyle("-fx-border-color: #fafafa");
        auth_password.setStyle("-fx-border-color: #fafafa");

        if (login.length() <= 1)
            auth_login.setStyle("-fx-border-color: #e06249");
        else if (pass.length() <= 3)
            auth_password.setStyle("-fx-border-color: #e06249");
        else if (!db.authDBUser(login, md5String(pass))) {
            System.out.println(login + " : " + pass);
            labelInfo.setStyle("-fx-text-fill: red");
            labelInfo.setText("Пользователь не найден");
            System.out.println(login + " : " + md5String(pass));
        }
        else {
            auth_login.setText("");
            auth_password.setText("");
            labelInfo.setStyle("-fx-text-fill: #fafafa");
            labelInfo.setText("Вы залогинились!");
            auth_button.setDisable(true);
            System.out.println(login + " : " + md5String(pass));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articles-main.fxml", stage);

            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();
        }
    }

    private void registrationUser() {
        String login = reg_login.getCharacters().toString();
        String email = reg_mail.getCharacters().toString();
        String pass = reg_password.getCharacters().toString();

        reg_login.setStyle("-fx-border-color: #fafafa");
        reg_mail.setStyle("-fx-border-color: #fafafa");
        reg_password.setStyle("-fx-border-color: #fafafa");

        if (login.length() <= 1)
            reg_login.setStyle("-fx-border-color: #e06249");
        else if (email.length() <= 5 || !email.contains("@") || !email.contains("."))
            reg_mail.setStyle("-fx-border-color: #e06249");
        else if (pass.length() <= 3)
            reg_password.setStyle("-fx-border-color: #e06249");
        else if (!reg_rights.isSelected()) {
            labelInfo.setStyle("-fx-text-fill: red");
            labelInfo.setText("Поставьте галочку");
        }
        else if (db.isExistsUser(login, email)) {
            labelInfo.setStyle("-fx-text-fill: red");
            labelInfo.setText("Такой пользователь или email уже существует");
        }
        else {
            db.regUser(login, email, md5String(pass));
            reg_login.setText("");
            reg_mail.setText("");
            reg_password.setText("");
            labelInfo.setStyle("-fx-text-fill: #fafafa");
            labelInfo.setText("Вы успешно зарегистрированы");
        }
    }

    public static String md5String(String pass) {
        MessageDigest md = null;
        byte[] digest = new byte[0];

        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(pass.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32)
            md5Hex = "0" + md5Hex;

        return md5Hex;
    }
}
