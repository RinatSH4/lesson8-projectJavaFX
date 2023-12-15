package com.project.project2javafx.contlollers;

import com.project.project2javafx.DB;
import com.project.project2javafx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddArticleController {

    @FXML
    private TextArea full_text_field;

    @FXML
    private TextArea intro_field;

    @FXML
    private TextField title_field;

    @FXML
    void initialize() {

    }

    @FXML
    void addArticle(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String title = title_field.getCharacters().toString();
        String intro = intro_field.getText();
        String text = full_text_field.getText();

        title_field.setStyle("-fx-border-color: #fafafa");
        intro_field.setStyle("-fx-border-color: #fafafa");
        full_text_field.setStyle("-fx-border-color: #fafafa");

        if (title.length() <= 5)
            title_field.setStyle("-fx-border-color: #e06249");
        else if (intro.length() <= 10)
            intro_field.setStyle("-fx-border-color: #e06249");
        else if (text.length() <= 15)
            full_text_field.setStyle("-fx-border-color: #e06249");

        else {
            DB db = new DB();
            db.addArticle(title, intro, text);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articles-main.fxml", stage);
        }
    }

}