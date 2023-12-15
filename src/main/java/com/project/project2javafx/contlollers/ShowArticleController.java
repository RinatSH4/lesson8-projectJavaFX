package com.project.project2javafx.contlollers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.project.project2javafx.DB;
import com.project.project2javafx.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShowArticleController {

    @FXML
    private Button backBtn;

    @FXML
    private Label textLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DB db = new DB();
        ResultSet resultSet = db.getArticleText(articlesPanelController.getId());
        resultSet.next();
        titleLabel.setText(resultSet.getString("title"));
        textLabel.setText(resultSet.getString("text"));

        backBtn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("articles-main.fxml", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}