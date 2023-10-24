/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import stationm.entities.Station;
import stationm.services.ServiceStation;
import stationm.tools.DataSource;

/**
 * FXML Controller class
 *
 * @author 21655
 */
public class AjoutstationController implements Initializable {
    
    @FXML
    private Button backstation;
    
     @FXML
    private Button ajoutajout;
     
      @FXML
    private Button modifiermodifier;
      @FXML
    private TextField emplacementSearchField;
      
      @FXML
    private TextArea listvelo;
      
      @FXML
    private TextField nomstation;
      
      

    @FXML
    private Button supprimersupprimer;
    
     @FXML
    private TextField emplacementstation;
    
     @FXML
    private AnchorPane map;
     
     @FXML
    private Button excels;
     

    @FXML
    private AnchorPane map0;
    @FXML
    private Button exit;
    @FXML
    private Button confirmerstation;
    @FXML
    private TableColumn<Station,String> emplacement_col;

    @FXML
    private TableColumn<Station, Integer> id_col;
    
    

    @FXML
    private TableColumn<Station, String> nom_col;
    
   

    @FXML
    private TableView<Station> table;
    private Connection cnx;
    private Statement statement;
    private ResultSet result;
    
    @FXML
    public void selectstation(){

        int num = table.getSelectionModel().getSelectedIndex();

        Station station1 = table.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        nomstation.setText(String.valueOf(station1.getNom_s()));
        emplacementstation.setText(station1.getEmplacement_s());
        
        
        
    }
    
public void exportToExcel() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Station Data");

    // Create header row
    XSSFRow headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("ID");
    headerRow.createCell(1).setCellValue("Nom");
    headerRow.createCell(2).setCellValue("Emplacement");

    // Fetch data from the table and populate the Excel sheet
    ObservableList<Station> data = table.getItems();
    for (int i = 0; i < data.size(); i++) {
        XSSFRow row = sheet.createRow(i + 1);
        row.createCell(0).setCellValue(data.get(i).getId_s());
        row.createCell(1).setCellValue(data.get(i).getNom_s());
        row.createCell(2).setCellValue(data.get(i).getEmplacement_s());
    }

    try (FileOutputStream fileOut = new FileOutputStream("StationData.xlsx")) {
        workbook.write(fileOut);
        fileOut.close();
        showAlert(Alert.AlertType.INFORMATION, "Export Successful", "Station data exported to StationData.xlsx.");
        File excelFile = new File("StationData.xlsx");
        Desktop.getDesktop().open(excelFile);
    } catch (IOException e) {
        showAlert(Alert.AlertType.ERROR, "Export Failed", "An error occurred during export.");
    }
}   
    
    
    


private void showAlert(Alert.AlertType alertType, String title, String content) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}


    
    
    
   public void modifier() {
    Station selectedStation = table.getSelectionModel().getSelectedItem(); // Obtenez la réclamation sélectionnée dans la TableView

    if (selectedStation != null) {
        
        try {
            selectedStation.setNom_s(nomstation.getText()); // Remplacez "nouveauTitre" par le titre modifié
            selectedStation.setEmplacement_s(emplacementstation.getText()); // Remplacez "nouvelleDescription" par la description modifiée

            ServiceStation.getInstance().modifier(selectedStation);
        
            table.refresh();
        } catch (SQLException ex) {
           
            
        }
    }
}
    
    public void supprimer() {
    Station selectedStation = table.getSelectionModel().getSelectedItem(); // Obtenez la station sélectionnée dans la TableView

    if (selectedStation != null) {
        try {
            int id = selectedStation.getId_s(); // Obtenez l'ID de la station sélectionnée
            ServiceStation.getInstance().supprimer(id); // Appelez la méthode supprimer du service en utilisant l'ID

             //Supprimez la station de la TableView
            table.getItems().remove(selectedStation);
        } 
        catch (SQLException ex) {
            // Gérez les erreurs en conséquence
            
        }
    }
}
    public void searchByEmplacement() {
    String searchText = emplacementSearchField.getText(); // Get the search text from the TextField
    List<Station> filteredStations = new ArrayList<>();

    table.getItems().stream().filter((station) -> (station.getEmplacement_s().toLowerCase().contains(searchText.toLowerCase()))).forEachOrdered((station) -> {
        // If the station's emplacement matches the search text (case-insensitive), add it to the filtered list
        filteredStations.add(station);
        });

    ObservableList<Station> filteredData = FXCollections.observableArrayList(filteredStations);
    table.setItems(filteredData); // Update the TableView with the filtered data
}

    
    public void exit(){
        
            System.exit(0);
    }
    
    public ObservableList<Station> liststation(){
        ObservableList<Station> stationList = FXCollections.observableArrayList();
        cnx = DataSource.getInstance().getConnection();
        String sql = "SELECT * FROM station";

        try{
            statement = cnx.createStatement();
            result = statement.executeQuery(sql);
            Station station1;

            while(result.next()){
                station1 = new Station(
                        result.getInt("id_s"),
                        result.getString("nom_s"),
                        result.getString("emplacement_s")
                        
                );

                stationList.add(station1);
            }
        } catch(SQLException e){
        }

        return stationList;
    }
       
       public void showData(){

        ObservableList<Station> show = liststation();

        id_col.setCellValueFactory(new PropertyValueFactory<>("id_s"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom_s"));
        emplacement_col.setCellValueFactory(new PropertyValueFactory<>("emplacement_s"));
        

        table.setItems(show);
        

    }
       public void changepage(){
            if(backstation.isFocused()){
               map0.setVisible(true);
               map.setVisible(false);
           }
           
           if (confirmerstation.isFocused()) {
        Station stationSelectionnee = table.getSelectionModel().getSelectedItem();

        if (stationSelectionnee != null) {
            // Récupérez l'ID de la station sélectionnée
            int idStationSelectionnee = stationSelectionnee.getId_s();

            // Réalisez la requête SQL pour récupérer les modèles des vélos de cette station
            String sql = "SELECT model FROM velo WHERE id_s = ?";

            try {
                PreparedStatement preparedStatement = cnx.prepareStatement(sql);
                preparedStatement.setInt(1, idStationSelectionnee);
                ResultSet resultSet = preparedStatement.executeQuery();
                HashSet<String> uniqueModels = new HashSet<>(); // Create a HashSet to store unique models
                StringBuilder modeleVelo = new StringBuilder();
                while (resultSet.next()) {
    String model = resultSet.getString("model");
    if (!uniqueModels.contains(model)) { // Check if the model is not already in the HashSet
        modeleVelo.append(model).append("\n ");
        uniqueModels.add(model); // Add the model to the HashSet
    }
}

                if (modeleVelo.length() > 0) {
                    modeleVelo.setLength(modeleVelo.length() - 2); // Pour supprimer la virgule finale
                    
                    
                    listvelo.setText(modeleVelo.toString());
                    
                    map.setVisible(true);
                    map0.setVisible(false);
                } else {
                    listvelo.setText("Aucun vélo dans cette station.");
                    map.setVisible(true);
                    map0.setVisible(false);
                }

            } catch (SQLException e) {
            }
        } else {
            listvelo.setText("Veuillez sélectionner une station.");
        }
    }
}

    // Autres méthodes et initialisation ici

           
          
      
          
       
       
         public void SEND() throws SQLException {
    cnx = DataSource.getInstance().getConnection();

    String sql = "INSERT INTO station (nom_s, emplacement_s) VALUES (?, ?)";

    try {
        if (nomstation.getText().isEmpty() || emplacementstation.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        } else {
            
            
            
            Station station = new Station();
            station.setNom_s(nomstation.getText());
            station.setEmplacement_s(emplacementstation.getText());
            java.util.Date currentDate = new java.util.Date();
            

            
                PreparedStatement prepare = cnx.prepareStatement(sql);
                prepare.setString(1, station.getNom_s());
                prepare.setString(2, station.getEmplacement_s());
                
                prepare.executeUpdate();

                nomstation.setText("");
                emplacementstation.setText("");
                
                
               
            
            }
    } catch (SQLException e) {
      
        
    }
    showData();
}
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       excels.setOnAction(event -> {
        exportToExcel();
    });
        showData();
    }
}

 
    
    

        
     
    

