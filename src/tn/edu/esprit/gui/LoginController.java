package tn.edu.esprit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import workshopjdbc.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    public void frontend(ActionEvent ignored) {
        MainApp.getInstance().loadFront();
    }

    @FXML
    public void backend(ActionEvent ignored) {
        MainApp.getInstance().loadBack();
    }
}
