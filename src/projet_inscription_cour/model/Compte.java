package projet_inscription_cour.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet_inscription_cour.util.BaseDeDonne;
import ws.ManageWS;
import ws.ManageWS_Service;

public class Compte {

    static ManageWS ws = new ManageWS_Service().getManageWSPort();
    
    private final IntegerProperty idCompte;
    private final StringProperty login;
    private final StringProperty pass;
    private final BooleanProperty activer ;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty typeCompte;
    /*
     private final IntegerProperty idCompte = new SimpleIntegerProperty();
     private final StringProperty login = new SimpleStringProperty();
     private final StringProperty pass = new SimpleStringProperty();
     private final BooleanProperty activer = new SimpleBooleanProperty();
     private final StringProperty nom = new SimpleStringProperty();
     private final StringProperty prenom = new SimpleStringProperty();
     private final StringProperty typeCompte = new SimpleStringProperty();
     */

    /**
     * Default constructor.
     */
    public Compte() {
        this(0, null, null, false, null, null, null);
    }
    
     public Compte(ws.Compte comptWS) {
        this.idCompte = new SimpleIntegerProperty(comptWS.getIdcompte());
        this.login = new SimpleStringProperty(comptWS.getLogin());
        this.pass = new SimpleStringProperty(comptWS.getPass());
        this.activer = new SimpleBooleanProperty(comptWS.isActiver());
        this.nom = new SimpleStringProperty(comptWS.getNom());
        this.prenom = new SimpleStringProperty(comptWS.getPrenom());
        this.typeCompte = new SimpleStringProperty(comptWS.getTypecompte());
    }

    public Compte(Integer idCompte, String login, String pass, boolean activer, String nom, String prenom, String typeCompte) {
        this.idCompte = new SimpleIntegerProperty(idCompte);
        this.login = new SimpleStringProperty(login);
        this.pass = new SimpleStringProperty(pass);
        this.activer = new SimpleBooleanProperty(activer);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.typeCompte = new SimpleStringProperty(typeCompte);

    }
    
    public Compte(String login, String pass, String nom, String prenom) {
        this.idCompte = new SimpleIntegerProperty(-1);
        this.login = new SimpleStringProperty(login);
        this.pass = new SimpleStringProperty(pass);
        this.activer = new SimpleBooleanProperty(false);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.typeCompte = new SimpleStringProperty("etudiant");

    }
    
    public Compte(List<Object> list) {
        int i=0;
        this.idCompte = new SimpleIntegerProperty(new Integer(list.get(i++).toString()));
        this.login = new SimpleStringProperty(list.get(i++).toString());
        this.pass = new SimpleStringProperty(list.get(i++).toString());
        this.activer = new SimpleBooleanProperty(new Boolean(list.get(i++).toString()));
        this.nom = new SimpleStringProperty(list.get(i++).toString());
        this.prenom = new SimpleStringProperty(list.get(i++).toString());
        this.typeCompte = new SimpleStringProperty(list.get(i++).toString());

    }
    
    /**
     * verifi que le compte courant es un compte Super administrateur
     * @return true si c'est un compte super administrateur
     */
    public boolean isSupAdmin(){
        return getTypeCompte().equals("supAdmin");
    }
    
    public void afficheCompte(){
        System.out.println("\n------------- le compte est -------------\n");
        System.out.println("idCompte = "+getIdCompte());
        System.out.println("login = "+getLogin());
        System.out.println("pass = "+getPass());
        System.out.println("activer = "+isActiver());
        System.out.println("nom = "+getNom());
        System.out.println("prenom = "+getPrenom());
        System.out.println("typeCompte = "+getTypeCompte());
    }
    
    /**
     * retourne un compte de type provenant du web service "sans le champ ID"
     * @return 
     */
    public ws.Compte getCompteWS(){
        ws.Compte compteBD = new ws.Compte();
        
        compteBD.setNom(this.getNom());
        compteBD.setPass(this.getPass());
        compteBD.setActiver(this.isActiver());
        compteBD.setLogin(this.getLogin());
        compteBD.setPrenom(this.getPrenom());
        compteBD.setTypecompte(this.getTypeCompte());
        return compteBD;
    }
    
    /**
     * cette fonction creer le compte courant dans la BD
     * puis met à jour le champs idCompte
     */
    public void creerCompteBD(){
        /*
        String requete = "INSERT INTO `bd_tp_interface_java`.`compte` (`LOGIN`, `PASS`, `ACTIVER`, `NOM`, `PRENOM`, `TYPECOMPTE`) "
                + "VALUES ('" + getLogin() + "', '" + getPass() + "', '0', '" + getNom() + "', '" + getPrenom() + "', 'etudiant');";
        BaseDeDonne bd = new BaseDeDonne();
        
        if (bd.requeteInsertUpdate(requete)) {
      */
        
        if (ws.creerCompteEtudiant(getCompteWS())) {
            setIdCompte(isExistCompte());//mise à jour du champs idCOmpte
        } else {// echec de l'exécution de la requete
            setIdCompte(-2);//on met l'id à -2 si il ya eu echec lors de l'exécution de la requete
        }
    }
    
    /**
     * cette fonction fait la mise à jour du compte courant dans la BD
     */
    public boolean updateCompteBD(){
        //String requete = "UPDATE `bd_tp_interface_java`.`compte` SET `NOM`='" + getNom() + "', `PRENOM`='" + getPrenom() + "' WHERE `IDCOMPTE`='" + getIdCompte() + "';";
        //BaseDeDonne bd = new BaseDeDonne();
        ws.Compte cpt = getCompteWS();
        cpt.setIdcompte(getIdCompte());//on fait cette opération car la fonction getCompteWS() ne contient pas le champ IDCompte
        try{
            ws.update(cpt);
            return true;
        }catch(Exception ex){
            System.out.println("impossible d'enregistrer les information"+ex);
        return false;
    }
        
        
         
    }
    
    /**
     * cette fonction verrifie si le Compte courant existe déja dans la base de donné
     * @return -1 si le compte n'existe pas et le numero du compte s'il existe 
     */
    public Integer isExistCompte(){
        /*
        String requete = "SELECT IDCOMPTE FROM bd_tp_interface_java.compte where LOGIN=\""+getLogin()+"\";";
        BaseDeDonne bd = new BaseDeDonne();
        List<List> resultat = bd.resultaRequete(requete);
        */
        
        ws.Compte comptews = ws.findCompte(getLogin());
        if(comptews==null) return -1;
        else return comptews.getIdcompte();
    }
    
    /**
     * cette fonction permet d'incrire l'étudiant aux cours donc la liste est prisent en parametre
     * @param lisCour 
     */
    public void inscritEtudiantAuxCour(ObservableList<Cour> lisCour){
        /*
        String requete = "";
        BaseDeDonne bd = new BaseDeDonne();
        for (int i = 0; i < lisCour.size(); i++) {
            if (lisCour.get(i).getChoisi()) {
                requete = "INSERT INTO `bd_tp_interface_java`.`inscrit` (`IDCOUR`, `IDCOMPTE`) "
                        + "VALUES ('" + lisCour.get(i).getIdCour() + "', '" + getIdCompte() + "');\n";
                bd.requeteInsertUpdate(requete);//insertion de données
                //System.out.println(requete);
            }
        }
        */
        ws.Compte comptews = getCompteWS();
        List<ws.Cours> lesCour = new ArrayList<>();
        for(Cour cour:lisCour) {
            if(cour.getChoisi()){
                lesCour.add(cour.getCOurWS());
            }
        }
        ws.crerCompteAvecCour(comptews, lesCour);
    }
    
    /**
     * cette fonction permet d'activé(mettre le champ activé à vrai) les compte qui se trouve dans la liste Observable
     * @param lisCompte 
     */
    public static void ActiverCompte(ObservableList<Compte> lisCompte) {
        /*
        String requete = "";
        BaseDeDonne bd = new BaseDeDonne();
        int j = 0;
        for (int i = 0; i < lisCompte.size(); i++) {
            j = 0;
            if (lisCompte.get(i).isActiver()) j = 1;
            requete = "UPDATE `bd_tp_interface_java`.`compte` SET `ACTIVER`='" + j + "' WHERE `IDCOMPTE`='" + lisCompte.get(i).getIdCompte() + "';";
            bd.requeteInsertUpdate(requete);//insertion de données
            //System.out.println(requete);
        }
        */
        ws.Compte compteWS ;
        for(Compte compte : lisCompte){
            compteWS = compte.getCompteWS();
            compteWS.setIdcompte(compte.getIdCompte());
            ws.update(compteWS);
        }
    }
    
    /**
     * cette fonction permet de désactivé(mettre le champ activé à faux) les compte qui se trouve dans la liste Observable
     * @param lisCompte 
     */
    public static void desactiverCompte(ObservableList<Compte> lisCompte) {
        /*
        String requete = "";
        BaseDeDonne bd = new BaseDeDonne();
        int j ;
        for (int i = 0; i < lisCompte.size(); i++) {
            j = 1;
            if (lisCompte.get(i).isActiver()){
                j = 0;
                //lisCompte.get(i).setActiver(true);
            }
            requete = "UPDATE `bd_tp_interface_java`.`compte` SET `ACTIVER`='" + j + "' WHERE `IDCOMPTE`='" + lisCompte.get(i).getIdCompte() + "';";
            bd.requeteInsertUpdate(requete);//insertion de données
            //System.out.println(requete);
        }
        */
        ActiverCompte(lisCompte);
    }
    
    /**
     * retourne une liste observable qui represent l'ensemble des comptes en attente de validation
     * @return 
     */
    public static ListProperty<Compte> listCompteAttentValidation(){
        
        /*
        ListProperty<Compte> lisObservableDeCompte = new SimpleListProperty(FXCollections.observableArrayList());
        String nomCompte;
        String prenomCompte; 
        BaseDeDonne bd = new BaseDeDonne();
        String requete = "select * from compte where ACTIVER<>1;";
        List<List> compteBD = bd.resultaRequete(requete);
        
        
        
        for (int i = 1; i < compteBD.size(); i++) {//on commence à 1 car à 0 on à l'entête

            int j=0;
            Compte compte = new Compte(new Integer((compteBD.get(i)).get(j++).toString()), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString(), new Boolean((compteBD.get(i)).get(j++).toString()), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString());
            compte.afficheCompte();
            lisObservableDeCompte.add(compte);
            //System.out.println(nomCour+"\n"+libeleCour+"\n");
        }
        */
        ListProperty<Compte> lisObservableDeCompte = new SimpleListProperty(FXCollections.observableArrayList());
        List<ws.Compte> allCompte = ws.getAllCompteExceptSupadmin();
        for(ws.Compte compte : allCompte){
            if(!compte.isActiver()) {
                lisObservableDeCompte.add(new Compte(compte));
            }
        }
        return lisObservableDeCompte;
    }
    
    /**
     * retourne une liste observable qui represent l'ensemble des comptes valider
     * @return 
     */
    public static ListProperty<Compte> listCompterValider(){
        /*
        BaseDeDonne bd = new BaseDeDonne();
        String requete = "select * from compte where ACTIVER=1 and TYPECOMPTE<>\"supadmin\";";
        List<List> compteBD = bd.resultaRequete(requete);
        String nomCompte;
        String prenomCompte; 
        
        ListProperty<Compte> lisObservableDeCompte = new SimpleListProperty(FXCollections.observableArrayList());
        for (int i = 1; i < compteBD.size(); i++) {//on commence à 1 car à 0 on à l'entête

            int j=0;
            Compte compte = new Compte(new Integer((compteBD.get(i)).get(j++).toString()), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString(), new Boolean((compteBD.get(i)).get(j++).toString()), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString(), (compteBD.get(i)).get(j++).toString());
            compte.afficheCompte();
            lisObservableDeCompte.add(compte);
            //System.out.println(nomCour+"\n"+libeleCour+"\n");
        }
        return lisObservableDeCompte;
*/
        ListProperty<Compte> lisObservableDeCompte = new SimpleListProperty(FXCollections.observableArrayList());
        List<ws.Compte> allCompte = ws.getAllCompteExceptSupadmin();
        for(ws.Compte compte : allCompte){
            if(compte.isActiver()) {
                lisObservableDeCompte.add(new Compte(compte));
            }
        }
        return lisObservableDeCompte;
    }

    //getter et setter
    public int getIdCompte() {
        return idCompte.get();
    }

    public void setIdCompte(int value) {
        idCompte.set(value);
    }

    public IntegerProperty idCompteProperty() {
        return idCompte;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String value) {
        login.set(value);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String value) {
        pass.set(value);
    }

    public StringProperty passProperty() {
        return pass;
    }

    public boolean isActiver() {
        return activer.get();
    }

    public void setActiver(boolean value) {
        activer.set(value);
    }

    public BooleanProperty activerProperty() {
        return activer;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String value) {
        nom.set(value);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String value) {
        prenom.set(value);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getTypeCompte() {
        return typeCompte.get();
    }

    public void setTypeCompte(String value) {
        typeCompte.set(value);
    }

    public StringProperty typeCompteProperty() {
        return typeCompte;
    }

}
