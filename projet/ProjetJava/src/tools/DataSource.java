/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author med amine nsir
 */
public class DataSource {

    public static Object getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     //
    String url = "jdbc:mysql://localhost:3306/reclamation";
    String user = "root";
    String pwd = "";
    
    
    Connection con;
    
    //3 
    static DataSource instance;
     //1 rendre le constructeur prive
    private DataSource() {
        
        try {
            con = DriverManager.getConnection(url, user, pwd);
            
            System.out.println("connexion etablie");
        } catch (SQLException ex) {
            System.out.println("Probeleme de connexion");
        }
        }
    
    // 2 etape: de creer une methode static pour utiliser le const 
    public static DataSource getinstance(){
        if(instance == null){
            instance =  new DataSource();
        }
        return instance;
        
    }

    public Connection getCon() {
        return con;
    }
    
}
