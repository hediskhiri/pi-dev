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
 * @param <Station>
 */
public interface IserviceStation<Station> {
    public void ajouter(Station s);
    public void modifier(Station s)throws SQLException;
    public void supprimer(int id_s)throws SQLException;
    public List<Station> getAll(Station s)throws SQLException;
    //public void ajouterVeloAStation(int id_s, int id_v);
    //public Station rechercherStationParId(int id_s);
    // public Velo rechercherVeloParId(int id_v);
}
