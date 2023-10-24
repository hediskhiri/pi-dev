module com.example.mybike {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires twilio;


    opens com.example.mybike to javafx.fxml;
    exports com.example.mybike;
    exports entity;
    opens entity to javafx.fxml;
}