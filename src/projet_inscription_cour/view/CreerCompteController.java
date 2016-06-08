/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import projet_inscription_cour.util.BaseDeDonne;

/**
 * FXML Controller class
 *
 * @author KEF10
 */
public class CreerCompteController implements Initializable {

    private Stage stage;
    ObservableList<Cour> lisObservableDeCour = FXCollections.observableArrayList();
    
    @FXML
    private TextField champLogin;
    @FXML
    private PasswordField champPass;
    @FXML
    private PasswordField champPassRep;
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
    private Button bValide;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        miseAJourTabCour();
    }    
    
    /**
     * mise a jour du tableau des cours
     */
    public void miseAJourTabCour(){
        lisObservableDeCour = Cour.listCourBDObservableList();//mise à jour de la liste observable avec les cour contenue dans la BD
        
        tabCour.setEditable(true);//vas donnée la possibilité d'interagir avec le tableau et donc de cocher les cours
        
        choiColone.setCellFactory(new Callback<TableColumn<Cour, Boolean>, TableCell<Cour, Boolean>>() {//ce block permet de dire que la colone "choiColone" sera de type case à coché
            @Override
            public TableCell<Cour, Boolean> call(TableColumn<Cour, Boolean> p) {
                return new CheckBoxTableCell<Cour, Boolean>();
            }
        });
        choiColone.setCellValueFactory(new PropertyValueFactory<Cour, String>("choisi"));//permet de lie la colone "choiColone" à la propriéte "choisi" du beans Cour
        
        nomColone.setCellValueFactory(new PropertyValueFactory("nomCour"));//permet de lie la colone "nomColone" à la propriéte "nomCour" du beans Cour
        libeleColone.setCellValueFactory(new PropertyValueFactory("libeleCour"));//permet de lie la colone "libeleColone" à la propriéte "libeleCour" du beans Cour
                       
        tabCour.setItems(lisObservableDeCour);//on forni les donnée au tableau
    }

    @FXML
    public void boutonValider(){
        String msg ="";
        if(champsValide()){
            Compte compt = new Compte(champLogin.getText(), champPass.getText(), champNom.getText(), champPrenom.getText());
            
            if (compt.isExistCompte() == -1) {//le compte n'existe pas on peut donc le créer
               
                    //on doit mettre à jour les cour que l'étudiant vient de choisir dans la table "inscrit"
                    compt.inscritEtudiantAuxCour(lisObservableDeCour);
                   
                
                
            } else {//cas où le compte qu'on veux créer existe déja
                msg = "Login déja utilisé: veille le changer";
            }
        }
        if (msg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("alert");
            alert.setHeaderText("il ya un problème!!!");
            alert.setContentText(msg);
            alert.showAndWait();
        }


    }
    
     /**
     * cette fonction retourne vrai si tous les champs sont valide
     * @return 
     */
    private boolean champsValide(){
        String messageErreur = "";

        if (champLogin.getText() == null || champLogin.getText().length() == 0) {
            messageErreur += "login invalide!\n";
        }
        if (champPass.getText() == null || champPass.getText().length() == 0) {
            messageErreur += "mot de passe invalide!\n";
        }
        if (champNom.getText() == null || champNom.getText().length() == 0) {
            messageErreur += "nom invalide!\n";
        }
        if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
            messageErreur += "prenom invalide!\n";
        }
        if (!champPass.getText().equals(champPassRep.getText())) {
            messageErreur += "les deux mot de passe doivent être identique!\n";
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
