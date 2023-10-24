/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshopjdbc;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.entities.Panier;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceCategorie;
import tn.edu.esprit.services.ServiceProduit;
import tn.edu.esprit.tools.DatabaseConnection;

public class WorkshopJDBC {

    private static Object serviceProduit;

      Parent fxml;
        private AnchorPane root;
    
    //***************************************************
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        DatabaseConnection.getInstance();

       //  Créez et ajoutez une catégorie à la base de données
        ServiceCategorie serviceCategorie = new ServiceCategorie();
        Categorie categorie1 = new Categorie( "velonormale ");
        categorie1.setIdCategorie(1);
        Categorie categorie2 = new Categorie( "veloelectrique ");
      //  serviceCategorie.ajouter(categorie1);
      //  serviceCategorie.ajouter(categorie2);
      //////////////////////////////////////////////////////////////////////////////////////////////////////
        User u = new User();
        
        
        u.setId(1);
//          ServicePanier servicePanier = new ServicePanier();
//                Panier pn = servicePanier.getPanierByUser(u);
                         
//                System.out.println("prix total du panier = "+pn.getPrixTot());

          
//           for (Produit produit :   servicePanier.getAllProduitByPanier(pn)) {
//    System.out.println("idprod :" + produit.getIdProduit() + ", Nom : " + produit.getNomProd() + ", descriptionProd : " + produit.getDescriptionProd() +  ", Prix : " + produit.getPrixProd() + ", remise : " + produit.getRemise() + ", imageProd : " + produit.getImageProd());
//           }
           
                           //servicePanier.supprimerProduitDuPanier(pn,servicePanier.getAllProduitByPanier(pn).get(0) );
                           
//                               for (Produit produit :   servicePanier.getAllProduitByPanier(pn)) {
//    System.out.println("idprod :" + produit.getIdProduit() + ", Nom : " + produit.getNomProd() + ", descriptionProd : " + produit.getDescriptionProd() +  ", Prix : " + produit.getPrixProd() + ", remise : " + produit.getRemise() + ", imageProd : " + produit.getImageProd());
//           }

                             
ServiceProduit sp = new ServiceProduit();
for (Produit p : sp.getAll());

    }
}



//////////////////////////////////////////////////////////////////////////////////       
     
    // rez la catégorie créée
//        Produit produit1 = new Produit(555, "teeeeee", "Un téléphone ", 100, 5.0f, "image", categorieDuProduit);
//        serviceProduit.ajouter(produit1);
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
//        int idProduitASupprimer = 40;  // Remplacez par l'ID du produit que vous souhaitez supprimer
//        serviceProduit.supprimer(idProduitASupprimer);
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
   
    // Create a cart and add it to the database
 
    //servicePanier.ajouter(panier);

// Fetch the product you want to add to the cart (you may retrieve it from the database)
 //   Produit produitToBeAdded = serviceProduit.getOne(produit1); // Replace 555 with the actual product ID

// Add the product to the cart
//    servicePanier.ajouterProduitAuPanier(panier, produitToBeAdded);

// Display the content of the cart
   // System.out.println("Cart contents:");
   /* for (Produit produit : panier.getProduits()) {
    System.out.println("Nom : " + produit.getNomProd() + ", Prix : " + produit.getPrixProd());*/


     
      
        
        

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

 

      

      
     
