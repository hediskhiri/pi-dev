package entities;

/**
 * Cette énumération représente les différents états d'une réclamation.
 */
public enum Etat {
    TRAITE("Traité"),
    NON_TRAITE("Non traité");

    private final String libelle;

    Etat(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
