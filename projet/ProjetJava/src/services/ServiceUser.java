/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author med amine nsir
 */
public class ServiceUser implements IserviceUser<User> {
    private static ServiceUser instance;

    PreparedStatement preparedstatement;
    Connection con;
    Statement ste;
    public ServiceUser(){
    con =tools.DataSource.getinstance().getCon();
    }
    
    public static ServiceUser getInstance(){
    if (instance == null){
        instance = new ServiceUser();
    }     
    return instance;

    }
    @Override
    public void ajouter(User u) throws SQLException {
        try {
            String req = "INSERT INTO user(iduser)values(?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,u.getIduser()); 
            pre.executeUpdate();
            
            
            
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
    }
    
}
