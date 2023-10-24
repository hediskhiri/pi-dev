/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.edu.esprit.entities.Produit;

/**
 * @author Lenovo
 */
public class cardController {

    @FXML
    private VBox commander;

    @FXML
    private Label nomProduit;

    //@FXML
    //private String picProduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label remiseProduit;

    /*public void setData(Produit produit) {
        ServiceProduit sp = new ServiceProduit();
        nomProduit.setText(produit.getNomProd());
        prixProduit.setText(Integer.toString(produit.getPrixProd()));
        remiseProduit.setText(Float.toString(produit.getRemise()));
    }*/
    public void setData(Produit produit) {
        nomProduit.setText("Produit" + produit.getNomProd());
        prixProduit.setText("Prix: " + produit.getPrixProd());
        remiseProduit.setText("Remise: " + produit.getRemise());
    }
}
