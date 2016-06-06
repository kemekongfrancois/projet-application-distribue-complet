/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementbean;

import entite.Compte;
import entite.Cours;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.ManageBean;

/**
 *
 * @author KEF10
 */
@Named(value = "bean")
@SessionScoped
public class Bean implements Serializable {

    private Compte compte;
    private List<Compte> listCoursActif;
    private List<Compte> listCoursNonActif;

    @EJB
    private ManageBean manageBean;

    public Bean() {
    }

    public String getCompteIfExist(String login, String pass) {
        //String login = "kef1";
        //String pass = "0000";
        String page;
        Compte cpt = manageBean.getCompteLoginPass(login, pass);
        if (cpt == null) {
            FacesMessage message = new FacesMessage("Login ou mot de passe incorrect ");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);//ceci permet de dire que le message à affiché sera de type Erreur
            FacesContext.getCurrentInstance().addMessage(null, message);

            page = "accueil";
        } else {
            //FacesMessage message = new FacesMessage("connection OK");
            //FacesContext.getCurrentInstance().addMessage(null, message);

            //initialisation();
            compte = cpt;
            if(cpt.getTypecompte().equals("supAdmin")) page = "PageSuperAdmin?faces-redirect=true&amp";
            else page = "pageUtilisateur?faces-redirect=true&amp";
        }
        return page;
    }

    /**
     * permet d'initialisé les champs de la page d'accuiel
     */
    public void initialisation() {
        listCoursActif = new ArrayList<>();
        listCoursNonActif = new ArrayList<>();
        List<Compte> listCompte = manageBean.getAllCompteExceptSupadmin();
        for (Compte compte : listCompte) {
            if (compte.getActiver()) {
                listCoursActif.add(compte);
            } else {
                listCoursNonActif.add(compte);
            }
        }
    }
    
    /**
     * retourne la liste des cours de l'étudiant connecté
     * @return 
     */
    public Collection<Cours> getCoursEtudiant(){
        return compte.getCoursCollection();
    } 
    
    /**
     * enregistre les modification sur le client,
     */
    public void update() {
        compte = manageBean.update(compte);
        FacesMessage message = new FacesMessage("Enregistrement effectué");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        //return retourVerSupAdmin();
    }
    
    /**
     * cette fonction permet d'envoiyer un msg à la page des 2tudiant leur informant si leur compte et activé ou pas
     * @return 
     */
    public String getMessage(){
        if(compte.getActiver()) return "le compte es activé vous ne pouvais donc plus effectué des modification";
        else return "Vous pouver modifie vous information car le compte n'es pas encore verroillé";
    }
    
    
    public String getDeconnection(){
        //compte = null;//on détruit le compte connecter affin que le bouton précedent ne marche plus
        return "accueil?faces-redirect=true&amp";
    }
    
    public String showDetails(int idCompte) {  
        return "DetailsCompte?idCompte=" + idCompte;  
    } 

    public List<Compte> getListCoursNonActif() {
        return listCoursNonActif;
    }

    public void setListCoursNonActif(List<Compte> listCoursNonActif) {
        this.listCoursNonActif = listCoursNonActif;
    }

    public List<Compte> getListCoursActif() {
        return listCoursActif;
    }

    public void setListCoursActif(List<Compte> listCoursActif) {
        this.listCoursActif = listCoursActif;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

}
