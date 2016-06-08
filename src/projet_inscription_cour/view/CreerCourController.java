/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projet_inscription_cour.model.Cour;

/**
 * FXML Controller class
 *
 * @author KEF10
 */
public class CreerCourController implements Initializable {
    private Stage stage;

    @FXML
    private TextField nomCour;
    @FXML
    private TextField libeleCour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void boutonEnregistrer(){
        if(champsValide()){
            Cour cour = new Cour(nomCour.getText(), libeleCour.getText());
            if (cour.isExistCour()) {
                // le cour existe déja
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Erreur ");
                alert.setHeaderText("Ce cour existe déja ");
                alert.setContentText("changer le nom du cour");

                alert.showAndWait();
            }else{
                if(cour.enregistreCourBD()){
                    stage.close();
                }else{
                    // problème lors de l'exécution de la requete
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("Erreur de requete BD ");
                    alert.setHeaderText("problème lors de l'exécution de la requete ");
                    alert.setContentText("");

                    alert.showAndWait();
                }
            }
        }
    }
    /**
     * cette fonction retourne vrai si tous les champs sont valide
     * @return 
     */
    private boolean champsValide(){
        String messageErreur = "";

        if (nomCour.getText() == null || nomCour.getText().length() == 0) {
            messageErreur += "nom de cour invalide!\n";
        }
        if (libeleCour.getText() == null || libeleCour.getText().length() == 0) {
            messageErreur += "libelé de cour invalide!\n";
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
