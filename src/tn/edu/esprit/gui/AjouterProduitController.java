/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.card;
import tn.edu.esprit.services.ServiceProduit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private AnchorPane card1;
    @FXML
    private Button ajouterpanier;
    @FXML
    private AnchorPane card11;
    @FXML
    private Button ouvrurpanier;
    @FXML
    private Hyperlink hyper1;
    @FXML
    private Hyperlink nextpage;
    @FXML
    private Button ajouteraupanier;
    @FXML
    private AnchorPane card111;
    @FXML
    private Button ouvrirpanier;
    @FXML
    private Hyperlink hyper11;
    @FXML
    private Hyperlink retourpage;
    @FXML
    private AnchorPane card112;
    @FXML
    private Hyperlink hyper12;
    @FXML
    private Hyperlink retour;
    @FXML
    private AnchorPane card1121;
    @FXML
    private Button ajouteraupanier1;
    @FXML
    private Button ouvrirpanier1;
    @FXML
    private Hyperlink hyper121;
    @FXML
    private Hyperlink nextpage1;
    @FXML
    private Hyperlink retour1;
    @FXML
    private HBox cardLayout;

    /* @Override
     public void initialize(URL url, ResourceBundle rb) {
         // Retrieve your products from the database.
         ServiceProduit sp = new ServiceProduit();
         List<Produit> produits = new ArrayList<>(sp.afficher());

         // Create a new card for each product.
         List<card> cards = new ArrayList<>();
         for (Produit produit : produits) {
             cards.add(new card(produit));
         }

         // Add the cards to the cardLayout HBox.
         for (card card : cards) {
             cardLayout.getChildren().addAll(card);
         }
     }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Retrieve your products from the database.
        ServiceProduit sp = new ServiceProduit();
        List<Produit> produits = new ArrayList<>(sp.afficher());

        // Create a new card for each product.
        List<card> cards = new ArrayList<>();

        for (Produit produit : produits) {
            card card = new card(produit);
            cardLayout.getChildren().add(card);

        }
    }


    private void ajouterprod(ActionEvent event) {
        //
    }
}

