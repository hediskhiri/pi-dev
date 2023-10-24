//package tn.edu.esprit.entities;
//import javafx.scene.layout.AnchorPane;
//
// 
//
//public class card extends AnchorPane {
//    
//    private String produitName;
//    private double prix;
//    private double remise;
//    private String image;
//
//    public card(Produit produit) {
//        this.produitName = produit.getNomProd();
//        this.prix = produit.getPrixProd();
//        this.remise = produit.getRemise();
//        this.image = produit.getImageProd();
//    }
//    
//
//    public String getProduitName() {
//        return produitName;
//    }
//
//    public double getPrix() {
//        return prix;
//    }
//
//    public double getRemise() {
//        return remise;
//    }
//
//    public String getImage() {
//        return image;
//    }
//}

package tn.edu.esprit.entities;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class card extends VBox {
    private Label nomProduitLabel;
    private Label prixProduitLabel;
    private Label remiseProduitLabel;
    private ImageView imageProduitView;

    public card(Produit produit) {
        nomProduitLabel = new Label("nomproduit" + produit.getNomProd());
        prixProduitLabel = new Label("Prix: " + produit.getPrixProd());
        remiseProduitLabel = new Label("Remise: " + produit.getRemise());
        // Assuming you have the image URL in produit.getImageProd(), you can set it to an ImageView here.

        // Add the components to the card
        getChildren().addAll(nomProduitLabel, prixProduitLabel, remiseProduitLabel, imageProduitView);
    }
}
