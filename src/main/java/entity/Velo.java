/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author houss
 */
public class Velo {
    private int id; 
    private String modele, etat,image,station;
    private float prix;

    public Velo(String modele, String etat, String station, float prix) {
        this.modele = modele;
        this.etat = etat;
        this.station = station;
        this.prix = prix;
    }

    public Velo(int id, String modele, String etat, String station, float prix) {
        this.id = id;
        this.modele = modele;
        this.etat = etat;
        this.station = station;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Velo(int id, String modele, String etat) {
        this.id = id;
        this.modele = modele;
        this.etat = etat;
    }

    public Velo(String modele, String etat) {
        this.modele = modele;
        this.etat = etat;
    }

    public Velo(int id, String modele, String etat, String image, String station, float prix) {
        this.id = id;
        this.modele = modele;
        this.etat = etat;
        this.image = image;
        this.station = station;
        this.prix = prix;
    }

    public Velo() {
    }

    @Override
    public String toString() {
        return "Velo id:"+this.id +"  "+this.modele + " "+ this.etat;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
