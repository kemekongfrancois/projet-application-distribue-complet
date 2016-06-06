/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entite.Cours;

/**
 *
 * @author KEF10
 */
public class CoursBeans extends Cours{
    private Boolean choix = false;//cette variable vas permettre de connaitre si un cour es choisi ou pas

    public CoursBeans() {
    }
    
    public CoursBeans(Cours cour) {
        this.setIdcour(cour.getIdcour());
        this.setLibelecour(cour.getLibelecour());
        this.setCompteCollection(cour.getCompteCollection());
        this.setNomcour(cour.getNomcour());
    }

    public Boolean getChoix() {
        return choix;
    }

    public void setChoix(Boolean choix) {
        this.choix = choix;
    }
    
    public String getMsg(){
        String message = getNomcour();
        return  message +(choix ? " (cocher)" : " (décocher)");
    }
    
    public Cours toCours(){
        return new Cours(this.getIdcour());
        
    } 
    
}
