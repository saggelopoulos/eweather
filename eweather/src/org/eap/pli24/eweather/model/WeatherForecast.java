package org.eap.pli24.eweather.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */

@Entity
@Table(name = "WEATHER_FORECAST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeatherForecast.findAll", query = "SELECT w FROM WeatherForecast w")
    , @NamedQuery(name = "WeatherForecast.findByDatetime", query = "SELECT w FROM WeatherForecast w WHERE w.weatherForecastPK.datetime = :datetime")
    , @NamedQuery(name = "WeatherForecast.findByCityId", query = "SELECT w FROM WeatherForecast w WHERE w.weatherForecastPK.cityId = :cityId")
    , @NamedQuery(name = "WeatherForecast.findByTemprature", query = "SELECT w FROM WeatherForecast w WHERE w.temprature = :temprature")
    , @NamedQuery(name = "WeatherForecast.findByClounds", query = "SELECT w FROM WeatherForecast w WHERE w.clounds = :clounds")
    , @NamedQuery(name = "WeatherForecast.findByWindSpeed", query = "SELECT w FROM WeatherForecast w WHERE w.windSpeed = :windSpeed")
    , @NamedQuery(name = "WeatherForecast.findByRain", query = "SELECT w FROM WeatherForecast w WHERE w.rain = :rain")
    , @NamedQuery(name = "WeatherForecast.findBySnow", query = "SELECT w FROM WeatherForecast w WHERE w.snow = :snow")
    //Προσθήκη απο την ομαδα 
    , @NamedQuery(name = "WeatherForecast.findDateRange", query = "SELECT w FROM WeatherForecast w WHERE w.weatherForecastPK.datetime BETWEEN :startDate AND :endDate  and w.weatherForecastPK.cityId = :cityId")
    , @NamedQuery(name = "WeatherForecast.findByPkey", query = "SELECT w FROM WeatherForecast w WHERE w.weatherForecastPK.datetime = :datetime and w.weatherForecastPK.cityId = :cityId")
    , @NamedQuery(name = "WeatherForecast.findByDateList", query = "SELECT w FROM WeatherForecast w WHERE w.weatherForecastPK.datetime in :datetimes and w.weatherForecastPK.cityId = :cityId")
    , @NamedQuery(name = "WeatherForecast.findMinMax", query = "SELECT w.city, MAX(w.temprature), MIN(w.temprature) FROM WeatherForecast w GROUP BY w.city" )
})
public class WeatherForecast implements Serializable {

    @Size(max = 200)
    @Column(name = "ICON")
    private String icon;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WeatherForecastPK weatherForecastPK;
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
    @ManyToOne(optional = false)
    private City city;
    @JoinColumn(name = "CONDITION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Condition conditionId;

    public WeatherForecast() {
    }

    public WeatherForecast(WeatherForecastPK weatherForecastPK) {
        this.weatherForecastPK = weatherForecastPK;
    }

    public WeatherForecast(Date datetime, int cityId) {
        this.weatherForecastPK = new WeatherForecastPK(datetime, cityId);
    }

    public WeatherForecastPK getWeatherForecastPK() {
        return weatherForecastPK;
    }

    public void setWeatherForecastPK(WeatherForecastPK weatherForecastPK) {
        this.weatherForecastPK = weatherForecastPK;
    }

    public BigDecimal getTemprature() {
        return temprature;
    }

    public void setTemprature(BigDecimal temprature) {
        this.temprature = temprature;
    }

    public BigDecimal getClounds() {
        return clounds;
    }

    public void setClounds(BigDecimal clounds) {
        this.clounds = clounds;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public BigDecimal getRain() {
        return rain;
    }

    public void setRain(BigDecimal rain) {
        this.rain = rain;
    }

    public BigDecimal getSnow() {
        return snow;
    }

    public void setSnow(BigDecimal snow) {
        this.snow = snow;
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
        this.conditionId = conditionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherForecastPK != null ? weatherForecastPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherForecast)) {
            return false;
        }
        WeatherForecast other = (WeatherForecast) object;
        if ((this.weatherForecastPK == null && other.weatherForecastPK != null) || (this.weatherForecastPK != null && !this.weatherForecastPK.equals(other.weatherForecastPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eap.pli24.eweather.model.WeatherForecast[ weatherForecastPK=" + weatherForecastPK + " ]";
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }
}
