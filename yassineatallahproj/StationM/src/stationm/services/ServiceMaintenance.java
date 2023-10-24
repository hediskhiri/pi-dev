/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationm.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import stationm.entities.Maintenance;
import stationm.entities.Velo;
import stationm.tools.DataSource;

/**
 *
 * @author 21655
 */
public class ServiceMaintenance implements IserviceMaintenance<Maintenance> {
      private static ServiceMaintenance instance;
    Connection cnx ;
    
    public ServiceMaintenance(){
    this.cnx= DataSource.getInstance().getConnection();
    
    
}
     public static ServiceMaintenance getInstance(){
        if (instance == null){
            instance = new ServiceMaintenance();            
        }
        return instance;
    }

    @Override
    public void ajouter(Maintenance m) throws SQLException {
         try {
            String req = "INSERT INTO Maintenance(id_v,start_time,end_time)values(?,?,?)";
            PreparedStatement pre = cnx.prepareStatement(req);
           
            pre.setInt(1, m.getVelol().getId_v());
            Date startDate = new Date(m.getStart_time().getTime());
            Date endDate = new Date(m.getEnd_time().getTime());
             
            pre.setDate(2, startDate);
            pre.setDate(3, endDate);
            
           
            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
    }

    @Override
    public void modifier(Maintenance m) throws SQLException {
         String req = "update maintenance set  start_time = ?, end_time=? where id_m=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        Date startDate = new Date(m.getStart_time().getTime());
        Date endDate = new Date(m.getEnd_time().getTime());

        ps.setDate(2, startDate);
        ps.setDate(3, endDate);
        
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id_m) throws SQLException {
        String req = "DELETE FROM maintenance WHERE id_m = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id_m);
    
    ps.executeUpdate();
    }

    /*@Override
    public List<Maintenance> getAll(Maintenance m) throws SQLException {
        List<Maintenance> maintenances = new ArrayList<>();
         
        try {
            String req = "SELECT m.*, v.id_v as velo_id, v.model as velo_model, v.type as velo_type, v.status as velo_status FROM Maintenance m " + "JOIN Velo v ON m.velo_id=v.id_v";

            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
               
                Maintenance maintenance = new Maintenance();
                maintenance.setId_m(rs.getInt("id_m"));
                maintenance.setId_p(rs.getInt("id_p"));
               
                maintenance.setStart_time(rs.getDate("start_time"));
                maintenance.setEnd_time(rs.getDate("end_time"));
                /*Velo velo = new Velo();
                velo.setId_v(rs.getInt("velo_id"));
                velo.setModel(rs.getString("velo_model"));
                velo.setType(rs.getString("velo_type"));
                velo.setStatus(rs.getString("velo_status"));
                maintenance.setVelo(velo);
                maintenances.add(maintenance);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return maintenances;
    }*/
    }
    

