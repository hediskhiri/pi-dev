package projetjava;
import entities.User;
import entities.Reclamation;
import entities.Reponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import services.ServiceReclamation;
import services.ServiceReponse;
import services.ServiceUser;
import tools.DataSource;
import entities.TypeRec;
import entities.Etat;
public class ProjetJava {

    public static void main(String[] args) throws SQLException {
        String str = "2018-03-21";
        Date date = Date.valueOf(str);
        Connection con;
        con = DataSource.getinstance().getCon();

        ServiceUser SU = ServiceUser.getInstance();
        User U = new User();
        SU.ajouter(U);

        TypeRec typeRec = TypeRec.AVIS; // Spécifiez le type de réclamation
        Etat etat = Etat.TRAITE; // Spécifiez l'état de la réclamation

        ServiceReclamation sr = ServiceReclamation.getInstance();
        Reclamation R = new Reclamation(8, "rec", "aaKKK", date, U, typeRec, etat);
        sr.ajouter(R);


        sr.ajouter(R);

        // Pour modifier une réclamation, vous pouvez utiliser la méthode de modification (à définir dans le service).
        // sr.modifier(R);

        // Pour supprimer une réclamation, vous pouvez utiliser la méthode de suppression (à définir dans le service).
        // sr.supprimer(2);

        // Pour obtenir toutes les réclamations, utilisez la méthode getAll (à définir dans le service).
        /*List<Reclamation> reclamation = sr.getAll();
        for (Reclamation rec : reclamation) {
            System.out.println(rec);
        }*/

        // ServiceReponse sre = ServiceReponse.getInstance();
        // Reponse RE = new Reponse(7, R, "aKK55");
        // sre.ajouter(RE);

        // Pour modifier une réponse, utilisez la méthode de modification (à définir dans le service).
        // sre.modifier(RE);

        // Pour récupérer toutes les réponses, utilisez la méthode recuperer (à définir dans le service).
        /*List<Reponse> reponse = sre.recuperer();
        for (Reponse rep : reponse) {
            System.out.println(rep);
        }*/
    }
}

            
        
        
        
    
 
