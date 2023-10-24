/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import tn.edu.esprit.entities.Categorie;
import tn.edu.esprit.tools.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 */
public class ServiceCategorie implements IService<Categorie> {
    Connection cnx;

    public ServiceCategorie() {
        this.cnx = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Categorie c) {
        try {
            String req = "INSERT INTO `Categorie` (`idCategorie`, `nomCategorie`) VALUES ('" + c.getIdCategorie() + "','" + c.getNomCategorie() + "')";

            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Categorie c) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        try {
            String req = "UPDATE `Categorie` SET `nomCategorie` = '" + c.getNomCategorie() + "' WHERE `idCategorie` = " + c.getIdCategorie();

            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void supprimer(int id) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        try {
            String req = "DELETE FROM `Categorie` WHERE `idCategorie` = " + id;

            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Categorie getOne(Categorie c) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            String req = "SELECT * FROM `Categorie` WHERE `idCategorie` = " + c.getIdCategorie();

            Statement stm = cnx.createStatement();
            ResultSet rs;
            rs = stm.executeQuery(req);

            if (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(rs.getInt("idCategorie"));
                categorie.setNomCategorie(rs.getString("nomCategorie"));
                return categorie;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    public List<Categorie> getAll() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<Categorie> categories = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Categorie`";

            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(rs.getInt("idCategorie"));
                categorie.setNomCategorie(rs.getString("nomCategorie"));
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;


    }

    @Override
    public List<Categorie> getAll(Categorie t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
