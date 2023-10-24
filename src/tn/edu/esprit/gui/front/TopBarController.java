package tn.edu.esprit.gui.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import tn.edu.esprit.tools.Animations;
import tn.edu.esprit.tools.Constants;
import workshopjdbc.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.TRANSPARENT;
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnProduits;

    @FXML
    private Button btnPaniers;

    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnProduits,
                btnPaniers,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, Color.WHITE, Color.WHITE, Color.BLACK, 0, false);
        }

        btnProduits.setTextFill(COLOR_DARK);
        btnPaniers.setTextFill(COLOR_DARK);
    }

    @FXML
    private void afficherProduits(ActionEvent ignored) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_PRODUIT);

        btnProduits.setTextFill(Color.BLACK);
        Animations.animateButton(btnProduits, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherPaniers(ActionEvent ignored) {
        goToLink(Constants.FXML_FRONT_DISPLAY_MY_PANIER);

        btnPaniers.setTextFill(Color.BLACK);
        Animations.animateButton(btnPaniers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent ignored) {
        MainApp.getInstance().logout();
    }
}
