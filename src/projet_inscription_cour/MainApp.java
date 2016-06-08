/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import projet_inscription_cour.view.AccueilController;

/**
 *
 * @author KEF10
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane accueilLayout;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MiniProjet");

        initPageAccueil();

    }
        
    /**
     * Initialisation de la page d'accueil
     */
    public void initPageAccueil() {
        try {
            // chargement de la page d'accueil appartir du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Accueil.fxml"));
            accueilLayout = (BorderPane) loader.load();
            
            // donnée le stage courant et la fenetre d'accueil au controleur de l'accueil
            AccueilController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.setAccueilLayout(accueilLayout);

            // Show the scene containing the root layout.
            Scene scene = new Scene(accueilLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
