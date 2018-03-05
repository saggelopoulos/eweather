/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aggelopoulos
 */
@Entity
@Table(name = "WEATHER_ACTUAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeatherActual.findAll", query = "SELECT w FROM WeatherActual w")
    , @NamedQuery(name = "WeatherActual.findByCityId", query = "SELECT w FROM WeatherActual w WHERE w.cityId = :cityId")
    , @NamedQuery(name = "WeatherActual.findByTemprature", query = "SELECT w FROM WeatherActual w WHERE w.temprature = :temprature")
    , @NamedQuery(name = "WeatherActual.findByClounds", query = "SELECT w FROM WeatherActual w WHERE w.clounds = :clounds")
    , @NamedQuery(name = "WeatherActual.findByWindSpeed", query = "SELECT w FROM WeatherActual w WHERE w.windSpeed = :windSpeed")
    , @NamedQuery(name = "WeatherActual.findByRain", query = "SELECT w FROM WeatherActual w WHERE w.rain = :rain")
    , @NamedQuery(name = "WeatherActual.findBySnow", query = "SELECT w FROM WeatherActual w WHERE w.snow = :snow")
        //SA : manual added
    , @NamedQuery(name = "WeatherActual.findByCityList", query = "SELECT w FROM WeatherActual w WHERE w.cityId in :cityId")
    })
public class WeatherActual implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CITY_ID")
    private Integer cityId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TEMPRATURE")
    private BigDecimal temprature;
    @Column(name = "CLOUNDS")
    private BigDecimal clounds;
    @Column(name = "WIND_SPEED")
    private BigDecimal windSpeed;
    @Column(name = "RAIN")
    private BigDecimal rain;
    @Column(name = "SNOW")
    private BigDecimal snow;
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private City city;
    @JoinColumn(name = "CONDITION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Condition conditionId;

    public WeatherActual() {
    }

    public WeatherActual(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        Integer oldCityId = this.cityId;
        this.cityId = cityId;
        changeSupport.firePropertyChange("cityId", oldCityId, cityId);
    }

    public BigDecimal getTemprature() {
        return temprature;
    }

    public void setTemprature(BigDecimal temprature) {
        BigDecimal oldTemprature = this.temprature;
        this.temprature = temprature;
        changeSupport.firePropertyChange("temprature", oldTemprature, temprature);
    }

    public BigDecimal getClounds() {
        return clounds;
    }

    public void setClounds(BigDecimal clounds) {
        BigDecimal oldClounds = this.clounds;
        this.clounds = clounds;
        changeSupport.firePropertyChange("clounds", oldClounds, clounds);
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        BigDecimal oldWindSpeed = this.windSpeed;
        this.windSpeed = windSpeed;
        changeSupport.firePropertyChange("windSpeed", oldWindSpeed, windSpeed);
    }

    public BigDecimal getRain() {
        return rain;
    }

    public void setRain(BigDecimal rain) {
        BigDecimal oldRain = this.rain;
        this.rain = rain;
        changeSupport.firePropertyChange("rain", oldRain, rain);
    }

    public BigDecimal getSnow() {
        return snow;
    }

    public void setSnow(BigDecimal snow) {
        BigDecimal oldSnow = this.snow;
        this.snow = snow;
        changeSupport.firePropertyChange("snow", oldSnow, snow);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Condition getConditionId() {
        return conditionId;
    }

    public void setConditionId(Condition conditionId) {
        Condition oldConditionId = this.conditionId;
        this.conditionId = conditionId;
        changeSupport.firePropertyChange("conditionId", oldConditionId, conditionId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherActual)) {
            return false;
        }
        WeatherActual other = (WeatherActual) object;
        if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eap.pli24.eweather.model.WeatherActual[ cityId=" + cityId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
