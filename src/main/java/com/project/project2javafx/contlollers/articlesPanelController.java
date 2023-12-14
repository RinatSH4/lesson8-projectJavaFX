package com.project.project2javafx.contlollers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.project.project2javafx.DB;
import com.project.project2javafx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class articlesPanelController {

    @FXML
    private VBox panelVbox;

    @FXML
    private Button exit_button;
    @FXML
    private Button edit_btn;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, IOException {
        DB db = new DB();
        ResultSet resultSet = db.getArticles();

        while (resultSet.next()) {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("article.fxml")));
            Label title = (Label) node.lookup("#title");
            Label intro = (Label) node.lookup("#intro");

            title.setText(resultSet.getString("title"));
            intro.setText(resultSet.getString("intro"));

            node.setOnMouseEntered(event -> {
                node.setStyle("-fx-background-color: #707173");
            });

            node.setOnMouseExited(event -> {
                node.setStyle("-fx-background-color: #343434");
            });

            panelVbox.getChildren().add(node);
            panelVbox.setSpacing(10);
        }


        exit_button.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        edit_btn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("homework.fxml", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void exitUser(ActionEvent event) throws IOException {
        File file = new File("user.settings");
        file.delete();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml", stage);
    }

}
