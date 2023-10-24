/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entities.User;
import entities.Reclamation;
import entities.Reponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author med amine nsir
 */
public class ServiceReponse implements IServiceReponse<Reponse>{
private static ServiceReponse instance;
PreparedStatement preparedstatement;
Connection con;
 Statement ste;

public ServiceReponse(){
    con =tools.DataSource.getinstance().getCon();
    
    
}
public static ServiceReponse getInstance(){
    if (instance == null){
        instance = new ServiceReponse();
    }     
    return instance;
}
   

    @Override
    public void ajouter(Reponse rec) throws SQLException {
         try {
            String req = "INSERT INTO reponse(idrep,id,contenu)values(?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,rec.getIdrep() );
            pre.setInt(2,rec.getR().getId());
            pre.setString(3,rec.getContenu() );
            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
    }

    @Override
    public void modifier(Reponse rec) throws SQLException {
         String req = "update reponse set  idrec=? ,contenu=?  where idrep=?";
        PreparedStatement ps = con.prepareStatement(req);

        ps.setInt(1, rec.getR().getId());
        ps.setString(2,rec.getContenu());
        ps.setInt(3, rec.getIdrep());
        ps.executeUpdate();

    
    }

    @Override
    public void supprimer(int idrep) throws SQLException {
        try{
        String req = "DELETE FROM reponse WHERE idrep = ?";
         PreparedStatement ps = con.prepareStatement(req);
         ps.setInt(1, idrep);
    
          ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    
    

    @Override
    public List<Reponse> recuperer() throws SQLException {
    List<Reponse> listReponse = new ArrayList<>();
    String req = "SELECT rep.idrep, rep.contenu, rec.id AS reclamation_id "
            + "FROM reponse rep "
            + "JOIN reclamation rec ON rec.id = rep.idrec ";
    
    try {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            int idrep = rs.getInt("idrep");
            String contenu = rs.getString("contenu");
            int reclamation_id = rs.getInt("reclamation_id");

            // Créez des objets Reclamation et Reponse en utilisant les colonnes sélectionnées
            Reclamation reclamation = new Reclamation(reclamation_id);
            Reponse reponse = new Reponse(idrep, reclamation, contenu);

            listReponse.add(reponse);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return listReponse;
}
}
        
    
        /*List<Reponse> reponse = new ArrayList<>();
        String req = "select * from reponse";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
        Reponse rep = new Reponse();
        rep.setIdrep(rs.getInt("idrep"));
        rep.setContenu(rs.getString("contenu"));
        rep.setR();
        

            reponse.add(rep);
        }
        return reponse;*/
    
    
    
