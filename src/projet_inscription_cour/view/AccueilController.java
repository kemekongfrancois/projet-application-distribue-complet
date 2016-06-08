/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projet_inscription_cour.model.Compte;
import projet_inscription_cour.util.BaseDeDonne;
import ws.ManageWS;
import ws.ManageWS_Service;

/**
 * FXML Controller class
 *
 * @author KEF10
 */
public class AccueilController implements Initializable {

    Compte compteConnecter;
    private BorderPane accueilLayout;
    private Stage stage;
    
    ManageWS ws = new ManageWS_Service().getManageWSPort();

    @FXML
    private TextField champLogin;
    @FXML
    private TextField champPass;
    @FXML
    private Button bConnexion;
    @FXML
    private Button bReinitialise;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void boutonConnexion() {
        if (champsValide() && authentification()) {
            if (compteConnecter.isSupAdmin()) {
                chargeSupAdmin();
            } else {
                chargEtudiantCompte();
            }
        }
    }
    
   
    @FXML
    private void boutonCreerCompte() {
        try {
            // chargement de la page d'accueil appartir du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AccueilController.class.getResource("CreerCompte.fxml"));
            AnchorPane pageSuperAdmin = (AnchorPane) loader.load();

            // on introduit l'interface au centre de la fenétre
            accueilLayout.setCenter(pageSuperAdmin);

            //envoie de donnée au controleur
            CreerCompteController controller = loader.getController();
            controller.setStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cette fonction charge l'interface de connection d'un compte
     * standar(Etudiant)
     */
    private void chargEtudiantCompte() {
        try {
            // chargement de la page d'accueil appartir du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AccueilController.class.getResource("PageEtudiant.fxml"));
            AnchorPane pageEtudiant = (AnchorPane) loader.load();

            // on introduit l'interface au centre de la fenétre
            accueilLayout.setCenter(pageEtudiant);

            //envoie de donnée au controleur
            PageEtudiantController controller = loader.getController();
            controller.setCompteConnecter(compteConnecter);
            controller.setStage(stage);
            controller.miseAjourChamp();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * cette fonction charge l'interface de connection du super administrateur
     */
    private void chargeSupAdmin() {
        try {
            // chargement de la page d'accueil appartir du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AccueilController.class.getResource("PageSuperAdmin.fxml"));
            AnchorPane pageSuperAdmin = (AnchorPane) loader.load();

            // on introduit l'interface au centre de la fenétre
            accueilLayout.setCenter(pageSuperAdmin);

            //envoie de donnée au controleur
            PageSuperAdminController controller = loader.getController();
            controller.setCompteConnecter(compteConnecter);
            controller.setStage(stage);
            controller.miseAjourChamp();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cette fonction permet de s'authentifié et de mettre à jour le model
     *
     * @return
     */
    private boolean authentification() {
        String messageErreur = "";
        BaseDeDonne baseDeDonne = new BaseDeDonne();
        /* baseDeDonne.conectionBD();
        if(baseDeDonne.connectionOK()){
            baseDeDonne.deConectionBD();//on se deconnecte car l'exécution de la fonction resultaRequete se connectera à nouveau et se déconnectera à la fin
         */
        String login = champLogin.getText();
        String pass = champPass.getText();

        //String requete = "select * from compte where LOGIN=\"" + login + "\" and PASS=\"" + pass + "\"";
        ws.Compte resultat = ws.getCompteLoginPass(login, pass);
        //baseDeDonne.afficheContenuList(resultat);

        if (resultat == null) {
            messageErreur = "Login ou mot de passe incorrect";
        
        } else {//le compte existe
            compteConnecter = new Compte(resultat);
            //compteConnecter.afficheCompte();
            return true;
        }
        /*}else{
            messageErreur = "echec de la connexion à la BD";
        }*/
        if (messageErreur.length() != 0) {
            // il ya eu un pb on affiche le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Erreur");
            alert.setHeaderText("il y'a eu un problème ");
            alert.setContentText(messageErreur);

            alert.showAndWait();
        }
        return false;

    }

    /**
     * cette fonction retourne vrai si tous les champs sont valide
     *
     * @return
     */
    private boolean champsValide() {
        String messageErreur = "";

        if (champLogin.getText() == null || champLogin.getText().length() == 0) {
            messageErreur += "login invalide!\n";
        }
        if (champPass.getText() == null || champPass.getText().length() == 0) {
            messageErreur += "mot de passe invalide!\n";
        }

        if (messageErreur.length() == 0) {//il n'ya pas eu de pb

            return true;
        } else {
            // il ya eu un pb on affiche le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Champs invalide ");
            alert.setHeaderText("Veillé bien remplir tous les champs ");
            alert.setContentText(messageErreur);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void boutonReinitialise() {
        champLogin.setText(null);
        champPass.setText(null);
    }

    @FXML
    private void boutonFichierFermer() {
        stage.close();
    }

    //setter et getter
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public BorderPane getAccueilLayout() {
        return accueilLayout;
    }

    public void setAccueilLayout(BorderPane accueilLayout) {
        this.accueilLayout = accueilLayout;
    }

}
