/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.tools.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 */
public class ServiceProduit implements IService<Produit> {
    Connection cnx;

    public ServiceProduit() {
        this.cnx = DatabaseConnection.getInstance().getConnection();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void ajouter(Produit p) {
        try {
            String req = "INSERT INTO `Produit` (`nomProd`, `descriptionProd`, `prixProd`, `remise`, `idCategorie`, `imageProd`) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, p.getNomProd());
            preparedStatement.setString(2, p.getDescriptionProd());
            preparedStatement.setInt(3, p.getPrixProd());
            preparedStatement.setFloat(4, p.getRemise());
            preparedStatement.setInt(5, p.getCategories().getIdCategorie());
            preparedStatement.setString(6, p.getImageProd());

            int etat = preparedStatement.executeUpdate();

            System.out.println("ajout avec sucess" + etat);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void supprimer(int id) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            String deleteQuery = "DELETE FROM `Produit` WHERE `idProd` = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun enregistrement trouvé avec l'ID " + id + ". La suppression a échoué.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du Produit : " + ex.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void modifier(Produit p) {
        try {
            String updateQuery = "UPDATE `Produit` SET `nomProd` = ?, `descriptionProd` = ?, `prixProd` = ?, `remise` = ?, `idCategorie` = ?, `imageProd` = ? WHERE `idProd` = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(updateQuery);
            preparedStatement.setString(1, p.getNomProd());
            preparedStatement.setString(2, p.getDescriptionProd());
            preparedStatement.setFloat(3, p.getPrixProd());
            preparedStatement.setFloat(4, p.getRemise());
            preparedStatement.setInt(5, p.getCategories().getIdCategorie());
            preparedStatement.setString(6, p.getImageProd());
            preparedStatement.setInt(7, p.getIdProduit());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit avec l'ID " + p.getIdProduit() + " a été modifié avec succès.");
            } else {
                System.out.println("Aucun enregistrement trouvé avec l'ID " + p.getIdProduit() + ". La modification a échoué.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du Produit : " + ex.getMessage());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Produit> afficher() {
        List<Produit> produits = new ArrayList<>();

        try {
            String selectAllProduitsQuery = "SELECT * FROM `Produit` join Categorie on Produit.idCategorie = Categorie.idCategorie";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllProduitsQuery);

            while (resultSet.next()) {
                int idProduit = resultSet.getInt("idProd");
                String nomProd = resultSet.getString("nomProd");
                String descriptionProd = resultSet.getString("descriptionProd");
                int prixProd = resultSet.getInt("prixProd");
                float remise = resultSet.getFloat("remise");
                //
                String imageProd = resultSet.getString("imageProd");
                String nomCategorie = resultSet.getString("nomCategorie");
                // Create a Produit object with the retrieved data
                Produit produit = new Produit(idProduit, nomProd, descriptionProd, prixProd, remise, imageProd, new Categorie(nomCategorie));
                produits.add(produit);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des produits : " + ex.getMessage());
        }

        return produits;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Produit getOne(int i) {
        List<Produit> produits = afficher(); // Assuming you have a method to get all products
        if (i >= 0 && i < produits.size()) {
            return produits.get(i);
        }
        return null;
    }

    public Iterable<Produit> getAll() {
        return afficher();
    }

    @Override
    public Produit getOne(Produit t) {
        List<Produit> produits = afficher();
        for (Produit produit : produits) {
            if (produit.equals(t)) {
                return produit;
            }
        }
        return null;
    }

    @Override
    public List<Produit> getAll(Produit t) {
        List<Produit> produits = afficher();
        List<Produit> matchingProducts = new ArrayList<>();
        for (Produit produit : produits) {
            if (produit.equals(t)) {
                matchingProducts.add(produit);
            }
        }
        return matchingProducts;
    }

}





