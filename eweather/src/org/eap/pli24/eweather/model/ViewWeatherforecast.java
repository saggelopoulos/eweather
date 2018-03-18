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
 *
 * @author saggelopoulos
 */
@Entity
@Table(name = "VIEW_WEATHERFORECAST")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "ViewWeatherforecast.findAll", query = "SELECT v FROM ViewWeatherforecast v")
    , @NamedQuery(name = "ViewWeatherforecast.findByName", query = "SELECT v FROM ViewWeatherforecast v WHERE v.name = :name")
    , @NamedQuery(name = "ViewWeatherforecast.findByMaxTemprature", query = "SELECT v FROM ViewWeatherforecast v WHERE v.maxTemprature = :maxTemprature")
    , @NamedQuery(name = "ViewWeatherforecast.findByMinTemprature", query = "SELECT v FROM ViewWeatherforecast v WHERE v.minTemprature = :minTemprature")
    , @NamedQuery(name = "ViewWeatherforecast.findByAvgTemprature", query = "SELECT v FROM ViewWeatherforecast v WHERE v.avgTemprature = :avgTemprature")
})
public class ViewWeatherforecast implements Serializable
{

    @Basic(optional = false)
    @NotNull
    @Column(name = "CITY_ID")
    private int cityId;

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MAX_TEMPRATURE")
    private BigDecimal maxTemprature;
    @Column(name = "MIN_TEMPRATURE")
    private BigDecimal minTemprature;
    @Column(name = "AVG_TEMPRATURE")
    private BigDecimal avgTemprature;
    @Id
    private Long id;

    public ViewWeatherforecast()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getMaxTemprature()
    {
        return maxTemprature;
    }

    public void setMaxTemprature(BigDecimal maxTemprature)
    {
        this.maxTemprature = maxTemprature;
    }

    public BigDecimal getMinTemprature()
    {
        return minTemprature;
    }

    public void setMinTemprature(BigDecimal minTemprature)
    {
        this.minTemprature = minTemprature;
    }

    public BigDecimal getAvgTemprature()
    {
        return avgTemprature;
    }

    public void setAvgTemprature(BigDecimal avgTemprature)
    {
        this.avgTemprature = avgTemprature;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }
    
}
