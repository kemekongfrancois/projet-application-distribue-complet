/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import entite.Compte;
import entite.Cours;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import session.ManageBean;

/**
 *
 * @author KEF10
 */
@WebService(serviceName = "ManageWS")
public class ManageWS {

    @EJB
    private ManageBean manageBean;

    @WebMethod
    public Compte findCompte(String login) {
        try {
            return manageBean.findCompte(login);
        } catch (Exception ex) {
            Logger.getLogger(ManageWS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @WebMethod
    public void persist(Object object) {
        manageBean.persist(object);
    }

    @WebMethod
    public Compte getCompteLoginPass(@WebParam(name = "login")String login, @WebParam(name = "pass")String pass) {
        return manageBean.getCompteLoginPass(login, pass);
    }

    @WebMethod
    public List<Compte> getAllCompteExceptSupadmin() {
        return manageBean.getAllCompteExceptSupadmin();
    }

    @WebMethod
    public Compte getCompteById(int idCompte) {
        return manageBean.getCompteById(idCompte);
    }

    @WebMethod
    public Compte update(Compte compte) {
        return manageBean.update(compte);
    }

    @WebMethod
    public boolean creerCompteEtudiant(Compte compte) {
        return manageBean.creerCompteEtudiant(compte);
    }

    @WebMethod
    public boolean creerCour(@WebParam(name = "nom")String nom, @WebParam(name = "libele")String libele) {
        return manageBean.creerCour(nom, libele);
    }

    @WebMethod
    public Collection<Cours> getAllCour() {
        return manageBean.getAllCour();
    }
    
    @WebMethod
    public Collection<Cours> getCourDuCompte(Integer idCompte) {
        return manageBean.getCompteById(idCompte).getCoursCollection();
    }
    
    /**
     * retourne une liste qui represent l'ensemble des cours au quelle le compte n'es pas inscrit
     * @return 
     */
    @WebMethod
    public Collection<Cours> getCourDuCompteNonInscri(Integer idCompte) {
        Collection<Cours> touslesCour = manageBean.getAllCour();
        
        touslesCour.removeAll(manageBean.getCompteById(idCompte).getCoursCollection());
        return touslesCour;
    }
    
    @WebMethod
    /**
     * cette fonction créer un compte avec les cours que le compte à choisi
     */
    public boolean crerCompteAvecCour(@WebParam(name = "compte")Compte compte,@WebParam(name = "listCoursChoisi") List<Cours> listCourChoisi) {
        compte.setCoursCollection(listCourChoisi);

        return manageBean.creerCompteEtudiant(compte);
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
