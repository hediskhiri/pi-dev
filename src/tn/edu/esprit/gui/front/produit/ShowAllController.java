package tn.edu.esprit.gui.front.produit;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.edu.esprit.entities.PanierProduit;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.gui.front.MainWindowController;
import tn.edu.esprit.gui.front.panier.MonPanierController;
import tn.edu.esprit.services.PanierProduitService;
import tn.edu.esprit.tools.AlertUtils;
import tn.edu.esprit.tools.Constants;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowAllController implements Initializable {

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;

    List<Produit> listProduit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProduit = PanierProduitService.getInstance().getAllProduit();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listProduit);

        if (!listProduit.isEmpty()) {
            for (Produit produit : listProduit) {
                mainVBox.getChildren().add(makeProduitModel(produit));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeProduitModel(
            Produit produit
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PRODUIT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));

            Path selectedImagePath = FileSystems.getDefault().getPath(produit.getImageProd());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }
            ((Text) innerContainer.lookup("#idNomProd")).setText("Nom : " + produit.getNomProd());
            ((Text) innerContainer.lookup("#idDescriptionProd")).setText("Description : " + produit.getDescriptionProd());
            ((Text) innerContainer.lookup("#idPrixProd")).setText("Prix : " + produit.getPrixProd());
            ((Text) innerContainer.lookup("#idRemise")).setText("Remise : " + produit.getRemise());

            if (produit.getCategories().getNomCategorie() != null) {
                ((Text) innerContainer.lookup("#idCategories")).setText("Categorie : " + produit.getCategories().getNomCategorie());
            } else {
                ((Text) innerContainer.lookup("#idCategories")).setText("Categorie non specifié");
            }

            ((Button) innerContainer.lookup("#addToCartButton")).setOnAction(event -> addToCart(produit));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    void addToCart(Produit produit) {
        PanierProduit panierProduit = new PanierProduit(produit, MonPanierController.getPanier(), 1);

        boolean success = PanierProduitService.getInstance().add(panierProduit);
        if (success) {
            AlertUtils.makeSuccessNotification("Produit ajouté avec succés");
            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
        } else {
            AlertUtils.makeError("Produit déja ajouté au panier");
        }
    }
}
