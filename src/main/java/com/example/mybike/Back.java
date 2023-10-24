package com.example.mybike;

import entity.Velo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




public class Back implements Initializable {

    @FXML
    private Button delete_back;

    @FXML
    private ComboBox<?> etat_back;

    @FXML
    private TableColumn<Velo, String> etat_table;

    @FXML
    private TextField id_back;

    @FXML
    private TableColumn<Velo, Integer> id_table;

    @FXML
    private TableColumn<Velo, String> image_table;

    @FXML
    private Button insert_back;
    @FXML
    private Label nb_veloss;
    @FXML
    private ComboBox<?> modele_back;

    @FXML
    private TableColumn<Velo, String> modele_table;

    @FXML
    private TextField prix_back;

    @FXML
    private TableColumn<Velo, Float> prix_table;

    @FXML
    private TextField station_back;

    @FXML
    private TableColumn<Velo, String> station_table;

    @FXML
    private TableView<Velo> table;
    @FXML
    private Label nb_velos;

    @FXML
    private Button update_back;

    private Connection con;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    private String etat[] = {"disponible", "non disponible"};


    public void comboBox(){

        List<String> list = new ArrayList<>();

        for(String data1: etat){

            list.add(data1);

        }

        ObservableList dataList = FXCollections.observableArrayList(list);

        etat_back.setItems(dataList);

    }
    private String modele[] = {"electrique", "mecanique"};

    public void comboBox1(){

        List<String> list = new ArrayList<>();

        for(String data1: modele){

            list.add(data1);

        }

        ObservableList dataList = FXCollections.observableArrayList(list);

        modele_back.setItems(dataList);

    }



    //    INSERT IMAGE


    public void showData() {
        ServiceVelo sv = new ServiceVelo();
        List<Velo> veloList = sv.afficher(); // Assurez-vous que la méthode afficher retourne une liste de vélos

        id_table.setCellValueFactory(new PropertyValueFactory<>("id"));
        modele_table.setCellValueFactory(new PropertyValueFactory<>("modele"));
        etat_table.setCellValueFactory(new PropertyValueFactory<>("etat"));
        prix_table.setCellValueFactory(new PropertyValueFactory<>("prix"));
        image_table.setCellValueFactory(new PropertyValueFactory<>("image"));
        station_table.setCellValueFactory(new PropertyValueFactory<>("station"));

        ObservableList<Velo> data = FXCollections.observableArrayList(veloList); // Convertir la liste en une liste observable

        table.setItems(data); // Définir les données dans la table
    }

    public void insert() {
        con = MyDB.getinstance().getCon();
        ServiceVelo sv = new ServiceVelo();
        int nombreVeloDisponibles = getNombreVeloDisponibles();
        int nombreTotalVelos = getNombreTotalVelos();
        int nombreVeloElectrique = sv.getNombreVeloElectriques();

        try {
            if ( station_back.getText().isEmpty()
                    || prix_back.getText().isEmpty() || etat_back.getSelectionModel().isEmpty()
                    || modele_back.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
            } else {
                //int id = Integer.parseInt(id_back.getText());
                String station = station_back.getText();
                float prix = Float.parseFloat(prix_back.getText());

                // If "etat" and "modele" are ComboBoxes, use getSelectionModel().getSelectedItem() to get the selected values.
                String etat = (String) etat_back.getSelectionModel().getSelectedItem();
                String modele =(String) modele_back.getSelectionModel().getSelectedItem();



                // Create a new Velo object with the values
                Velo velo = new Velo(modele,etat,station,prix);

                sv.ajouter(velo);
                // Incrémentation du nombre de vélos disponibles

                nombreVeloDisponibles++; // Augmentez le nombre de 1
                nombreTotalVelos++;
                nombreVeloElectrique++;

                // Mettez à jour le texte du label nb_velos avec le nouveau nombre de vélos disponibles
                nb_velos.setText("Nombre total de vélos : " + nombreTotalVelos + "\n"
                        + "Nombre de vélos disponibles : " + nombreVeloDisponibles + "\n"
                        + "Nombre de vélos électriques : " + nombreVeloElectrique);
                // To immediately display the current data
                showData();
                clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void selectutilisateur(){

        int num = table.getSelectionModel().getSelectedIndex();

        Velo velo1 = table.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        id_back.setText(String.valueOf(velo1.getId()));
        station_back.setText(velo1.getStation());
        prix_back.setText(String.valueOf(velo1.getPrix()));
        etat_back.getSelectionModel().clearSelection();
        modele_back.getSelectionModel().clearSelection();


    }

    public void update() {
        con = MyDB.getinstance().getCon();
        ServiceVelo sv = new ServiceVelo();

        int nombreVeloDisponibles = getNombreVeloDisponibles();
        int nombreTotalVelos = getNombreTotalVelos();
        int nombreVeloElectrique = sv.getNombreVeloElectriques();

        try {
            // Assurez-vous qu'une ligne est sélectionnée dans le TableView
            Velo veloSelectionne = table.getSelectionModel().getSelectedItem();

            if (veloSelectionne == null) {
                // Gérez le cas où aucune ligne n'est sélectionnée
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une ligne à mettre à jour.");
                alert.showAndWait();
            } else {
                // Récupérez les nouvelles valeurs des champs (station, prix, etat, modele) depuis les contrôles d'interface utilisateur
                String nouvelleStation = station_back.getText();
                float nouveauPrix = Float.parseFloat(prix_back.getText());
                String nouvelEtat = (String) etat_back.getSelectionModel().getSelectedItem();
                String nouveauModele = (String) modele_back.getSelectionModel().getSelectedItem();

                // Mettez à jour l'objet Velo avec les nouvelles valeurs
                veloSelectionne.setStation(nouvelleStation);
                veloSelectionne.setPrix(nouveauPrix);
                veloSelectionne.setEtat(nouvelEtat);
                veloSelectionne.setModele(nouveauModele);

                // Appelez la méthode de mise à jour de ServiceVelo pour mettre à jour la base de données
                sv.modifier(veloSelectionne);

                // Mettez à jour le texte du label nb_velos avec les nouveaux nombres
                nb_velos.setText("Nombre total de vélos : " + nombreTotalVelos + "\n"
                        + "Nombre de vélos disponibles : " + nombreVeloDisponibles + "\n"
                        + "Nombre de vélos électriques : " + nombreVeloElectrique);

                // Pour afficher immédiatement les données mises à jour dans le TableView
                showData();

                // Réinitialisez les contrôles d'interface utilisateur
                clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clear(){

        id_back.setText("");
        station_back.setText("");
        prix_back.setText("");
        

        etat_back.getSelectionModel().clearSelection();
        modele_back.getSelectionModel().clearSelection();
        

    }
    public void delete() {
        con = MyDB.getinstance().getCon();
        ServiceVelo sv = new ServiceVelo();
        int nombreVeloDisponibles = getNombreVeloDisponibles();
        int nombreTotalVelos = getNombreTotalVelos();
        int nombreVeloElectrique = sv.getNombreVeloElectriques();

        try {
            // Get the selected Velo object from the table
            Velo veloToDelete = table.getSelectionModel().getSelectedItem();

            if (veloToDelete != null) {
                int idToDelete = veloToDelete.getId();

                // Call the ServiceVelo method to delete the Velo record from the database
                sv.supprimer(idToDelete);
                nombreVeloDisponibles--; // Augmentez le nombre de 1
                nombreTotalVelos--;
                nombreVeloElectrique--;

                // Mettez à jour le texte du label nb_velos avec le nouveau nombre de vélos disponibles
                nb_velos.setText("Nombre total de vélos : " + nombreTotalVelos + "\n"
                        + "Nombre de vélos disponibles : " + nombreVeloDisponibles + "\n"
                        + "Nombre de vélos électriques : " + nombreVeloElectrique);

                // Refresh the displayed data to reflect the deletion
                showData();
                clear(); // Clear input fields if needed
            } else {
                // Handle the case where no Velo is selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a Velo to delete.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getNombreVeloDisponibles() {
        int nombreVeloDisponibles = 0; // Initialisez la variable à 0 par défaut.

        try {
            String req = "SELECT COUNT(*) FROM velo WHERE etat = 'disponible'";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                nombreVeloDisponibles = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre de vélos disponibles : " + ex.getMessage());
        }

        return nombreVeloDisponibles;
    }

    public int getNombreTotalVelos() {
        int nombreTotalVelos = 0;

        try {
            String req = "SELECT COUNT(*) FROM velo";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                nombreTotalVelos = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre total de vélos : " + ex.getMessage());
        }

        return nombreTotalVelos;
    }




    public int getNombreVeloElectriques() {
        int nombreVeloElectriques = 0;

        try {
            String req = "SELECT COUNT(*) FROM velo WHERE modele = 'electrique'";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                nombreVeloElectriques = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre de vélos électriques : " + ex.getMessage());
        }

        return nombreVeloElectriques;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox();
        comboBox1();
        showData();
        delete_back.setOnAction(event -> delete());
        con = MyDB.getinstance().getCon();



        int nombreVeloDisponibles = getNombreVeloDisponibles();
        int nombreTotalVelos = getNombreTotalVelos();
        int nombreVeloElectriques = getNombreVeloElectriques(); // Appel de la fonction pour obtenir le nombre de vélos électriques


        nb_velos.setText("Nombre total de vélos : " + nombreTotalVelos + "\n" +
                "Nombre de vélos disponibles : " + nombreVeloDisponibles + "\n" +
                "Nombre de vélos électriques : " + nombreVeloElectriques);
    }



}

