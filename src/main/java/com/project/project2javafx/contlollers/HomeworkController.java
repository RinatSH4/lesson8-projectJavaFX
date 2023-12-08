package com.project.project2javafx.contlollers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.project2javafx.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HomeworkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField e_login;

    @FXML
    private TextField e_mail;

    @FXML
    private PasswordField e_password;

    @FXML
    private Button edit_button;

    @FXML
    private Label infoLabel;

    DB db = new DB();

    @FXML
    void initialize() {
        e_login.setText("Admin");
        e_mail.setText(db.getEmail("Admin"));

        edit_button.setOnAction(event -> {
            updateTable();
        });
    }

    private void updateTable() {
        String login = e_login.getCharacters().toString();
        String email = e_mail.getCharacters().toString();
        String pass = e_password.getCharacters().toString();

        if (login.length() <= 1)
            e_login.setStyle("-fx-border-color: #e06249");
        else if (email.length() <= 5 || !email.contains("@") || !email.contains("."))
            e_mail.setStyle("-fx-border-color: #e06249");
        else if (pass.length() <= 3)
            e_password.setStyle("-fx-border-color: #e06249");
        else if (db.isExistsUser(login)) {
            db.setUpdate(login, email, RegController.md5String(pass));
            e_login.setText("");
            e_mail.setText("");
            e_password.setText("");
            infoLabel.setStyle("-fx-text-fill: #fafafa");
            infoLabel.setText("Данные обновлены");
        }
        else {
            infoLabel.setStyle("-fx-text-fill: red");
            infoLabel.setText("Пользователь не найден");
        }
    }
}