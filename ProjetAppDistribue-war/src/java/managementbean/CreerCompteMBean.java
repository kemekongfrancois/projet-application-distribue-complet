/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementbean;

import beans.CoursBeans;
import entite.Compte;
import entite.Cours;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.ManageBean;

/**
 *
 * @author KEF10
 */
@Named(value = "creerCompteMBean")
@ViewScoped
public class CreerCompteMBean implements Serializable {

    private Compte compte;
    private List<CoursBeans> listCour = new ArrayList<>();
    @EJB
    private ManageBean manageBean;

    public CreerCompteMBean() {
        compte = new Compte();
    }

    public List<CoursBeans> getAllCour() {
        if (listCour.isEmpty()) {
            Collection<Cours> lisCourBD = manageBean.getAllCour();
            for (Cours cour : lisCourBD) {
                listCour.add(new CoursBeans(cour));
            }
        }

        return listCour;
    }

    /**
     * permmet d'affiche le message pris en paramettre
     *
     * @param msg
     */
    public void afficheMsg(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public String enregistrer() {
        String page = "accueil";
        String msg = "le compte à été créer vous pouvez vous connecter ";
        compte.setCoursCollection(new ArrayList<>());
        for (CoursBeans courB : listCour) {
            if (courB.getChoix()) {//on construi la liste des cours choisis
                //System.out.println(courB.getNomcour());
                compte.getCoursCollection().add(courB.toCours());
            }
        }
        if (!manageBean.creerCompteEtudiant(compte)) {//on enregistre le compte et les cours choisi

            page = "creerCompte";
            msg = "probléme consulter le server ";

        }
        FacesMessage message = new FacesMessage(msg);
        FacesContext.getCurrentInstance().addMessage(null, message);

        return page;
    }

    public String getAccueil() {
        //compte = null;//on détruit le compte connecter affin que le bouton précedent ne marche plus
        return "accueil?faces-redirect=true&amp";
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

}
