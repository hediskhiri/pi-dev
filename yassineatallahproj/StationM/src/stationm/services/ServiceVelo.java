/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import stationm.entities.Velo;
import stationm.tools.DataSource;


/**
 *
 * @author 21655
 */
public class ServiceVelo implements IserviceVelo<Velo>  {
    
     Connection cnx ;
    
    public ServiceVelo(){
    this.cnx= DataSource.getInstance().getConnection();
}

    
   
    @Override
    public void ajouter(Velo v) {
      try {
            String req = "INSERT INTO velo(model,type,status,id_s)values(?,?,?,?)";
            PreparedStatement pre = cnx.prepareStatement(req);
            
            pre.setString(1,v.getModel());
            pre.setString(2,v.getType() );
            pre.setString(3,v.getStatus());
            pre.setInt(4,v.getSta().getId_s());
            
            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
    }

    @Override
    public void modifier(Velo v) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(int id_v) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Velo> getAll(Velo v) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
