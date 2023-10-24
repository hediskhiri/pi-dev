package entities;

import java.sql.Date;
import entities.Etat;
import entities.TypeRec;


public class Reclamation {
    private int id;
    private String titre;
    private String description;
    private Date date;
    private int iduser;
    private TypeRec typeRec;
    private Etat etat;

    public Reclamation(int id, String titre, String description, Date date, User u, TypeRec typeRec, Etat etat) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.iduser = iduser;
        this.typeRec = typeRec;
        this.etat = etat;
    }

    public Reclamation(int aInt, String string, String string0, Date date, User user) {
       
    }

    public Reclamation() {
        
    }

    public Reclamation(int reclamation_id) {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

   

    public TypeRec getTypeRec() {
        return typeRec;
    }

    public void setTypeRec(TypeRec typeRec) {
        this.typeRec = typeRec;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", date=" + date + ", iduser=" + iduser + ", typeRec=" + typeRec + ", etat=" + etat + '}';
    }

    

    
}
