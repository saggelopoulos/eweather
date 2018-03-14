/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 *
 */
@Entity
@Table(name = "CONDITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Condition.findAll", query = "SELECT c FROM Condition c")
    , @NamedQuery(name = "Condition.findById", query = "SELECT c FROM Condition c WHERE c.id = :id")
    , @NamedQuery(name = "Condition.findByDescription", query = "SELECT c FROM Condition c WHERE c.description = :description")})
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conditionId")
    private Collection<WeatherForecast> weatherForecastCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conditionId")
    private Collection<WeatherActual> weatherActualCollection;

    public Condition() {
    }

    public Condition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<WeatherForecast> getWeatherForecastCollection() {
        return weatherForecastCollection;
    }

    public void setWeatherForecastCollection(Collection<WeatherForecast> weatherForecastCollection) {
        this.weatherForecastCollection = weatherForecastCollection;
    }

    @XmlTransient
    public Collection<WeatherActual> getWeatherActualCollection() {
        return weatherActualCollection;
    }

    public void setWeatherActualCollection(Collection<WeatherActual> weatherActualCollection) {
        this.weatherActualCollection = weatherActualCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Condition)) {
            return false;
        }
        Condition other = (Condition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescription();
    }
    
}
