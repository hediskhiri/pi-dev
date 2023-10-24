package tn.edu.esprit.entities;

public class Panier {

    private int id;
    private int idUser;
    private boolean etat;

    public Panier(int id, int userId, boolean etat) {
        this.id = id;
        this.idUser = userId;
        this.etat = etat;
    }

    public Panier(int userId, boolean etat) {
        this.idUser = userId;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return idUser;
    }

    public void setUserId(int userId) {
        this.idUser = userId;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
