/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.services.PanierProduitService;
import tn.edu.esprit.services.ServiceCategorie;
import tn.edu.esprit.services.ServiceProduit;
import workshopjdbc.MainApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class BackController implements Initializable {

    @FXML
    private Button ajouterprod;

    @FXML
    private TableView<Produit> table;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtidprod;

    @FXML
    private TextField txtnomprod;

    @FXML
    private TextField txtprixprod;

    @FXML
    private TextField txtremise;
    @FXML
    private ComboBox<Categorie> list_categorie;
    @FXML
    private Button addimg;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_modifier;

    Path selectedImagePath;
    boolean imageEdited = false;

    @FXML
    void ajouterprod(ActionEvent event) {
        Categorie c = list_categorie.getValue();
        String nomProd = txtnomprod.getText();
        String description = txtdescription.getText();
        String prixProdStr = txtprixprod.getText();
        String remiseStr = txtremise.getText();

        // Check if any of the required fields are empty
        if (nomProd.isEmpty() || description.isEmpty() || prixProdStr.isEmpty() || remiseStr.isEmpty() || c == null) {
            // Display an error alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all required fields.");
            alert.showAndWait();
            return; // Exit the method without proceeding
        }

        // Convert price and remise to integers
        int prixProd = Integer.parseInt(prixProdStr);
        int remise = Integer.parseInt(remiseStr);

        Produit p = new Produit(0, nomProd, description, prixProd, remise, "imageProd", c);

        if (imageEdited) {
            createImageFile();
            String imagePath = selectedImagePath.toString();
            p.setImageProd(imagePath);
        }

        ServiceProduit sp = new ServiceProduit();
        sp.ajouter(p);

        // Clear the table and refresh it with the updated data
        table.getItems().clear();
        List<Produit> all = new ArrayList<>(sp.afficher());
        for (Produit r : all) {
            table.getItems().add(r);
//    void ajouterprod(ActionEvent event) {
//        
//        
//       Categorie c = list_categorie.getValue();
//        Produit p = new Produit(0, txtnomprod.getText(), txtdescription.getText(),Integer.parseInt(txtprixprod.getText()), Integer.parseInt(txtremise.getText()), "imageProd", c);
//
//        ServiceProduit sp = new ServiceProduit();
//        sp.ajouter(p);
//            table.getItems().clear();
//           
//         List<Produit> all = new ArrayList<Produit>(sp.afficher());
//            for(Produit r : all)
//    {
//       
//        table.getItems().add(r);
//       
        }
    }


    @FXML
    void modifierprod(ActionEvent event) {
        Produit produitAModifier = table.getSelectionModel().getSelectedItem();

        if (produitAModifier != null) {
            try {
                if (imageEdited) {
                    createImageFile();
                    String imagePath = selectedImagePath.toString();
                    produitAModifier.setImageProd(imagePath);
                }

                // Récupérer les nouvelles valeurs depuis les champs de texte et le ComboBox
                String nouveauNom = txtnomprod.getText();
                String nouvelleDescription = txtdescription.getText();
                int nouveauPrix = Integer.parseInt(txtprixprod.getText());
                int nouveauRemise = Integer.parseInt(txtremise.getText());
                //int nouvelleRemise = Integer.parseInt(txtremise.getText());
                Categorie nouvelleCategorie = list_categorie.getValue();

                // Mettre à jour les valeurs du produit
                produitAModifier.setNomProd(nouveauNom);
                produitAModifier.setDescriptionProd(nouvelleDescription);
                produitAModifier.setPrixProd(nouveauPrix);
                produitAModifier.setRemise(nouveauRemise);
                produitAModifier.setCategories(nouvelleCategorie);

                // Appeler la fonction de service pour modifier le produit dans la base de données
                ServiceProduit sp = new ServiceProduit();
                sp.modifier(produitAModifier);

                // Mettre à jour l'affichage dans la TableView
                int index = table.getSelectionModel().getSelectedIndex();
                table.getItems().set(index, produitAModifier);
            } catch (NumberFormatException e) {
                // Gérer l'erreur de conversion des valeurs numériques (prix et remise)
                System.err.println("Erreur de conversion : " + e.getMessage());
            }
        } else {
            // Avertissement ou message d'erreur, car aucun produit n'a été sélectionné
            // Vous pouvez afficher un message à l'utilisateur pour lui demander de sélectionner un produit à modifier.
            System.err.println("Aucun produit sélectionné pour la modification.");
        }


    }

    @FXML
    void supprimerprod(ActionEvent event) {
        // Récupérez l'élément sélectionné dans la TableView
        Produit produitASupprimer = table.getSelectionModel().getSelectedItem();

        if (produitASupprimer != null) {
            // Appeler la fonction de service pour supprimer le produit de la base de données
            ServiceProduit sp = new ServiceProduit();
            sp.supprimer(produitASupprimer.getIdProduit());

            // Mettre à jour l'affichage en supprimant l'élément de la TableView
            table.getItems().remove(produitASupprimer);
        } else {
            // Avertissement ou message d'erreur, car aucun produit n'a été sélectionné
            // Vous pouvez afficher un message à l'utilisateur pour lui demander de sélectionner un produit à supprimer.

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ServiceCategorie sc = new ServiceCategorie();
        list_categorie.getItems().addAll(sc.getAll());
        btn_supprimer.setDisable(true);
        btn_modifier.setDisable(true);
        display();
        // TODO
    }


    private void display() {

        table.getItems().clear();
        ServiceProduit sp = new ServiceProduit();
        List<Produit> all = new ArrayList<Produit>(sp.afficher());

        TableColumn<Produit, Hyperlink> column2 =
                new TableColumn<>("nomProd");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("nomProd"));


        TableColumn<Produit, Hyperlink> column3 =
                new TableColumn<>("descriptionProd");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("descriptionProd"));


        TableColumn<Produit, Hyperlink> column4 =
                new TableColumn<>("prixProd");
        column4.setCellValueFactory(
                new PropertyValueFactory<>("prixProd"));


        TableColumn<Produit, Hyperlink> column5 =
                new TableColumn<>("remise");
        column5.setCellValueFactory(
                new PropertyValueFactory<>("remise"));


        TableColumn<Produit, Hyperlink> column6 =
                new TableColumn<>("Categories");
        column6.setCellValueFactory(
                new PropertyValueFactory<>("Categories"));


        table.getColumns().addAll(column5, column3, column4, column2, column6);
        for (Produit r : all) {
            System.out.println(r.getCategories().getNomCategorie());
            table.getItems().add(r);
        }
    }


    @FXML
    private void AddImage(ActionEvent ignored) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            imageEdited = true;
            selectedImagePath = Paths.get(file.getPath());
            imageView.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/tn/edu/esprit/images/uploads/" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void selectproduit() {

        int num = table.getSelectionModel().getSelectedIndex();

        Produit produit1 = table.getSelectionModel().getSelectedItem();

        if (num - 1 < -1)
            return;

        txtnomprod.setText(String.valueOf(produit1.getNomProd()));
        txtdescription.setText(produit1.getDescriptionProd());
        txtprixprod.setText(String.valueOf(produit1.getPrixProd()));

        float remise = produit1.getRemise();
        int remiseAsInt = (int) remise;
        txtremise.setText(String.valueOf(remiseAsInt));

        list_categorie.getSelectionModel().clearSelection();
        list_categorie.setValue(produit1.getCategories());

        btn_supprimer.setDisable(false);
        btn_modifier.setDisable(false);

        Path selectedImagePath = FileSystems.getDefault().getPath(produit1.getImageProd());
        if (selectedImagePath.toFile().exists()) {
            imageView.setImage(new Image(selectedImagePath.toUri().toString()));
        } else {
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/tn/edu/esprit/images/mdi/image-placeholder.png")).toExternalForm()));
        }
    }

    @FXML
    public void logout(ActionEvent ignored) {
        MainApp.getInstance().logout();
    }

    @FXML
    public void genererExel(ActionEvent ignored) {
        List<Produit> listProduit = PanierProduitService.getInstance().getAllProduit();

        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            FileChooser chooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
            chooser.getExtensionFilters().add(filter);

            HSSFSheet workSheet = workbook.createSheet("sheet 0");
            workSheet.setColumnWidth(1, 25);

            HSSFFont fontBold = workbook.createFont();
            fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(fontBold);

            Row row1 = workSheet.createRow((short) 0);
            row1.createCell(0).setCellValue("Id");
            row1.createCell(1).setCellValue("Nom");
            row1.createCell(2).setCellValue("Description");
            row1.createCell(3).setCellValue("Prix");
            row1.createCell(4).setCellValue("Remise");
            row1.createCell(5).setCellValue("Categorie");

            int i = 0;
            for (Produit produit : listProduit) {
                i++;
                Row row2 = workSheet.createRow((short) i);
                row2.createCell(0).setCellValue(produit.getIdProduit());
                row2.createCell(1).setCellValue(produit.getNomProd());
                row2.createCell(2).setCellValue(produit.getDescriptionProd());
                row2.createCell(3).setCellValue(produit.getPrixProd());
                row2.createCell(4).setCellValue(produit.getRemise());
                row2.createCell(5).setCellValue(produit.getCategories().toString());
            }

            workSheet.autoSizeColumn(0);
            workSheet.autoSizeColumn(1);
            workSheet.autoSizeColumn(2);
            workSheet.autoSizeColumn(3);
            workSheet.autoSizeColumn(4);
            workSheet.autoSizeColumn(5);

            workbook.write(Files.newOutputStream(Paths.get("produit.xls")));
            Desktop.getDesktop().open(new File("produit.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
