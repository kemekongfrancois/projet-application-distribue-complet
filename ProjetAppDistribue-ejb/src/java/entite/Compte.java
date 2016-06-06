/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KEF10
 */
@Entity
@Table(name = "compte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c"),
    @NamedQuery(name = "Compte.findAllExcepSupAdmin", query = "SELECT c FROM Compte c WHERE c.typecompte<>\"supadmin\""),
    @NamedQuery(name = "Compte.findByIdcompte", query = "SELECT c FROM Compte c WHERE c.idcompte = :idcompte"),
    @NamedQuery(name = "Compte.findByLogin", query = "SELECT c FROM Compte c WHERE c.login = :login"),
    @NamedQuery(name = "Compte.findByPass", query = "SELECT c FROM Compte c WHERE c.pass = :pass"),
    @NamedQuery(name = "Compte.findByLoginPass", query = "SELECT c FROM Compte c WHERE c.login = :login AND c.pass = :pass"),
    @NamedQuery(name = "Compte.findByActiver", query = "SELECT c FROM Compte c WHERE c.activer = :activer"),
    @NamedQuery(name = "Compte.findByNom", query = "SELECT c FROM Compte c WHERE c.nom = :nom"),
    @NamedQuery(name = "Compte.findByPrenom", query = "SELECT c FROM Compte c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Compte.findByTypecompte", query = "SELECT c FROM Compte c WHERE c.typecompte = :typecompte")})
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCOMPTE")
    private Integer idcompte;
    @Size(max = 32)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 32)
    @Column(name = "PASS")
    private String pass;
    @Column(name = "ACTIVER")
    private Boolean activer;
    @Size(max = 32)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 32)
    @Column(name = "PRENOM")
    private String prenom;
    @Size(max = 10)
    @Column(name = "TYPECOMPTE")
    private String typecompte;
    @ManyToMany(mappedBy = "compteCollection")
    private Collection<Cours> coursCollection;

    public Compte() {
    }

    public Compte(Integer idcompte) {
        this.idcompte = idcompte;
    }

    public Integer getIdcompte() {
        return idcompte;
    }

    public void setIdcompte(Integer idcompte) {
        this.idcompte = idcompte;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getActiver() {
        return activer;
    }

    public void setActiver(Boolean activer) {
        this.activer = activer;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypecompte() {
        return typecompte;
    }

    public void setTypecompte(String typecompte) {
        this.typecompte = typecompte;
    }

    @XmlTransient
    public Collection<Cours> getCoursCollection() {
        return coursCollection;
    }

    public void setCoursCollection(Collection<Cours> coursCollection) {
        this.coursCollection = coursCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompte != null ? idcompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.idcompte == null && other.idcompte != null) || (this.idcompte != null && !this.idcompte.equals(other.idcompte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entite.Compte[ idcompte=" + idcompte + " ]";
    }
    
}
