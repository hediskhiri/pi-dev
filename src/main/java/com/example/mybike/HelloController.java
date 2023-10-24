package com.example.mybike;
import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



import com.twilio.Twilio;
//import com.twilio.rest.lookups.v1.PhoneNumber;
import entity.Location;
import entity.bike;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button confirmer_location;

    @FXML
    private Button confirmer_location1;

    @FXML
    private Button confirmer_location11;

    @FXML
    private Label description_label;

    @FXML
    private DatePicker end_date;

    @FXML
    private DatePicker end_date1;

    @FXML
    private DatePicker end_date11;

    @FXML
    private Label modele_label;

    @FXML
    private Label modele_label1;

    @FXML
    private Label modele_label11;

    @FXML
    private AnchorPane page1;

    @FXML
    private AnchorPane page2;

    @FXML
    private AnchorPane page21;

    @FXML
    private AnchorPane page211;
    @FXML
    private Button annuler_reservation;

    @FXML
    private Label prix1;

    @FXML
    private Label prix2;

    @FXML
    private Label prix3;

    @FXML
    private Button reserver1;

    @FXML
    private Button reserver2;

    @FXML
    private Button reserver3;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker start_date1;

    @FXML
    private DatePicker start_date11;

    @FXML
    private Label station_label;

    @FXML
    private Label station_label1;

    @FXML
    private Label station_label11;

    @FXML
    private ImageView velo_bleu;

    @FXML
    private ImageView velo_jaune;

    @FXML
    private ImageView velo_noir;

    @FXML
    private ImageView velo_reserve;

    @FXML
    private ImageView velo_reserve1;

    @FXML
    private ImageView velo_reserve11;


    private Connection con;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    public void changepages(){
        if(reserver1.isFocused()){
            page1.setVisible(false);
            page2.setVisible(true);
            page21.setVisible(false);
            page211.setVisible(false);

        }
        if(reserver2.isFocused()){
            page1.setVisible(false);
            page21.setVisible(true);
            page2.setVisible(false);
            page211.setVisible(false);
        }
        if(reserver3.isFocused()){
            page1.setVisible(false);
            page211.setVisible(true);
            page2.setVisible(false);
            page21.setVisible(false);

        }

    }

    public void exit(){
        System.exit(0);
    }
    public void setStation_label(){
        station_label.setText(station_label.getText());

    }


    public void insert() {
        con = MyDB.getinstance().getCon();

        // Récupérez la liste de tous les IDs de vélo disponibles à partir de la table 'velo'
        List<Integer> availableBikeIds = getAllAvailableBikeIds(); // À implémenter

        if (availableBikeIds.isEmpty()) {
            // Gérez le cas où il n'y a pas de vélos disponibles
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Désolé, aucun vélo disponible !");
            alert.showAndWait();
            return;
        }

        String req = "INSERT INTO location (id_u, start_date, end_date, id, prix) VALUES (?, ?, ?, ?, ?)";

        try {
            if (start_date.getValue() == null || end_date.getValue() == null) {
                // Vérifiez si les DatePickers ont des valeurs sélectionnées
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une date de début et une date de fin !");
                alert.showAndWait();
            } else {
                // Obtenez les dates sélectionnées à partir des DatePickers
                LocalDate startDate = start_date.getValue();
                LocalDate endDate = end_date.getValue();

                // Calculez la différence en jours entre les deux dates
                long days = java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();


                float prix = days * 2;

                // Générez un ID de vélo aléatoire à partir de la liste des IDs disponibles
                int randomBikeId = getRandomBikeId(availableBikeIds);

                // Affichez le prix calculé dans le Label prix1
                prix1.setText("Prix : " + prix + " DT");
                station_label.setText(station_label.getText());


                // Assurez-vous d'avoir l'ID de l'utilisateur (id_u) que vous souhaitez insérer
                int userId = 1; // Remplacez 1 par l'ID de l'utilisateur approprié

                // Préparez la requête SQL avec les paramètres
                prepare = con.prepareStatement(req);
                prepare.setInt(1, userId);
                prepare.setString(2, startDate.toString());
                prepare.setString(3, endDate.toString());
                prepare.setInt(4, randomBikeId); // Utilisez l'ID de vélo aléatoire
                prepare.setFloat(5, prix);

                // Exécutez la requête d'insertion
                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    sendReservationEmail("houssemkhedhri7@gmail.com", startDate, endDate, prix);
                    // L'insertion a réussi, vous pouvez afficher un message de succès ou effectuer d'autres actions nécessaires.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("La location a été enregistrée avec succès !");
                    alert.showAndWait();
                } else {
                    // Gérez le cas où aucune ligne n'a été insérée
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Message d'erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Échec de l'insertion, aucune ligne n'a été insérée.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRandomBikeId(List<Integer> availableBikeIds) {
        // Générez un ID de vélo aléatoire à partir de la liste des IDs disponibles
        Random random = new Random();
        int randomIndex = random.nextInt(availableBikeIds.size());
        return availableBikeIds.get(randomIndex);
    }


    private List<Integer> getAllAvailableBikeIds() {
        List<Integer> availableBikeIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MyDB.getinstance().getCon();
            String sql = "SELECT id FROM velo WHERE etat = 'disponible'"; // Assurez-vous d'ajuster le nom du champ de disponibilité.

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bikeId = resultSet.getInt("id");
                availableBikeIds.add(bikeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception, par exemple, affichez un message d'erreur ou journalisez l'erreur.
        }


        return availableBikeIds;
    }
    public void sendReservationEmail(String userEmail, LocalDate startDate, LocalDate endDate, float prix) {
        final String username = "bike.esprit@gmail.com"; // Remplacez par votre adresse email
        final String password = "zkhd ivmz qysp tuwi"; // Remplacez par votre mot de passe

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP de Gmail
        props.put("mail.smtp.port", 587); // Port SMTP de Gmail

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); // Adresse email de l'utilisateur
            message.setSubject("Détails de la réservation");

            // Contenu de l'email
            String emailContent = "Date de début : " + startDate + "\n" +
                    "Date de fin : " + endDate + "\n" +
                    "Prix : " + prix + " DT";

            message.setText(emailContent);

            Transport.send(message);

            System.out.println("Email envoyé avec succès !");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérez les erreurs d'envoi d'email ici
        }
    }



    public class sendSMS {

        // Find your Account SID and Auth Token at console.twilio.com
        public static final String ACCOUNT_SID = "AC1dbfffb804d9fc83ed912647478edffb";
        public static final String AUTH_TOKEN = "ff0b0d421927a1981be2a3e66bab1883";

        public static void sendTwilioSMS(String messageBody) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

           // Message message = Message.creator(
                    //new PhoneNumber("+21620264013"), // Votre numéro Twilio
                  //  new PhoneNumber("+21696816101"),   // Le numéro de destination
                  //  messageBody
            //).create();

           // System.out.println(message.getSid());
        }
    }


    public void retourPagePrecedente(ActionEvent event) {

            page1.setVisible(true);
            page2.setVisible(false);


        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}