package tn.edu.esprit.entities;


import java.time.LocalDate;

public class Commande {

    private int id;
    private int montant;
    private LocalDate date;
    private Panier panier;

    public Commande(int id) {
        this.id = id;
    }

    public Commande(int id, int montant, LocalDate date, Panier panier) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.panier = panier;
    }

    public Commande(int montant, LocalDate date, Panier panier) {
        this.montant = montant;
        this.date = date;
        this.panier = panier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

}