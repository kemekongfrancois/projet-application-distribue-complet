/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementbean;

import entite.Compte;
import entite.Cours;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.ManageBean;

/**
 *
 * @author KEF10
 */
@Named(value = "detailCompteMBean")
@ViewScoped
public class DetailCompteMBean implements Serializable{

    @EJB
    private ManageBean manageBean;

    /**
     * Creates a new instance of DetailCompteMBean
     */
    public DetailCompteMBean() {
    }
    
     private int idCompte;  
  private Compte compte;  
  
    /**
     * Action handler - renvoie vers la page du supAdmin
     */
    public String retourVerSupAdmin() {
        return "PageSuperAdmin?faces-redirect=true&amp";
    }
    
    /**
     * Action handler - met à jour la base de données en fonction du client
     * passé en paramètres, et renvoie vers la page qui affiche la liste des
     * clients.
     */
    public String update() {
        compte = manageBean.update(compte);
        
        return retourVerSupAdmin();
    }
    
/**
 * permet de mettre à jour le chapm compte appartir de l'id du compte
 */
  public void loadCompte() {
        this.compte = manageBean.getCompteById(idCompte);
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
  
    /**
     * retourne la liste des cour du compte
     * @return 
     */
    public Collection<Cours> listCourCompte(){
        return compte.getCoursCollection();
    }
    
  
  
   

}
