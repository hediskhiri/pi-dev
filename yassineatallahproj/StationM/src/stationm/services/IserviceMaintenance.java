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
 * @param <Maintenance>
 */
public interface IserviceMaintenance<Maintenance> {
    public void ajouter(Maintenance m)throws SQLException;
    public void modifier(Maintenance m)throws SQLException;
    public void supprimer(int id_m)throws SQLException;
   // public List<Maintenance> getAll(Maintenance m)throws SQLException;
}
