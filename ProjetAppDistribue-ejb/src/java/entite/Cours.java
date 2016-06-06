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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "cours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cours.findAll", query = "SELECT c FROM Cours c"),
    @NamedQuery(name = "Cours.findByIdcour", query = "SELECT c FROM Cours c WHERE c.idcour = :idcour"),
    @NamedQuery(name = "Cours.findByNomcour", query = "SELECT c FROM Cours c WHERE c.nomcour = :nomcour"),
    @NamedQuery(name = "Cours.findByLibelecour", query = "SELECT c FROM Cours c WHERE c.libelecour = :libelecour")})
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCOUR")
    private Integer idcour;
    @Size(max = 32)
    @Column(name = "NOMCOUR")
    private String nomcour;
    @Size(max = 32)
    @Column(name = "LIBELECOUR")
    private String libelecour;
    @JoinTable(name = "inscrit", joinColumns = {
        @JoinColumn(name = "IDCOUR", referencedColumnName = "IDCOUR")}, inverseJoinColumns = {
        @JoinColumn(name = "IDCOMPTE", referencedColumnName = "IDCOMPTE")})
    @ManyToMany
    private Collection<Compte> compteCollection;

    public Cours() {
    }

    public Cours(Integer idcour) {
        this.idcour = idcour;
    }

    public Integer getIdcour() {
        return idcour;
    }

    public void setIdcour(Integer idcour) {
        this.idcour = idcour;
    }

    public String getNomcour() {
        return nomcour;
    }

    public void setNomcour(String nomcour) {
        this.nomcour = nomcour;
    }

    public String getLibelecour() {
        return libelecour;
    }

    public void setLibelecour(String libelecour) {
        this.libelecour = libelecour;
    }

    @XmlTransient
    public Collection<Compte> getCompteCollection() {
        return compteCollection;
    }

    public void setCompteCollection(Collection<Compte> compteCollection) {
        this.compteCollection = compteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcour != null ? idcour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cours)) {
            return false;
        }
        Cours other = (Cours) object;
        if ((this.idcour == null && other.idcour != null) || (this.idcour != null && !this.idcour.equals(other.idcour))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entite.Cours[ idcour=" + idcour + " ]";
    }
    
}
