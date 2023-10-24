/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.controlsfx.control.Notifications;
import stationm.entities.Maintenance;
import stationm.entities.Station;
import stationm.entities.Velo;
import stationm.services.ServiceMaintenance;
import stationm.services.ServiceStation;
import stationm.tools.DataSource;

/**
 * FXML Controller class
 *
 * @author 21655
 */
public class AjoutermaintenanceController implements Initializable {
    
      @FXML
    private Button exit0;
        @FXML
    private Button ajoutermaintenance;
        
          @FXML
    private Button modifiermaintenance;

    @FXML
    private DatePicker enddateenddate;
    
    @FXML
    private ComboBox<String> sortingComboBox;

    
      @FXML
    private TableColumn<Maintenance, Integer> id_mmaintenance;

    
    @FXML
    private ComboBox<String> stationcombo;
    
    private ServiceStation serviceStation;
    
    @FXML
    private ComboBox<String> velocombo;
    
      @FXML
    private TableColumn<Maintenance, Integer> id_vmaintenance;


    @FXML
    private TableColumn<Maintenance, Date> endmaintenance;

   

    @FXML
    private DatePicker startdatestartdate;

    @FXML
    private TableColumn<Maintenance, Date> startmaintenance;

    @FXML
    private Button supprimermaintenance;
    
    


    
     @FXML
    private TableView<Maintenance> table8;
    private Connection cnx;
    private Statement statement;
    private ResultSet result;
    
    
     @FXML
    public void selectmaintenance(){

        int num = table8.getSelectionModel().getSelectedIndex();

        Maintenance maintenance1 = table8.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        stationcombo.setValue(getStationNom(maintenance1.getVelol().getSta().getId_s()));
        velocombo.setValue(getVeloModel(maintenance1.getVelol().getId_v()));
       /* Date startDate = maintenance1.getStart_time();
        Date endDate = maintenance1.getEnd_time();
        
        Instant startInstant = startDate.toInstant();
        Instant endInstant = endDate.toInstant();

        LocalDate startDateLocal = startInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLocal = endInstant.atZone(ZoneId.systemDefault()).toLocalDate();

        startdatestartdate.setValue(startDateLocal);
        enddateenddate.setValue(endDateLocal);*/
        
        
    }
    
    
    
    
     @FXML
    public void selectStation() {
        // Cette méthode est appelée lorsque la station est sélectionnée dans stationcombo
        updateVeloComboBox();
    }
    
    @FXML
public void sortTable() {
    String selectedSortOption = (String) sortingComboBox.getValue();

    if (selectedSortOption != null) {
        if (selectedSortOption.equals("Sort by Start Time")) {
            // Sort the table by start_time
            table8.getSortOrder().clear(); // Clear existing sorting
            table8.getSortOrder().add(startmaintenance);
        } else if (selectedSortOption.equals("Sort by End Time")) {
            // Sort the table by end_time
            table8.getSortOrder().clear(); // Clear existing sorting
            table8.getSortOrder().add(endmaintenance);
        }
    }
}

 private void displayNotification(String title, String text) {
        Notifications.create()
            .title(title)
            .text(text)
            .showInformation(); // You can change this to show a different type of notification
    }

   

   private List<String> getVeloModelsForStation(int stationId) {
    List<String> veloModels = new ArrayList<>();

    String sql = "SELECT model FROM velo WHERE id_s = ?";
    try (Connection connection = DataSource.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, stationId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String model = resultSet.getString("model");
                veloModels.add(model);
            }
        }
    } catch (SQLException e) {
    }

    return veloModels;
}
   private Velo getVeloVelo(int veloId) {
    Velo velo = null;

    try {
        Connection connection = DataSource.getInstance().getConnection();
        String query = "SELECT * FROM velo WHERE id_v = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, veloId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id_v = resultSet.getInt("id_v");
                    String model = resultSet.getString("model");
                    String type = resultSet.getString("type");
                    String status = resultSet.getString("status");
                    int id_s = resultSet.getInt("id_s");

                    velo = new Velo(id_v, model, type, status, getStationById(id_s));
                }
            }
        }
    } catch (SQLException e) {
        // Handle the exception
        e.printStackTrace();
    }

    return velo;
}

private Station getStationById(int stationId) {
    Station station = null;

    try {
        Connection connection = DataSource.getInstance().getConnection();
        String query = "SELECT * FROM station WHERE id_s = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stationId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id_s = resultSet.getInt("id_s");
                    String nom_s = resultSet.getString("nom_s");
                    String emplacement_s = resultSet.getString("emplacement_s");
                    
                    station = new Station(id_s, nom_s, emplacement_s);
                }
            }
        }
    } catch (SQLException e) {
        // Handle the exception
        e.printStackTrace();
    }

    return station;
}

  
   @Override
public void initialize(URL url, ResourceBundle rb) {
    
   
    
     ObservableList<String> items = FXCollections.observableArrayList("Sort by Start Time", "Sort by End Time");

    // Set the items for the ComboBox
    sortingComboBox.setItems(items);

    
    
    
    showData();
    
    // Initialisez votre service de gestion des stations
    serviceStation = ServiceStation.getInstance();

    // Récupérez la liste de toutes les stations
    List<Station> stations = null;
    
    try {
        stations = serviceStation.getAll(new Station());
    } catch (SQLException ex) {
        Logger.getLogger(AjoutermaintenanceController.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Créez une liste unique de noms de stations
    ObservableList<String> uniqueNames = FXCollections.observableArrayList();
    
    for (Station station : stations) {
        uniqueNames.add(station.getNom_s());
    }
    
    // Remplissez le ComboBox avec les noms des stations
    stationcombo.setItems(uniqueNames);

    // Ajoutez un gestionnaire d'événements pour la sélection de la station
    stationcombo.setOnAction(event -> updateVeloComboBox());

    // Initialisez le contenu du velocombo
    velocombo.setItems(FXCollections.observableArrayList());

    // Sélectionnez la première station (ou la station par défaut)
    if (!uniqueNames.isEmpty()) {
        stationcombo.setValue(uniqueNames.get(0));
        updateVeloComboBox();
    }
}

private void updateVeloComboBox() {
    String selectedStation = stationcombo.getValue();

    if (selectedStation != null) {
        // Récupérez l'ID de la station sélectionnée
        int stationId = getStationId(selectedStation);

        // Obtenez la liste des modèles de vélos en fonction de l'ID de la station
        List<String> veloModels = getVeloModelsForStation(stationId);
        
         veloModels = veloModels.stream()
                .distinct()
                .collect(Collectors.toList());
        

        // Mettez à jour le contenu du velocombo
        velocombo.setItems(FXCollections.observableArrayList(veloModels));
    }
}


             
   
      
    
     public void exit(){
        
            System.exit(0);
     }
     
public void MAINTENA() throws SQLException {
    cnx = DataSource.getInstance().getConnection();

    String sql = "INSERT INTO maintenance (id_v, start_time, end_time) VALUES (?, ?, ?)";

    try {
        String selectedVeloModel = velocombo.getValue();
        int selectedVeloId = getVeloId(selectedVeloModel); // Get the ID of the selected bike
        Date startDate = java.sql.Date.valueOf(startdatestartdate.getValue());
        Date endDate = java.sql.Date.valueOf(enddateenddate.getValue());
        
        Maintenance maintenance2 = new Maintenance();
        maintenance2.setVelol(getVeloVelo(selectedVeloId));
        maintenance2.setStart_time(startDate);
        maintenance2.setEnd_time(endDate);

        PreparedStatement prepare = cnx.prepareStatement(sql);
        prepare.setInt(1, selectedVeloId); // Insert the bike ID
        prepare.setDate(2, new java.sql.Date(startDate.getTime()));
        prepare.setDate(3, new java.sql.Date(endDate.getTime()));

        prepare.executeUpdate();
        

    } catch (SQLException e) {
        // Handle the exception

    }

    showData();
   String title = "New Maintenance Added";
    String text = "A new maintenance has been added. Details: ..."; // Include relevant maintenance details

    displayNotification(title, text);
}



public ObservableList<Maintenance> listmaintenance() {
    ObservableList<Maintenance> maintenanceList = FXCollections.observableArrayList();
    cnx = DataSource.getInstance().getConnection();
    String sql = "SELECT m.id_m, m.start_time, m.end_time, m.id_v, v.model, v.type, v.status, v.id_s " +
                 "FROM maintenance m " +
                 "JOIN velo v ON m.id_v = v.id_v";

    try {
        statement = cnx.createStatement();
        result = statement.executeQuery(sql);

        while (result.next()) {
            int id_m = result.getInt("id_m");
            Date start_time = result.getDate("start_time");
            Date end_time = result.getDate("end_time");
            int id_v = result.getInt("id_v");
            String model = result.getString("model");
            String type = result.getString("type");
            String status = result.getString("status");
            int id_s = result.getInt("id_s");

            Velo velo = getVeloVelo(id_v); // Use the method to get the Velo by ID

            Maintenance maintenance1 = new Maintenance(id_m, velo, start_time, end_time);
            maintenanceList.add(maintenance1);
        }
    } catch (SQLException e) {
        // Handle the exception

    }

    return maintenanceList;
}

public void showData() {
    ObservableList<Maintenance> show = listmaintenance();
    
    id_mmaintenance.setCellValueFactory(new PropertyValueFactory<>("id_m"));
    id_vmaintenance.setCellValueFactory(new PropertyValueFactory<>("velol.id_v")); // Update the field reference to use velol.id_v
    startmaintenance.setCellValueFactory(new PropertyValueFactory<>("start_time"));
    endmaintenance.setCellValueFactory(new PropertyValueFactory<>("end_time"));

    table8.setItems(show);
}


       
             
       private int getVeloId(String veloModel) {
        int veloId = 0;
        try {
            Connection connection = DataSource.getInstance().getConnection();
            String query = "SELECT id_v FROM velo WHERE model = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, veloModel);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        veloId = resultSet.getInt("id_v");
                    }
                }
            }
        } catch (SQLException e) {
        }
        return veloId;
    }
       
       private int getStationId(String snom) {
        int stationId = 0;
        try {
            Connection connection = DataSource.getInstance().getConnection();
            String query = "SELECT id_s FROM station WHERE nom_s = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, snom);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        stationId = resultSet.getInt("id_s");
                    }
                }
            }
        } catch (SQLException e) {
        }
        return stationId;
    }
  
       
       
       
        private String getStationNom(int stationid) {
        String stationnom = "";
        try {
            Connection connection = DataSource.getInstance().getConnection();
            String query = "SELECT nom_s FROM station WHERE id_s = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, stationid);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        stationnom = resultSet.getString("nom_s");
                    }
                }
            }
        } catch (SQLException e) {
        }
        return stationnom;
    }
        
        private String getVeloModel(int veloid) {
        String velomodel = "";
        try {
            Connection connection = DataSource.getInstance().getConnection();
            String query = "SELECT model FROM velo WHERE id_v = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, veloid);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        velomodel = resultSet.getString("model");
                    }
                }
            }
        } catch (SQLException e) {
        }
        return velomodel;
    }
        
    
  
  
   public void supprimermain() {
    Maintenance selectedMaintenance = table8.getSelectionModel().getSelectedItem(); // Obtenez la station sélectionnée dans la TableView

    if (selectedMaintenance != null) {
        try {
            int id = selectedMaintenance.getId_m(); // Obtenez l'ID de la station sélectionnée
            ServiceMaintenance.getInstance().supprimer(id); // Appelez la méthode supprimer du service en utilisant l'ID

             //Supprimez la station de la TableView
            table8.getItems().remove(selectedMaintenance);
        } 
        catch (SQLException ex) {
            // Gérez les erreurs en conséquence
            
        }
    }
}
}
  
      

        
   

