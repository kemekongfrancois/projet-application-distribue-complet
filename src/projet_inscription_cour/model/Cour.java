/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.model;

import java.util.Collection;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet_inscription_cour.util.BaseDeDonne;
import ws.Cours;
import ws.ManageWS;
import ws.ManageWS_Service;

/**
 *
 * @author KEF10
 */
public class Cour {
    static ManageWS ws = new ManageWS_Service().getManageWSPort();
    /**
     * cette variable vas permetre de connaitre si le cour es choisi lors du chois de cour par l'étudiant
     */
    private BooleanProperty choisi;
    private final IntegerProperty idCour;
    private final StringProperty nomCour;
    private final StringProperty libeleCour;
    
   // String nomCour;
   // String libeleCour;
    
    public Cour(String nomCour, String libeleCour){
        this.choisi = new SimpleBooleanProperty(false);//par defau on suposera que l'etudiant n'a pas chois le cour courant
        this.idCour = new SimpleIntegerProperty(0);
        this.nomCour = new SimpleStringProperty(nomCour);
        this.libeleCour = new SimpleStringProperty(libeleCour);
    }
    
    public ws.Cours getCOurWS(){
        ws.Cours courws = new Cours();
        courws.setIdcour(this.getIdCour());
        courws.setLibelecour(this.getLibeleCour());
        courws.setNomcour(this.getNomCour());
        return courws;
        
    }
    
    /**
     * cette fonction verrifie si le cour courant existe déja dans la base de donné
     * @return 
     */
    public boolean isExistCour(){
        /*
        String requete = "SELECT NOMCOUR FROM cours where NOMCOUR=\""+getNomCour()+"\";";
        BaseDeDonne bd = new BaseDeDonne();
        List<List> resultat = bd.resultaRequete(requete);
        
        if(resultat.size()<2) return false;
        else return true;
        */
        return false;
    }
    
    public boolean enregistreCourBD(){
        /*
        String requete = "INSERT INTO `bd_tp_interface_java`.`cours` (`NOMCOUR`, `LIBELECOUR`) VALUES ('"+getNomCour()+"', '"+getLibeleCour()+"');";
        BaseDeDonne bd = new BaseDeDonne();
        return bd.requeteInsertUpdate(requete);
        */
        return ws.creerCour(getNomCour(), getLibeleCour());
    }
    
    /**
     * retourne une liste observable qui represent l'ensemble des cour present dans la BD
     * @return 
     */
    public static ObservableList<Cour> listCourBDObservableList(){
        /*
        BaseDeDonne bd = new BaseDeDonne();
        String requete = "SELECT * FROM bd_tp_interface_java.cours;";
        List<List> courBD = bd.resultaRequete(requete);
        */
        List<ws.Cours> courBD = ws.getAllCour();
        String nomCour;
        String libeleCour; 
        
        ObservableList<Cour> lisObservableDeCour = FXCollections.observableArrayList();
        /*
        for (int i = 1; i < courBD.size(); i++) {//on commence à 1 car à 0 on à l'entête

            nomCour = (courBD.get(i)).get(1).toString();//la position 1 contiend le nom du cour
            libeleCour = (courBD.get(i)).get(2).toString();//la position 2 contient le libele du cour
            Cour cour = new Cour(nomCour, libeleCour);
            cour.setIdCour(new Integer((courBD.get(i)).get(0).toString()));//la position 0 contiend l'identifiant
            lisObservableDeCour.add(cour);
            //System.out.println(nomCour+"\n"+libeleCour+"\n");
        }
        */
        for(ws.Cours cr: courBD){
            nomCour = cr.getNomcour();
            libeleCour = cr.getLibelecour();
            Cour cour = new Cour(nomCour, libeleCour);
            cour.setIdCour(cr.getIdcour());
            lisObservableDeCour.add(cour);
        }
        return lisObservableDeCour;
    }
    
     /**
     * retourne une liste observable qui represent l'ensemble des cours au quelle le compte es inscrit
     * @return 
     */
    public static ObservableList<Cour> listCourCompteBDObservableList(Integer idCompte){
        /*
        BaseDeDonne bd = new BaseDeDonne();
        String sousrequete,requete = "SELECT IDCOUR FROM bd_tp_interface_java.inscrit where IDCOMPTE="+idCompte+";";
        List<List> courBD ,listIdcourBD = bd.resultaRequete(requete);
        */
        String nomCour;
        String libeleCour; 
        
        Collection<ws.Cours> courBD = ws.getCourDuCompte(idCompte);
        ObservableList<Cour> lisObservableDeCour = FXCollections.observableArrayList();
        /*
        for (int i = 1; i < listIdcourBD.size(); i++) {//on commence à 1 car à 0 on à l'entête
            sousrequete = "SELECT * FROM bd_tp_interface_java.cours where IDCOUR="+(listIdcourBD.get(i)).get(0).toString()+";";
            courBD = bd.resultaRequete(sousrequete);
            if(courBD.size()>1){
                nomCour = (courBD.get(1)).get(1).toString();//la position 1 contiend le nom du cour
                libeleCour = (courBD.get(1)).get(2).toString();//la position 2 contient le libele du cour
                Cour cour = new Cour(nomCour, libeleCour);
                cour.setIdCour(new Integer((courBD.get(1)).get(0).toString()));//la position 0 contiend l'identifiant
                cour.setChoisi(Boolean.TRUE);//on met le champs choisi à vrai
                lisObservableDeCour.add(cour);
                //System.out.println(nomCour+"\n"+libeleCour+"\n");
            }
            
        }*/
        for(ws.Cours cr: courBD){
            nomCour = cr.getNomcour();
            libeleCour = cr.getLibelecour();
            Cour cour = new Cour(nomCour, libeleCour);
            cour.setChoisi(Boolean.TRUE);//on met le champs choisi à vrai
            cour.setIdCour(cr.getIdcour());
            lisObservableDeCour.add(cour);
        }
        return lisObservableDeCour;
    }
    
    /**
     * retourne une liste observable qui represent l'ensemble des cours au quelle le compte n'es pas inscrit
     * @return 
     */
    public static ObservableList<Cour> listCourNomInscritCompte(Integer idCompte){
        /*
        BaseDeDonne bd = new BaseDeDonne();
        String sousrequete,requete = "SELECT IDCOUR FROM bd_tp_interface_java.cours "
                + "where IDCOUR not in (SELECT IDCOUR FROM bd_tp_interface_java.inscrit where IDCOMPTE="+idCompte+");";
        List<List> courBD ,listIdcourBD = bd.resultaRequete(requete);
        String nomCour;
        String libeleCour; 
        
        ObservableList<Cour> lisObservableDeCour = FXCollections.observableArrayList();
        for (int i = 1; i < listIdcourBD.size(); i++) {//on commence à 1 car à 0 on à l'entête
            sousrequete = "SELECT * FROM bd_tp_interface_java.cours where IDCOUR="+(listIdcourBD.get(i)).get(0).toString()+";";
            courBD = bd.resultaRequete(sousrequete);
            if(courBD.size()>1){
                nomCour = (courBD.get(1)).get(1).toString();//la position 1 contiend le nom du cour
                libeleCour = (courBD.get(1)).get(2).toString();//la position 2 contient le libele du cour
                Cour cour = new Cour(nomCour, libeleCour);
                cour.setIdCour(new Integer((courBD.get(1)).get(0).toString()));//la position 0 contiend l'identifiant
                lisObservableDeCour.add(cour);
                //System.out.println(nomCour+"\n"+libeleCour+"\n");
            }
            
        }
        return lisObservableDeCour;
*/
        String nomCour;
        String libeleCour; 
        
        Collection<ws.Cours> courBD = ws.getCourDuCompteNonInscri(idCompte);
        ObservableList<Cour> lisObservableDeCour = FXCollections.observableArrayList();
        
        for(ws.Cours cr: courBD){
            nomCour = cr.getNomcour();
            libeleCour = cr.getLibelecour();
            Cour cour = new Cour(nomCour, libeleCour);
            cour.setIdCour(cr.getIdcour());
            lisObservableDeCour.add(cour);
        }
        return lisObservableDeCour;
    }
    
    
    public void afficheCour(){
        System.out.println("\n idCour= "+getIdCour());
        System.out.println(" nom Cour= "+getNomCour());
        System.out.println(" libele Cour= "+getLibeleCour());
        System.out.println(" chois Cour= "+getChoisi());
    }
    
    
    //getter et setter

    public int getIdCour() {
        return idCour.get();
    }

    public void setIdCour(int value) {
        idCour.set(value);
    }

    public IntegerProperty idCourProperty() {
        return idCour;
    }

    public String getNomCour() {
        return nomCour.get();
    }

    public void setNomCour(String value) {
        nomCour.set(value);
    }

    public StringProperty nomCourProperty() {
        return nomCour;
    }

    public String getLibeleCour() {
        return libeleCour.get();
    }

    public void setLibeleCour(String value) {
        libeleCour.set(value);
    }

    public StringProperty libeleCourProperty() {
        return libeleCour;
    }

    public Boolean getChoisi() {
        return choisi.get();
    }

    public void setChoisi(Boolean value) {
        this.choisi.set(value);
    }

    public BooleanProperty choisiProperty() {
        return choisi;
    }
    
}
