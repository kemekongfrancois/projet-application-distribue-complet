/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_inscription_cour.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KEF10
 */
public class BaseDeDonne {
    /*
    private String pilote="com.mysql.jdbc.Driver";
    private String nomBD="bd_tp_interface_java";
    private String adresseBD="127.0.0.1";
    private String userBD="root";
    private String passwdBD="kef007007";
    private Connection connexionBD ;
    */
    public String msgEreur = "\n";//cette variable vas contenir le dernier message d'erreur rencontre
    
    /**
     * cette fonction permet d'effectue la connection à une BD
     * si la connection se passe bien l'attribut "connexionBD" sera différent de "null"
     * s'il ya eu un pb "connexionBD" aura la valeur null et "msgEreur" contiendra l'exception rencontrée
     * @return 
     */
    /*
    public Connection conectionBD() {
            Connection connexion = null;
        try {
            Class.forName(pilote);
            
            connexion = DriverManager.getConnection("jdbc:mysql://"+adresseBD+"/"+nomBD, userBD, passwdBD);
            connexionBD = connexion;
            System.out.println("succé de la connexion à la BD");
        
        } catch (Exception e) {
            e.printStackTrace();
            msgEreur += e+"\n**********************************\n";
        }
        return connexion;
    }
    */
    /**
     * cette fonction permet de se déconnecter de la BD
     * @return 
     */
    /*
    public boolean deConectionBD() {
        if (connexionBD != null) {
            try {
                connexionBD.close();
                System.out.println("succé de la déconnexion à la BD");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BaseDeDonne.class.getName()).log(Level.SEVERE, null, ex);
                msgEreur += ex+"\n**********************************\n";
                return false;
            }
        }
        msgEreur += "il n'existe pas de connection à la BD \n**********************************\n";
        return false;
    }
    */
    /**
     * cette fonction retourne une liste de liste qui represente le resultat de
     * l'exécution de la requete
     *
     * @param requete
     * @return null si il ya eu un pb et si la liste ne contiend qu'un seul élément,alors le resultat de la requete es nul car
     * le 1er élément represente l'entête de la requete
     */
    /*
    public List<List> resultaRequete( String requete) {
        conectionBD();
        java.util.List<java.util.List> resultaFinal = null;
        if (connexionBD == null) {
            System.out.println("pas de connexion à la BD");
            return null;
        } else {//la connection à la BD a reusi
            try {
                resultaFinal = new ArrayList<>();
                ResultSet result = connexionBD.createStatement().executeQuery(requete);
                int nbChampRequete = result.getMetaData().getColumnCount();//on recuper le nb de colone de la requete

                java.util.List<String> ligne = new ArrayList<>();//on initialise la ligne

                //introduction de l'entête
                ResultSetMetaData resultMeta = result.getMetaData();
                for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
                    ligne.add(resultMeta.getColumnName(i).toUpperCase());
                }
                resultaFinal.add(ligne);//on introduit l'entête

                while (result.next()) {//on parcourt les differentes lignes du resultat
                    ligne = new ArrayList<>();//on initialise la ligne
                    for (int i = 0; i < nbChampRequete; i++) {//on recuper une ligne du resultat de la requette qu'on range dans notre liste
                        ligne.add(result.getObject(i + 1).toString());//getObjet commence à 0 raison pour laquelle on fait le +1 
                        //System.out.print("\t" + i + "---" + result.getObject(i + 1).toString() + "\t |");
                    }
                    System.out.println("");
                    resultaFinal.add(ligne);//on introduit la ligne dans la liste finnal
                }
                deConectionBD();
                return resultaFinal;
            } catch (SQLException ex) {
                System.out.println(ex+"");
                msgEreur += ex+"\n**********************************\n";
                deConectionBD();
                return null;
            } 
        }
    }
    */
    /**
     * cette fonction exécute la requete de mise à jour ou d'insertion pris en parametre
     * @param requete 
     * @return true si l'insertion ou la mise a jour c'est bien passé
     */
    /*
    public boolean requeteInsertUpdate(String requete){
        conectionBD();
        if(connexionBD == null) {
            System.out.println("pas de connexion à la BD");
            return false;
        }else{
            try {
                connexionBD.createStatement().executeUpdate(requete);//exécution de la requete
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BaseDeDonne.class.getName()).log(Level.SEVERE, null, ex);
                msgEreur += ex+"\n**********************************\n";
                return false;
            }
        }
        
            
    }
    */
    /**
     * cette fonction affiche le contenue de la lite de liste pris en parametre
     *
     * @param donne
     */
    /*
    public void afficheContenuList(java.util.List<java.util.List> donne) {
        System.out.println("\n le contenue de la liste est:\n");
        for (int i = 0; i < donne.size(); i++) {
            for (int j = 0; j < donne.get(i).size(); j++) {
                System.out.print((donne.get(i)).get(j) + "\t\t");
            }
            System.out.println("\n");
        }
    }
    */
    /**
     * perme de verrifié que la connection à la BD es OK
     * @return true si la connection es OK
     */
    /*
    public boolean connectionOK(){
        if(connexionBD==null) return false;
        return true;
    }
    */


    
    
}
