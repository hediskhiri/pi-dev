package tn.edu.esprit.gui.front.panier;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.edu.esprit.entities.Panier;
import tn.edu.esprit.entities.PanierProduit;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.gui.front.MainWindowController;
import tn.edu.esprit.services.PanierProduitService;
import tn.edu.esprit.tools.AlertUtils;
import tn.edu.esprit.tools.Constants;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class MonPanierController implements Initializable {

    @FXML
    public Text topText;

    @FXML
    public Button addButton;

    @FXML
    public VBox mainVBox;

    public static Panier panier;

    @FXML
    public static List<PanierProduit> monPanierProduitList = new ArrayList<>();

    @FXML
    public Text totalText;
    @FXML
    public Text totalTextSansRemise;

    public static Panier getPanier() {
        if (panier == null) {
            panier = PanierProduitService.getInstance().getPanier(1);
        }
        return panier;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monPanierProduitList = PanierProduitService.getInstance().getAllByPanier(getPanier().getId());

        displayData();

        int prixTotalSansRemise = 0;
        for (PanierProduit panierProduit : monPanierProduitList) {
            prixTotalSansRemise += panierProduit.getQte() * panierProduit.getProduit().getPrixProd();
        }
        totalTextSansRemise.setText("Prix total sans remise : " + prixTotalSansRemise);

        int prixTotal = getPrixTotal();

        totalText.setText("Total : " + prixTotal);

    }

    private static int getPrixTotal() {
        int prixTotal = 0;

        for (PanierProduit panierProduit : monPanierProduitList) {
            int qte = panierProduit.getQte();
            float remise = panierProduit.getProduit().getRemise(); // Assuming remise is a percentage.

            // Calculate the price after applying the discount.
            float prixProd = panierProduit.getProduit().getPrixProd();
            float discountedPrice = prixProd - (prixProd * remise / 100);

            // Add the product price with the discount applied to the total.
            prixTotal += (int) (qte * discountedPrice);
        }
        return prixTotal;
    }

    void displayData() {
        mainVBox.getChildren().clear();

        int panierProduitCount = 0;
        for (PanierProduit panierProduit : monPanierProduitList) {
            if (panierProduit.getPanier() != null) {
                panierProduitCount++;
                mainVBox.getChildren().add(makePanierProduitModel(panierProduit));
            }
        }

        if (panierProduitCount == 0) {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makePanierProduitModel(
            PanierProduit panierProduit
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PANIER_PRODUIT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#produitNameText")).setText("Produit : " + panierProduit.getProduit().getNomProd());
            ((Text) innerContainer.lookup("#quantityText")).setText("Quantity : " + panierProduit.getQte());
            ((Text) innerContainer.lookup("#unitPriceText")).setText("Unit price : " + panierProduit.getProduit().getPrixProd());
            ((Text) innerContainer.lookup("#discountText")).setText("Remise : " + panierProduit.getProduit().getRemise() + "%");

            ((Text) innerContainer.lookup("#totalPriceText")).setText(
                    "Total price : " + (
                            panierProduit.getProduit().getPrixProd() * panierProduit.getQte() * (
                                    1 - (panierProduit.getProduit().getRemise() / 100)
                            )
                    )
            );

            ((Button) innerContainer.lookup("#addButton")).setOnAction((event) -> addQuantity(panierProduit));
            ((Button) innerContainer.lookup("#removeButton")).setOnAction((event) -> removeQuantity(panierProduit));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void addQuantity(PanierProduit panierProduit) {
        panierProduit.setQte(panierProduit.getQte() + 1);
        boolean success = PanierProduitService.getInstance().updateQuantity(panierProduit);
        if (success) {
            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
        } else {
            AlertUtils.makeError("Error");
        }
    }

    private void removeQuantity(PanierProduit panierProduit) {
        if (panierProduit.getQte() == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText(null);
            alert.setContentText("Etes vous sûr de vouloir supprimer cet produit de votre panier ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.isPresent()) {
                if (action.get() == ButtonType.OK) {
                    boolean success = PanierProduitService.getInstance().delete(panierProduit);
                    if (success) {
                        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
                    } else {
                        AlertUtils.makeError("Error");
                    }
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
                }
            }
        } else {
            panierProduit.setQte(panierProduit.getQte() - 1);
            boolean success = PanierProduitService.getInstance().updateQuantity(panierProduit);
            if (success) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
            } else {
                AlertUtils.makeError("Error");
            }
        }
    }

    @FXML
    private void ajouterPanierProduit(ActionEvent ignored) {
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_ADD_PANIER_PRODUIT);
    }

    @FXML
    public void genererPDF(ActionEvent ignored) {
        List<PanierProduit> panierProduitList = monPanierProduitList;

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("produit.pdf")));
            document.open();

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(20);

            int i = 0;
            float prixTotal = 0;

            document.add(new Paragraph(" -------------------------------------------------------------------------------------------------------------------- "));
            document.add(new Paragraph(" --------------------------------------------------- FACTURE ------------------------------------------------- "));
            document.add(new Paragraph(" -------------------------------------------------------------------------------------------------------------------- "));


            for (PanierProduit panierProduit : panierProduitList) {
                prixTotal = prixTotal + panierProduit.getProduit().getPrixProd() * panierProduit.getQte() * (
                        1 - (panierProduit.getProduit().getRemise() / 100)
                );
                i++;

                Produit produit = panierProduit.getProduit();

                document.add(new Paragraph("- Produit " + i + " -"));

                try {
                    com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(produit.getImageProd());
                    image.scaleAbsoluteWidth(300);
                    image.scaleAbsoluteHeight(300);
                    image.isScaleToFitHeight();
                    document.add(image);
                } catch (FileNotFoundException e) {
//                    AlertUtils.makeError("Image not found, PDF will display without image");
                }

                document.add(new Paragraph("Nom : " + produit.getNomProd() +
                        " | Description : " + produit.getDescriptionProd() +
                        " | Prix : " + produit.getPrixProd() +
                        " | Remise : " + produit.getRemise() +
                        " | Categorie : " + produit.getCategories()));

                document.add(new Paragraph(" \n "));
            }

            document.add(new Paragraph(" \n "));
            document.add(new Paragraph(" -------------------------------------------------------------------------------------------------------------------- "));
            document.add(new Paragraph(" \n "));
            document.add(new Paragraph("Nombre de produits : " + panierProduitList.size()));
            document.add(new Paragraph("Prix total : " + prixTotal));


            document.newPage();
            document.close();

            writer.close();

            Desktop.getDesktop().open(new File("produit.pdf"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
