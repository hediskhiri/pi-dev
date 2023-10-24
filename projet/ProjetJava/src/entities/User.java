/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author med amine nsir
 */
public class User {
    private int iduser;
    private String email;

    public User() {
    }

    

   

    public User(int iduser, String email) {
        this.iduser = iduser;
        this.email = email;
    }

    public User(int i) {
       
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "iduser=" + iduser + ", email=" + email + '}';
    }

    
    

    
}
