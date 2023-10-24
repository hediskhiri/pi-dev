package resources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import stationm.entities.Station;
import stationm.entities.Velo;
import stationm.services.ServiceStation;
import stationm.services.ServiceVelo;
import stationm.tools.DataSource;

public class StationM extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ajoutstation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("mybike!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        String str = "2022-06-12";
        Date date = Date.valueOf(str);
        String strr = "2022-06-19";
        Date datee = Date.valueOf(strr);
        Connection cnx;
        cnx=DataSource.getInstance().getConnection();
        //ServiceStation sr = new ServiceStation();
        //Station S=new Station(23,"atallahi","nabeul");
        //sr.ajouter(S);
        //sr.supprimer(4);
        
        
        //ServiceVelo srr = new ServiceVelo();
        //Velo V=new Velo(62,"électrique","bich","chich",S);
        //srr.ajouter(V); 
        
        
        //S.getVelos().add(V);
       
        //ServiceMaintenance srr = new ServiceMaintenance();
        //Maintenance M=new Maintenance(1,6,date,datee);
        //srr.ajouter(M);
       //ServiceStation stationService = new ServiceStation();
       //stationService.ajouterVeloAStation(8, 6);*/
       
    
       
        launch(args);
    }
}
