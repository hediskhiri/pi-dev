/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.SQLException;

/**
 *
 * @author med amine nsir
 */
public interface IserviceUser<User> {
    public void ajouter(User u) throws SQLException;
}
