package services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    private String adresseEmailExpeditrice; // Adresse e-mail de l'expéditeur
    private String motDePasseExpeditrice; // Mot de passe de l'expéditeur
    private Session session; // Objet Session pour la configuration de l'envoi d'e-mails
    private String smtpHost; // L'hôte SMTP (par exemple, "smtp.gmail.com")
    private String username; // Le nom d'utilisateur pour l'authentification SMTP
    private String password; // Le mot de passe pour l'authentification SMTP

    public EmailService(String adresseEmailExpeditrice, String motDePasseExpeditrice) {
        this.adresseEmailExpeditrice = adresseEmailExpeditrice;
        this.motDePasseExpeditrice = motDePasseExpeditrice;

        // Configuration de la session pour l'envoi d'e-mails
        smtpHost = "smtp.gmail.com"; // Remplacez par l'hôte SMTP approprié
        username = adresseEmailExpeditrice;
        password = motDePasseExpeditrice;

        // Configuration des propriétés pour l'envoi d'e-mails
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "587");

        // Créez une session de messagerie
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void envoyerEmail(String destinataire, String sujet, String contenu) {
        try {
            // Créez un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adresseEmailExpeditrice));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            // Envoyez le message
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + destinataire);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Ajoutez d'autres méthodes si nécessaire
}
