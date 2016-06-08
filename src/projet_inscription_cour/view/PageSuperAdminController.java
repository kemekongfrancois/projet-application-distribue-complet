/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import projet_inscription_cour.model.Compte;
import projet_inscription_cour.model.Cour;

/**
 * FXML Controller class
 *
 * @author KEF10
 */
public class PageSuperAdminController implements Initializable {
    
    Compte compteConnecter;
    private Stage stage;
    //ObservableList<Compte> lisObservableDeCompteAttenteValidation = FXCollections.observableArrayList();
    ListProperty<Compte> lisObservableDeCompteAttenteValidation = new SimpleListProperty<>();
    
    //ObservableList<Compte> lisObservableDeCompteValider = FXCollections.observableArrayList();
    ListProperty<Compte> lisObservableDeCompteValider = new SimpleListProperty();
    
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private TableView<Compte> tabCompteAttenteValidation;
    @FXML
    private TableColumn choiColoneAttenteValidation;
    @FXML
    private TableColumn nomColoneAttenteValidation;
    @FXML
    private TableColumn prenomColoneAttenteValidation;
    @FXML
    private Button bValideAttenteValidation;
    @FXML
    private TableView<Compte> tabCompteValider;
    @FXML
    private TableColumn choiColoneValider;
    @FXML
    private TableColumn nomColoneValider;
    @FXML
    private TableColumn prenomColoneValider;
    @FXML
    private Button bValideValider;
    @FXML
    private TableView<Cour> tabCour;
    @FXML
    private TableColumn nomCourColone;
    @FXML
    private TableColumn libeleCourColone;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // permet d'xécuter la fonction "afficheCourCompte" lorsqu'on selectionne un compte
        tabCompteAttenteValidation.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficheCourCompte(newValue));
        tabCompteValider.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficheCourCompte(newValue));
    }    
    
    private void afficheCourCompte(Compte compte){
        if(compte!=null) {
            ObservableList<Cour> listCour = Cour.listCourCompteBDObservableList(compte.getIdCompte());
            nomCourColone.setCellValueFactory(new PropertyValueFactory("nomCour"));//permet de lie la colone "nomCourColone" à la propriéte "nomCour" du beans Cour
            libeleCourColone.setCellValueFactory(new PropertyValueFactory("libeleCour"));

            tabCour.setItems(listCour);//on forni les donnée au tableau
        }else{
            tabCour.setItems(null);//on eface le contenue du tableau
        }
    } 
    
    
    @FXML
    private void boutonCreerCour(){
        
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageSuperAdminController.class.getResource("CreerCour.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("creer cour");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // donnée le stage à la fenetre de création de cour
            CreerCourController controller = loader.getController();
            controller.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * mise à jour des champs de l'interface graphique
     */
    public void miseAjourChamp() {
        nom.setText(compteConnecter.getNom());
        prenom.setText(compteConnecter.getPrenom());
        
        lisObservableDeCompteAttenteValidation = Compte.listCompteAttentValidation();//mise à jour de la liste observable avec les comptes en attente de validation
        
        tabCompteAttenteValidation.setEditable(true);//vas donnée la possibilité d'interagir avec le tableau et donc de cocher les comptes
    
        choiColoneAttenteValidation.setCellFactory(new Callback<TableColumn<Compte, Boolean>, TableCell<Compte, Boolean>>() {//ce block permet de dire que la colone "choiColone" sera de type case à coché
            @Override
            public TableCell<Compte, Boolean> call(TableColumn<Compte, Boolean> p) {
                return new CheckBoxTableCell<Compte, Boolean>();
            }
        });
        choiColoneAttenteValidation.setCellValueFactory(new PropertyValueFactory<Compte, String>("activer"));//permet de lie la colone "choiColone" à la propriéte "activer" du beans Compte
        
        nomColoneAttenteValidation.setCellValueFactory(new PropertyValueFactory("nom"));//permet de lie la colone "nomColone" à la propriéte "nom" du beans Compte
        prenomColoneAttenteValidation.setCellValueFactory(new PropertyValueFactory("prenom"));//permet de lie la colone "prenomColone" à la propriéte "prenom" du beans Compte
                       
        tabCompteAttenteValidation.setItems(lisObservableDeCompteAttenteValidation);//on forni les donnée au tableau
        
        miseAjourChamptabvalide();//mise à jour du tableau des compte valide
    }
    
    /**
     * mise à jour des champs du tableau des compte valide
     */
    public void miseAjourChamptabvalide() {
        
        lisObservableDeCompteValider = Compte.listCompterValider();//mise à jour de la liste observable avec les comptes validé
        
        tabCompteValider.setEditable(true);//vas donnée la possibilité d'interagir avec le tableau et donc de cocher les comptes
    
        choiColoneValider.setCellFactory(new Callback<TableColumn<Compte, Boolean>, TableCell<Compte, Boolean>>() {//ce block permet de dire que la colone "choiColone" sera de type case à coché
            @Override
            public TableCell<Compte, Boolean> call(TableColumn<Compte, Boolean> p) {
                return new CheckBoxTableCell<Compte, Boolean>();
            }
        });
        choiColoneValider.setCellValueFactory(new PropertyValueFactory<Compte, String>("activer"));//permet de lie la colone "choiColone" à la propriéte "activer" du beans Compte
        
        nomColoneValider.setCellValueFactory(new PropertyValueFactory("nom"));//permet de lie la colone "nomColone" à la propriéte "nom" du beans Compte
        prenomColoneValider.setCellValueFactory(new PropertyValueFactory("prenom"));//permet de lie la colone "prenomColone" à la propriéte "prenom" du beans Compte
            
        for(int i=0;i<lisObservableDeCompteValider.size();i++){
            //cette partie de faire en sorte que les compte n'apparaisse pas coché
            lisObservableDeCompteValider.get(i).setActiver(false);
        }
        
        tabCompteValider.setItems(lisObservableDeCompteValider);//on forni les donnée au tableau
        
    }
    
    @FXML
    private void boutonValiderAttenteValidation() {
        Compte.ActiverCompte(lisObservableDeCompteAttenteValidation);//mise à jour dans la BD
        //miseAjourChamp();
        
        Compte compte;
        //mise à jour sur l'IHM
        for (int i = 0; i < lisObservableDeCompteAttenteValidation.size(); i++) {
        //for (Compte compte : lisObservableDeCompteAttenteValidation.get()) {
            compte = lisObservableDeCompteAttenteValidation.get(i);
            compte.afficheCompte();
            if (compte.isActiver()) {
                lisObservableDeCompteValider.add(lisObservableDeCompteAttenteValidation.remove(i));
                i--;//car la taille de la liste change à chaque fois qu'on retir un élément de la liste il faut donc mettre à jour la variable de parcourt
                compte.setActiver(false);
            }
        }
        

    }
    
    @FXML
    private void boutonValiderCompteValide() {
        Compte.desactiverCompte(lisObservableDeCompteValider);//mise à jour dans la BD
        //miseAjourChamp();
        
        Compte compte;
        //mise à jour sur l'IHM
        for (int i = 0; i < lisObservableDeCompteValider.size(); i++) {
        //for (Compte compte : lisObservableDeCompteValider.get()) {
            compte = lisObservableDeCompteValider.get(i);
            compte.afficheCompte();
            if (compte.isActiver()) {
                //lisObservableDeCompteAttenteValidation.add(compte);
                lisObservableDeCompteAttenteValidation.add(lisObservableDeCompteValider.remove(i));
                i--;//car la taille de la liste change à chaque fois qu'on retir un élément de la liste il faut donc mettre à jour la variable de parcourt
                compte.setActiver(false);
            }
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
