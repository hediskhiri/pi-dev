/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author med amine nsir
 */
public interface IServiceReponse<Reponse> {
    public void ajouter(Reponse rec) throws SQLException ;

    public void modifier(Reponse rec) throws SQLException ;

    public void supprimer(int i) throws SQLException ;
    public List<Reponse> recuperer() throws SQLException ;
    
}
    

