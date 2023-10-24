package entities;

/**
 * Cette énumération représente les différents types de réclamations possibles.
 */
public enum TypeRec {
    INCIDENT("Incident"),
    RECLAMATION("Réclamation"),
    AVIS("Avis");

    private final String libelle;

    TypeRec(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
