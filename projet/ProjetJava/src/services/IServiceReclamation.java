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
public interface IServiceReclamation<Reclamation> {
    public void ajouter(Reclamation r) throws SQLException;
    public void modifier(Reclamation r) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public Reclamation getOne(Reclamation r) throws SQLException;
    public List<Reclamation> getAll() throws SQLException;
    
}
