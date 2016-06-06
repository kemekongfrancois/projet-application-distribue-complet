/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entite.Compte;
import entite.Cours;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author KEF10
 */
@Stateless
@LocalBean
public class ManageBean {

    @PersistenceContext(unitName = "ProjetAppDistribue-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public Compte getCompteLoginPass(String login, String pass) {
        /*
        Query query = em.createNamedQuery("Compte.findByLoginPass");
        query.setParameter("login", login);
        query.setParameter("pass", pass);
        */
        Query query = em.createNamedQuery("Compte.findByLoginPass");
        query.setParameter("pass", pass);
        query.setParameter("login", login);
        
        if(query.getResultList().size()<1) {
            System.out.println("\n******** ce compte es inexistant **********\n "
                    + "login= " + login + "\n pass= " + pass + 
                    "\n****************************\n");
            System.out.println(query);
            return null;
        }
        else {
            Compte cpt = (Compte)query.getResultList().get(0);
            System.out.println("\n*********************************************************\n "
                    + "le compte suivant c'est connecté\n"+cpt.toString()+
                    "\n**********************************");
            return cpt;
        }

        //return (Compte)query.getSingleResult();
        //return (Compte)query.getResultList().get(1);
        //return em.find(Compte.class, 1);
    }
    
    /**
     * retourne la liste des comptes excepte le compte du super administrateur
     * @return 
     */
    public List<Compte> getAllCompteExceptSupadmin(){
        Query query = em.createNamedQuery("Compte.findAllExcepSupAdmin");
        return (List<Compte>) query.getResultList();
    }
    
    public Compte getCompteById(int idCompte){
        return em.find(Compte.class, idCompte);
    }
    
    public Compte update(Compte compte) {
        return em.merge(compte);
    }
    
    /**
     * permete de crée un compte de type étudiant qui serat non actif
     * @param compte
     * @return 
     */
    public boolean creerCompteEtudiant(Compte compte){
        compte.setTypecompte("etudiant");
        compte.setActiver(Boolean.FALSE);
        try{
            persist(compte);
            return true;
        }catch(Exception ex){
            return false;
        }
        
    }
    /**
     * cette fonction permet de créer un nouveau cour dans la BD
     * @param nom
     * @param libele
     * @return 
     */
    public boolean creerCour(String nom, String libele){
        Cours cour = new Cours();
        cour.setLibelecour(libele);
        cour.setNomcour(nom);
        try {
            persist(cour);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Collection<Cours> getAllCour(){
        Query query = em.createNamedQuery("Cours.findAll");
        return  query.getResultList();
    }
    
    /**
     * cette fonction retourne le premier compte donc le login es pris en paramettre 
     * l'exception généré es renvoyer à l'appelant
     * @param login
     * @return
     * @throws Exception 
     */
    public Compte findCompte(String login) throws Exception{
        Query query = em.createNamedQuery("Compte.findByLogin");
        query.setParameter("login", login);
        if(query.getResultList().size()>0) return (Compte) query.getResultList().get(0);
        else return null;
    }
}
