/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationm.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 21655
 * @param <Velo>
 */
public interface IserviceVelo<Velo> {
    public void ajouter(Velo v);
    public void modifier(Velo v)throws SQLException;
    public void supprimer(int id_v)throws SQLException;
    public List<Velo> getAll(Velo v)throws SQLException;

        
   
}
