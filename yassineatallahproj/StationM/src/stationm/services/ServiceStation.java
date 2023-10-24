/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import stationm.entities.Station;
import stationm.entities.Velo;
import stationm.tools.DataSource;

/**
 *
 * @author 21655
 */
public class ServiceStation implements IserviceStation<Station> {
    private static ServiceStation instance;
    
    Connection cnx ;
    
    public ServiceStation(){
    this.cnx= DataSource.getInstance().getConnection();
}
     public static ServiceStation getInstance(){
        if (instance == null){
            instance = new ServiceStation();            
        }
        return instance;
    }
     
   

    @Override
    public void ajouter(Station s) {
       try {
            String req = "INSERT INTO station(nom_s,emplacement_s)values(?,?)";
            PreparedStatement pre = cnx.prepareStatement(req);
            
            pre.setString(1,s.getNom_s());
            pre.setString(2,s.getEmplacement_s() );
           
            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
    }

    @Override
    public void modifier(Station s) throws SQLException {
        String req = "update station set nom_s = ?, emplacement_s = ? where id_s=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1,s.getNom_s());
        ps.setString(2, s.getEmplacement_s());
        ps.setInt(3, s.getId_s());
        
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id_s) throws SQLException {
         String req = "DELETE FROM station WHERE id_s = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id_s);
    
    ps.executeUpdate();
    }
    
   

    @Override
    public List<Station> getAll(Station s) throws SQLException {
         List<Station> stations = new ArrayList<>();
         
        try {
            String req = "SELECT * FROM Station";

            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
               
                Station station = new Station();
                station.setId_s(rs.getInt("id_s"));
                station.setNom_s(rs.getString("nom_s"));
                station.setEmplacement_s(rs.getString("emplacement_s"));
                
                stations.add(station);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return stations;
    }
    /*private List<Velo> getVelosForStation(int stationId) throws SQLException {
    List<Velo> velos = new ArrayList<>();
    
    try {
        String req = "SELECT * FROM Velo WHERE id_station = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, stationId);
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Velo velo = new Velo();
   
            velo.setId_v(rs.getInt("id_v"));
            velo.setModel(rs.getString("model"));
            velo.setType(rs.getString("type"));
            velo.setStatus(rs.getString("status"));
        
            
            velos.add(velo);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return velos;
}*/
    
    /*@Override
    public Station rechercherStationParId(int id_s) {
        
        PreparedStatement stmt ;
        ResultSet rs ;
        Station station = null;

        try {
            
            String query = "SELECT * FROM stations WHERE id_s = ?";
            stmt = cnx.prepareStatement(query);
            stmt.setInt(1, id_s);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Créez un objet Station à partir des données de la base de données
                station = new Station();
                station.setId_s(rs.getInt("id_s"));
                station.setNom_s(rs.getString("nom_s"));
                station.setEmplacement_s(rs.getString("emplacement_s"));
                station.setVelos(null);
                
              
                // Vous devrez peut-être récupérer la liste de vélos ici, en fonction de votre structure de données
            }

        } catch (SQLException e) {
        } finally {
            // Fermez les ressources (ResultSet, PreparedStatement, Connection)
            // Gérez les exceptions appropriées ici
        }
        
        return station;
    }
    
    @Override
     public Velo rechercherVeloParId(int id_v) {
      
        PreparedStatement stmt ;
        ResultSet rs;
        Velo velo = null;

        try {
           
            String query = "SELECT * FROM velos WHERE id_v = ?";
            stmt = cnx.prepareStatement(query);
            stmt.setInt(1, id_v);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Créez un objet Velo à partir des données de la base de données
                velo = new Velo();
                velo.setId_v(rs.getInt("id_v"));
                velo.setModel(rs.getString("model"));
                velo.setType(rs.getString("type"));
                velo.setStatus(rs.getString("status"));
                velo.setId_s(rs.getInt("id_s"));
            }

        } catch (SQLException e) {
        } finally {
            // Fermez les ressources (ResultSet, PreparedStatement, Connection)
            // Gérez les exceptions appropriées ici
        }

        return velo;
    }
    

    
    @Override
     public void ajouterVeloAStation(int id_s, int id_v) {
       
    Station station = rechercherStationParId(id_s); 

    
    Velo velo = rechercherVeloParId(id_v); 

    
    if (station != null && velo != null) {
        List<Velo> velos = station.getVelos();

        if (velos == null) {
            velos = new ArrayList<>();
            station.setVelos(velos);
        }

        velos.add(velo);
    }
    }*/
    
   
}

    
    

