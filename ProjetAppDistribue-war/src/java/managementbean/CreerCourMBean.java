/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementbean;

import entite.Cours;
import java.io.Serializable;
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
@Named(value = "creerCourBean")
@ViewScoped
public class CreerCourMBean implements Serializable{

    @EJB
    private ManageBean manageBean;

    private String nom;
    private String libele;
    
    public CreerCourMBean() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }
    
    public String creerCour(){
        
        if(manageBean.creerCour(nom, libele)){
            return retourVerSupAdmin();
        }else{
            FacesMessage message = new FacesMessage("Impossible de creer ce cour contacter le server pour plus d'information ");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);//ceci permet de dire que le message à affiché sera de type Erreur
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "creerCour";
        }
        
    }
    
    /**
     * Action handler - renvoie vers la page du supAdmin
     */
    public String retourVerSupAdmin() {
        return "PageSuperAdmin?faces-redirect=true&amp";
    }
}
