/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

/**
 * @author Lenovo
 */
public class Produit {
    private int idProduit;
    private String nomProd;
    private String descriptionProd;
    private int prixProd;
    private float remise;
    private String imageProd;
    private Categorie Categories; // Clé étrangère vers la table Categorie

    public Produit() {
    }

    public Produit(String nomProd, String descriptionProd, int prixProd, float remise) {
        this.nomProd = nomProd;
        this.descriptionProd = descriptionProd;
        this.prixProd = prixProd;
        this.remise = remise;
    }

    public Produit(String nomProd, String descriptionProd, int prixProd, float remise, String imageProd) {
        this.nomProd = nomProd;
        this.descriptionProd = descriptionProd;
        this.prixProd = prixProd;
        this.remise = remise;
        this.imageProd = imageProd;
    }

    public Produit(int idProduit, String nomProd, String descriptionProd, int prixProd, float remise, String imageProd, Categorie Categories) {
        this.idProduit = idProduit;
        this.nomProd = nomProd;
        this.descriptionProd = descriptionProd;
        this.prixProd = prixProd;
        this.remise = remise;
        this.imageProd = imageProd;
        this.Categories = Categories;
    }

    public Produit(int idProduit, String nomProd, String descriptionProd, int prixProd, float remise, String imageProd) {
        this.idProduit = idProduit;
        this.nomProd = nomProd;
        this.descriptionProd = descriptionProd;
        this.prixProd = prixProd;
        this.remise = remise;
        this.imageProd = imageProd;
    }


    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescriptionProd() {
        return descriptionProd;
    }

    public void setDescriptionProd(String descriptionProd) {
        this.descriptionProd = descriptionProd;
    }

    public int getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(int prixProd) {
        this.prixProd = prixProd;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    public String getImageProd() {
        return imageProd;
    }

    public void setImageProd(String imageProd) {
        this.imageProd = imageProd;
    }

    public Categorie getCategories() {
        return Categories;
    }

    public void setCategories(Categorie Categories) {
        this.Categories = Categories;
    }

    @Override
    public String toString() {
        return nomProd;
    }
}
