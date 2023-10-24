package tn.edu.esprit.gui.front.panier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tn.edu.esprit.entities.PanierProduit;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.gui.front.MainWindowController;
import tn.edu.esprit.services.PanierProduitService;
import tn.edu.esprit.tools.AlertUtils;
import tn.edu.esprit.tools.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class AjouterPanierProduit implements Initializable {

    @FXML
    public ComboBox<Produit> produitCB;
    @FXML
    public TextField quantityTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Produit produit : PanierProduitService.getInstance().getAllProduit()) {
            produitCB.getItems().add(produit);
        }

        topText.setText("Ajouter produit au panier");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            PanierProduit panierProduit = new PanierProduit(
                    produitCB.getValue(),
                    MonPanierController.panier,
                    Integer.parseInt(quantityTF.getText())
            );

            boolean success = PanierProduitService.getInstance().add(panierProduit);
            if (success) {
                MonPanierController.monPanierProduitList.add(panierProduit);
                AlertUtils.makeSuccessNotification("Produit ajouté avec succés");
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
            } else {
                AlertUtils.makeError("Erreur");
            }
        }
    }


    private boolean controleDeSaisie() {


        if (produitCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir produit");
            return false;
        }

        if (quantityTF.getText().isEmpty()) {
            AlertUtils.makeInformation("quantity ne doit pas etre vide");
            return false;
        }

        try {
            Integer.parseInt(quantityTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("quantity doit etre un nombre");
            return false;
        }

        return true;
    }
}