///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tn.edu.esprit.services;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import tn.edu.esprit.entities.Panier;
//import tn.edu.esprit.entities.Produit;
//import tn.edu.esprit.entities.User;
//import tn.edu.esprit.tools.DataSource;
///**
// *
// * @author Lenovo
// */
//public class ServicePanier implements IService<Panier> {
//Connection cnx ;
//
//public ServicePanier(){
//    this.cnx= DataSource.getInstance().getConnection();
//}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    public void ajouterProduitAuPanier(Panier panier, Produit produit) {
//        try {
//            // Assurez-vous que le panier existe
//            if (panier != null && produit != null) {
//                // Insérez le produit dans le panier
//                String insertProduitQuery = "INSERT INTO Panier_Produit (panier_id, produit_id) VALUES (?, ?)";
//                PreparedStatement produitStatement = cnx.prepareStatement(insertProduitQuery);
//                produitStatement.setInt(1, panier.getIdPanier());
//                produitStatement.setInt(2, produit.getIdProduit());
//
//                int lignesAffectées = produitStatement.executeUpdate();
//                if (lignesAffectées > 0) {
//                    // Mettez à jour la quantité et le prix total du panier
//                   // panier.setQuantite(panier.getQuantite() + 1);
//                  //  calculerEtMettreAJourPrixTotal(panier);
//                }
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private float calculerEtMettreAJourPrixTotal(Panier panier) {
//       try {
//        String selectAllPaniersQuery = "SELECT prixprod FROM Panier pr join Produit pn on (pr.idProd = pn.idProd) where etat = 1 and idPanier = "+panier.getIdPanier();
//        Statement statement = cnx.createStatement();
//        ResultSet resultSet = statement.executeQuery(selectAllPaniersQuery);
//        int  prixtot = 0;
//
//
//        while (resultSet.next()) {
//
//             int prix = resultSet.getInt("prixprod");
//                   prixtot+=prix;
//
//
//        }
//        return prixtot;
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération des paniers : " + ex.getMessage());
//    }
//
//       return -2.0f;
//    }
//
//
//
//
//
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public void supprimerProduitDuPanier(Panier panier, Produit produit) {
//        try {
//            // Mettre à jour la quantité du panier
//
//
//            // Mettre à jour le prix total du panier
//
//
//            // Supprimer le produit du panier en base de données
//            String deleteProduitQuery = "update  panier set etat = 0 where idpanier = "+panier.getIdPanier()+" and idprod = "+produit.getIdProduit();
//            PreparedStatement deleteProduitStatement = cnx.prepareStatement(deleteProduitQuery);
//
//            deleteProduitStatement.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//    @Override
//    public Panier getOne(Panier t) {
//      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    try {
//        String selectPanierQuery = "SELECT * FROM `Panier` WHERE `idPanier` = ?";
//        PreparedStatement panierStatement = cnx.prepareStatement(selectPanierQuery);
//        panierStatement.setInt(1, t.getIdPanier());
//
//        ResultSet resultSet = panierStatement.executeQuery();
//
//        if (resultSet.next()) {
//            int idPanier = resultSet.getInt("idPanier");
//            float prixTot = resultSet.getFloat("prixTot");
//            int etat = resultSet.getInt("etat");
//
//            // Créez un objet Panier avec les données récupérées
//            Panier panier = new Panier();
//            panier.setIdPanier(idPanier);
//            panier.setPrixTot(prixTot);
//            panier.setQantite(1);
//
//            // Maintenant, obtenez les produits associés à ce panier
//            List<Produit> produits = getProduitsForPanier(idPanier);
//            panier.setProduits(produits);
//
//            return panier;
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération du panier : " + ex.getMessage());
//    }
//
//    return null;
//    }
//
//    public Panier getPanierByUser(User user) {
//      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    try {
//        String selectPanierQuery = "SELECT * FROM Panier p join User u on (p.idpanier = u.idpanier) where idUser = ?  ";
//        PreparedStatement panierStatement = cnx.prepareStatement(selectPanierQuery);
//        panierStatement.setInt(1, user.getId());
//
//        ResultSet resultSet = panierStatement.executeQuery();
//
//        if (resultSet.next()) {
//            int idPanier = resultSet.getInt("idPanier");
//
//
//            // Créez un objet Panier avec les données récupérées
//            Panier panier = new Panier();
//            panier.setIdPanier(idPanier);
//            panier.setQantite(getCountProduit(panier));
//
//            // Maintenant, obtenez les produits associés à ce panier
//            panier.setPrixTot(calculerEtMettreAJourPrixTotal(panier));
//
//            return panier;
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération du panier : " + ex.getMessage());
//    }
//
//    return null;
//    }
//
//  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        public int getCountProduit(Panier p ) {
//      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    try {
//        String selectPanierQuery = "SELECT count(*) as count FROM Panier p where idpanier = ?  ";
//        PreparedStatement panierStatement = cnx.prepareStatement(selectPanierQuery);
//        panierStatement.setInt(1,p.getIdPanier());
//
//        ResultSet resultSet = panierStatement.executeQuery();
//
//        if (resultSet.next()) {
//            int count = resultSet.getInt("count");
//
//
//            return count;
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération du panier : " + ex.getMessage());
//    }
//
//    return -1;
//    }
//
//
//    @Override
//    public List<Panier> getAll(Panier t) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    List<Panier> paniers = new ArrayList<>();
//
//    try {
//        String selectAllPaniersQuery = "SELECT * FROM `Panier`";
//        Statement statement = cnx.createStatement();
//        ResultSet resultSet = statement.executeQuery(selectAllPaniersQuery);
//
//        while (resultSet.next()) {
//            int idPanier = resultSet.getInt("idPanier");
//            float prixTot = resultSet.getFloat("prixTot");
//            int etat = resultSet.getInt("etat");
//            int quantite = resultSet.getInt("quantite");
//
//            // Créez un objet Panier avec les données récupérées
//            Panier panier = new Panier();
//            panier.setIdPanier(idPanier);
//            panier.setPrixTot(prixTot);
//            panier.setQantite(quantite);
//
//            // Maintenant, obtenez les produits associés à ce panier
//            List<Produit> produits = getProduitsForPanier(idPanier);
//            panier.setProduits(produits);
//
//            paniers.add(panier);
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération des paniers : " + ex.getMessage());
//    }
//
//    return paniers;
//    }
//
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        public List<Produit> getAllProduitByPanier(Panier t) {
//
//            List<Produit> produits = new ArrayList<>();
//
// try {
//        String selectAllPaniersQuery = "SELECT * FROM Panier pr join Produit pn on (pr.idPanier = pn.idPanier) where pr.etat = 1 and pr.idPanier = ?";
//        PreparedStatement preparedStatement = cnx.prepareStatement(selectAllPaniersQuery);
//        preparedStatement.setInt(1, t.getIdPanier());
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            int idProduit = resultSet.getInt("idProduit");
//            String nomProduit = resultSet.getString("nomProd");
//            String descriptionProd =resultSet.getString("descriptionProd");
//             int prixProd = resultSet.getInt("prixProd");
//             float remise =resultSet.getFloat("remise")  ;
//             String imageProd =resultSet.getString("imageprod");
//            // Autres colonnes nécessaires pour créer l'objet Pro;duit
//
//            // Créez un objet Produit avec les données récupérées
//            Produit produit = new Produit(idProduit, nomProduit,descriptionProd,prixProd,remise,imageProd);
//            produits.add(produit);
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la récupération des produits : " + ex.getMessage());
//    }
//
//    return produits;
//}
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private List<Produit> getProduitsForPanier(int idPanier) {
// List<Produit> produits = new ArrayList<>();
//        try {
//            // Écrire la requête pour obtenir la liste des produits associés à un panier spécifique.
//            String selectProduitsQuery = "SELECT * FROM `Produit` WHERE `idPanier` = ?";
//            PreparedStatement produitsStatement = cnx.prepareStatement(selectProduitsQuery);
//            produitsStatement.setInt(1, idPanier);
//
//            ResultSet resultSet = produitsStatement.executeQuery();
//
//            while (resultSet.next()) {
//                int idProduit = resultSet.getInt("idProduit");
//                // Récupérez d'autres données pour chaque produit.
//                // Créez un objet Produit pour chaque enregistrement récupéré et ajoutez-le à la liste.
//                // Utilisez les données du résultat pour initialiser les propriétés de l'objet Produit.
//            }
//        } catch (SQLException ex) {
//            System.out.println("Erreur lors de la récupération des produits du panier : " + ex.getMessage());
//        }
//        return produits;    }
//
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//
//    @Override
//    public void ajouter(Panier t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void modifier(Panier t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void supprimer(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//
