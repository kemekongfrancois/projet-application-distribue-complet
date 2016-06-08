/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import projet_inscription_cour.model.Compte;
import projet_inscription_cour.model.Cour;

/**
 * FXML Controller class
 *
 * @author KEF10
 */
public class PageEtudiantController implements Initializable {
    Compte compteConnecter;
    private Stage stage;

    @FXML
    Label msg;
    @FXML
    private TextField champNom;
    @FXML
    private TextField champPrenom;
    @FXML
    private TableView tabCour;
    @FXML
    private TableColumn choiColone;
    @FXML
    private TableColumn nomColone;
    @FXML
    private TableColumn libeleColone;
    @FXML
    private Button bEnregistrer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 
    /**
     * mise à jour des champs de l'interface graphique
     */
    public void miseAjourChamp() {
        champNom.setText(compteConnecter.getNom());
        champPrenom.setText(compteConnecter.getPrenom());
        
        ObservableList<Cour> lisObservableDeCour = Cour.listCourCompteBDObservableList(compteConnecter.getIdCompte());//mise à jour de la liste observable avec les cours que l'étudiants avait choisi
        ObservableList<Cour> lisObservableDeCourNonChoisi = Cour.listCourNomInscritCompte(compteConnecter.getIdCompte());//on recupére les cour que l'étudiant n'a pas choisi
        for(int i=0;i<lisObservableDeCourNonChoisi.size();i++) {//on introduit dans la liste des cour choisi les cour non choisi
            lisObservableDeCour.add(lisObservableDeCourNonChoisi.get(i));
        }
        
        //tabCour.setEditable(true);//vas donnée la possibilité d'interagir avec le tableau et donc de cocher les cours
    
        choiColone.setCellFactory(new Callback<TableColumn<Cour, Boolean>, TableCell<Cour, Boolean>>() {//ce block permet de dire que la colone "choiColone" sera de type case à coché
            @Override
            public TableCell<Cour, Boolean> call(TableColumn<Cour, Boolean> p) {
                return new CheckBoxTableCell<Cour, Boolean>();
            }
        });
        choiColone.setCellValueFactory(new PropertyValueFactory<Compte, String>("choisi"));//permet de lie la colone "choiColone" à la propriéte "activer" du beans Compte
        
        nomColone.setCellValueFactory(new PropertyValueFactory("nomCour"));//permet de lie la colone "nomColone" à la propriéte "nom" du beans Compte
        libeleColone.setCellValueFactory(new PropertyValueFactory("libeleCour"));//permet de lie la colone "prenomColone" à la propriéte "prenom" du beans Compte
                       
        tabCour.setItems(lisObservableDeCour);//on forni les donnée au tableau
        
        if(compteConnecter.isActiver()){//on verroille les champ si le compte es activé
            tabCour.setEditable(false);
            champNom.setEditable(false);
            champPrenom.setEditable(false);
            bEnregistrer.setDisable(true);
            msg.setText("Vous ne pouvez pas apporter des modifications à votre compte veille contacter l’administrateur ");
        }
    }
    
    @FXML
    private void boutonEnregistrer(){
        if(champsValide()){
            compteConnecter.setNom(champNom.getText());
            compteConnecter.setPrenom(champPrenom.getText());
            if(!compteConnecter.updateCompteBD()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("problème ");
                alert.setHeaderText("verrifié la connection réseau ");
                alert.setContentText("impossible de communiqué avec la base de donnée");

                alert.showAndWait();
            }
                
            
        }
    }
    
    /**
     * cette fonction retourne vrai si tous les champs sont valide
     * @return 
     */
    private boolean champsValide(){
        String messageErreur = "";

        if (champNom.getText() == null || champNom.getText().length() == 0) {
            messageErreur += "nom invalide!\n";
        }
        if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
            messageErreur += "prenom invalide!\n";
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
    //setter et getter

    public Compte getCompteConnecter() {
        return compteConnecter;
    }

    public void setCompteConnecter(Compte compteConnecter) {
        this.compteConnecter = compteConnecter;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
