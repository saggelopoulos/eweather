/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 *
 */
@Embeddable
public class WeatherForecastPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Basic(optional = false)
    @Column(name = "CITY_ID")
    private int cityId;

    public WeatherForecastPK() {
    }

    public WeatherForecastPK(Date datetime, int cityId) {
        this.datetime = datetime;
        this.cityId = cityId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datetime != null ? datetime.hashCode() : 0);
        hash += (int) cityId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherForecastPK)) {
            return false;
        }
        WeatherForecastPK other = (WeatherForecastPK) object;
        if ((this.datetime == null && other.datetime != null) || (this.datetime != null && !this.datetime.equals(other.datetime))) {
            return false;
        }
        if (this.cityId != other.cityId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eap.pli24.eweather.model.WeatherForecastPK[ datetime=" + datetime + ", cityId=" + cityId + " ]";
    }
    
}
