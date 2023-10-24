/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mybike;

import entity.Velo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author houss
 */
public class ServiceVelo implements IServiceVelo<Velo> {
    private Connection con;
    private Statement ste;
    private PreparedStatement pre;
    private ResultSet result;


    public ServiceVelo() {
        con = MyDB.getinstance().getCon();    }


    @Override
    public void ajouter(Velo t) {
        try {

            String req = "INSERT INTO velo (modele,etat,prix,image,station)values(?,?,?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1,t.getModele() );
            pre.setString(2,t.getEtat());
            pre.setFloat(3,(t.getPrix()));
            pre.setString(4,t.getImage());
            pre.setString(5,t.getStation());

            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
       
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM velo WHERE id = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du vélo : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(Velo t) {
        try {
            String req = "UPDATE velo SET modele = ?, etat = ?, prix = ? , image= ?, station= ?  WHERE id = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getModele());
            pre.setString(2, t.getEtat());
            pre.setFloat(3, t.getPrix());
            pre.setString(4,t.getImage());
            pre.setString(5,t.getStation());
            pre.setInt(6, t.getId());


            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du vélo : " + ex.getMessage());
        }
    }

    @Override
    public List<Velo> afficher() {
        List<Velo> velos = new ArrayList<>();
        String sql ="select * from velo";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                Velo v = new Velo(rs.getInt("id"),
                        rs.getString("modele"), rs.getString("etat"), rs.getString("image"),  rs.getString("station"), rs.getFloat("prix"));
                velos.add(v);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return velos;
    }

    public int getNombreVeloDisponibles() {
        try {
            String req = "SELECT COUNT(*) FROM velo WHERE etat = 'disponible'";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre de vélos disponibles : " + ex.getMessage());
        }
        return 0; // En cas d'erreur ou si aucun vélo n'est disponible.
    }
    public int getNombreTotalVelos() {
        int nombreTotalVelos = 0; // Initialisez la variable à 0 par défaut.

        try {
            String req = "SELECT COUNT(*) FROM velo";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                nombreTotalVelos = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre total de vélos : " + ex.getMessage());
        }

        return nombreTotalVelos;
    }
    public int getNombreVeloElectriques() {
        int nombreVeloElectriques = 0;

        try {
            String req = "SELECT COUNT(*) FROM velo WHERE modele = 'electrique'";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                nombreVeloElectriques = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors du calcul du nombre de vélos électriques : " + ex.getMessage());
        }

        return nombreVeloElectriques;
    }

}
