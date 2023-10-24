package tn.edu.esprit.services;

import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.entities.Panier;
import tn.edu.esprit.entities.PanierProduit;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.tools.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierProduitService {

    private static PanierProduitService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public PanierProduitService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static PanierProduitService getInstance() {
        if (instance == null) {
            instance = new PanierProduitService();
        }
        return instance;
    }

    public List<PanierProduit> getAllByPanier(int idPanier) {
        List<PanierProduit> listPanierProduit = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `panier_produit` AS x " +
                            "RIGHT JOIN `produit` AS y ON x.produit_id = y.idProd " +
                            "RIGHT JOIN `panier` AS z ON x.panier_id = z.idPanier " +
                            "WHERE x.produit_id = y.idProd " +
                            "AND x.panier_id = idPanier"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listPanierProduit.add(
                        new PanierProduit(
                                new Produit(
                                        resultSet.getInt("y.idProd"),
                                        resultSet.getString("y.nomProd"),
                                        resultSet.getString("y.descriptionProd"),
                                        resultSet.getInt("y.prixProd"),
                                        resultSet.getInt("y.remise"),
                                        resultSet.getString("y.imageProd")
                                ),
                                new Panier(
                                        resultSet.getInt("z.idPanier"),
                                        resultSet.getInt("z.userId"),
                                        resultSet.getBoolean("z.etat")
                                ),
                                resultSet.getInt("qte")
                        )
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) panierProduit : " + exception.getMessage());
        }
        return listPanierProduit;
    }

    public Panier getPanier(int id) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `panier` WHERE idPanier = " + id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Panier(
                        resultSet.getInt("idPanier"),
                        resultSet.getInt("userId"),
                        resultSet.getBoolean("etat")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) getAllProduit : " + exception.getMessage());
        }
        return null;
    }

    public List<Produit> getAllProduit() {
        List<Produit> listProduit = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `produit` " +
                    "LEFT JOIN categorie c on produit.idCategorie = c.idCategorie");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listProduit.add(
                        new Produit(
                                resultSet.getInt("idProd"),
                                resultSet.getString("nomProd"),
                                resultSet.getString("descriptionProd"),
                                resultSet.getInt("prixProd"),
                                resultSet.getInt("remise"),
                                resultSet.getString("imageProd"),
                                new Categorie(
                                        resultSet.getInt("c.idCategorie"),
                                        resultSet.getString("c.nomCategorie")
                                )
                        )
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) getAllProduit : " + exception.getMessage());
        }
        return listProduit;
    }

    public boolean checkExist(PanierProduit panierProduit) {
        String selectQuery = "SELECT COUNT(*) FROM panier_produit WHERE produit_id = ? AND panier_id = ?";

        try {
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, panierProduit.getProduit().getIdProduit());
            selectStatement.setInt(2, panierProduit.getPanier().getId());

            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();

            if (count > 0) {
                System.out.println("PanierProduit already exists");
                return true;
            } else {
                System.out.println("PanierProduit does not exist");
                return false;
            }
        } catch (SQLException exception) {
            System.out.println("Error (checkExist) panierProduit: " + exception.getMessage());
        }

        return false;
    }

    public boolean add(PanierProduit panierProduit) {
        if (checkExist(panierProduit)) {
            return false; // The record already exists, so we won't add it again.
        }

        String request = "INSERT INTO `panier_produit`(`produit_id`, `panier_id`, `qte`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panierProduit.getProduit().getIdProduit());
            preparedStatement.setInt(2, panierProduit.getPanier().getId());
            preparedStatement.setInt(3, panierProduit.getQte());

            preparedStatement.executeUpdate();
            System.out.println("PanierProduit added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) panierProduit : " + exception.getMessage());
        }
        return false;
    }

    public boolean updateQuantity(PanierProduit panierProduit) {
        String request = "UPDATE `panier_produit` SET `qte`=? WHERE panier_id = ? AND produit_id = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panierProduit.getQte());
            preparedStatement.setInt(2, panierProduit.getPanier().getId());
            preparedStatement.setInt(3, panierProduit.getProduit().getIdProduit());

            preparedStatement.executeUpdate();
            System.out.println("PanierProduit updated");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error updating panierProduit: " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(PanierProduit panierProduit) {
        String request = "DELETE FROM `panier_produit` WHERE panier_id = ? AND produit_id = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panierProduit.getPanier().getId());
            preparedStatement.setInt(2, panierProduit.getProduit().getIdProduit());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("PanierProduit deleted");
                return true;
            } else {
                System.out.println("No PanierProduit found for deletion.");
            }
        } catch (SQLException exception) {
            System.out.println("Error deleting panierProduit: " + exception.getMessage());
        }
        return false;
    }
}
