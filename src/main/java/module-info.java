module com.project.project2javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.project.project2javafx to javafx.fxml;
    exports com.project.project2javafx;
    exports com.project.project2javafx.contlollers;
    opens com.project.project2javafx.contlollers to javafx.fxml;
}