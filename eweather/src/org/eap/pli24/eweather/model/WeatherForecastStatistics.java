package org.eap.pli24.eweather.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */

@Entity
@Table(name = "WEATHER_FORECAST_STATISTICS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeatherForecastStatistics.findAll", query = "SELECT w FROM WeatherForecastStatistics w")
    , @NamedQuery(name = "WeatherForecastStatistics.findByCityId", query = "SELECT w FROM WeatherForecastStatistics w WHERE w.cityId = :cityId")
    , @NamedQuery(name = "WeatherForecastStatistics.findByName", query = "SELECT w FROM WeatherForecastStatistics w WHERE w.name = :name")
    , @NamedQuery(name = "WeatherForecastStatistics.findByTempratureMin", query = "SELECT w FROM WeatherForecastStatistics w WHERE w.tempratureMin = :tempratureMin")
    , @NamedQuery(name = "WeatherForecastStatistics.findByTempratureMax", query = "SELECT w FROM WeatherForecastStatistics w WHERE w.tempratureMax = :tempratureMax")
    , @NamedQuery(name = "WeatherForecastStatistics.findByTempratureAvg", query = "SELECT w FROM WeatherForecastStatistics w WHERE w.tempratureAvg = :tempratureAvg")})
public class WeatherForecastStatistics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CITY_ID")
    @Id
    private int cityId;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TEMPRATURE_MIN")
    private BigDecimal tempratureMin;
    @Column(name = "TEMPRATURE_MAX")
    private BigDecimal tempratureMax;
    @Column(name = "TEMPRATURE_AVG")
    private BigDecimal tempratureAvg;

    public WeatherForecastStatistics() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTempratureMin() {
        return tempratureMin;
    }

    public void setTempratureMin(BigDecimal tempratureMin) {
        this.tempratureMin = tempratureMin;
    }

    public BigDecimal getTempratureMax() {
        return tempratureMax;
    }

    public void setTempratureMax(BigDecimal tempratureMax) {
        this.tempratureMax = tempratureMax;
    }

    public BigDecimal getTempratureAvg() {
        return tempratureAvg;
    }

    public void setTempratureAvg(BigDecimal tempratureAvg) {
        this.tempratureAvg = tempratureAvg;
    } 
}
