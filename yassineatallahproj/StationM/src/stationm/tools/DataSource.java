/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package stationm.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;*/

/**
 *
 * @author abdelazizmezri
 */

package stationm.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;
    
    private String url = "jdbc:mysql://localhost:3306/stationm";
    private String user = "root";
    private String password = "";
    
    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
    
    public Connection getConnection() {
        try {
            if (cnx == null || cnx.isClosed()) {
                // Re-establish the connection if it's null or closed
                cnx = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cnx;
    }
}

/*public class DataSource {
    private Connection cnx;
    private static DataSource instance;
    
    private String url = "jdbc:mysql://localhost:3306/stationm";
    private String user = "root";
    private String password = "";
    
    private DataSource(){
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return this.cnx;
    }
}*/