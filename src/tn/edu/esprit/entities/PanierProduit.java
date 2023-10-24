package tn.edu.esprit.entities;


public class PanierProduit {

    private Produit produit;
    private Panier panier;
    private int qte;

    public PanierProduit(Produit produit, Panier panier, int qte) {
        this.produit = produit;
        this.panier = panier;
        this.qte = qte;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}