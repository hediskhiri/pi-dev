/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author med amine nsir
 */
public class NewFXMain extends Application {
    
    private double x = 0 ;
    private double y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NewFXMain.class.getResource("Backrecl.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("mybike!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}