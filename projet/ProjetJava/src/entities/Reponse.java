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
public class Reponse {
    private int idrep; 
    private Reclamation r; 
    private String contenu;

    

    public Reponse(String contenu) {
        this.contenu = contenu;
    }

    public Reponse() {
        
    }

   

    

    public int getIdrep() {
    return idrep;
}

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public Reclamation getR() {
        return r;
    }

    public void setR(Reclamation r) {
        this.r = r;
    }

   

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Reponse(int idrep, Reclamation r, String contenu) {
        this.idrep = idrep;
        this.r = r;
        this.contenu = contenu;
    }

    public Reponse(Reclamation r, String contenu) {
        this.r = r;
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Reponse{" + "idrep=" + idrep + ", r=" + r + ", contenu=" + contenu + '}';
    }

    public void setIdrec(int idReclamation) {
        
    }

   
    

}
