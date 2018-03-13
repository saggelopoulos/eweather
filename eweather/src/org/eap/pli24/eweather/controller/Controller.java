/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.controller;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eap.pli24.eweather.model.City;
import org.eap.pli24.eweather.model.Condition;
import org.eap.pli24.eweather.model.WeatherActual;
import org.eap.pli24.eweather.model.WeatherForecast;
import org.eap.pli24.eweather.wservice.OpenWeatherService;

/**
 * 
 *
 */
public class Controller 
{
    
    /**
     *  
     */
    private EntityManagerFactory  entityManagerFactory;
    
    /**
     * 
     */
    private EntityManager entityManager ;
    
    
    /**
     * 
     */
    private OpenWeatherService openWeatherService;
    
    /**
     * Class Constructor
     */
    public Controller()
    {
        entityManagerFactory =Persistence.createEntityManagerFactory("eweatherPU");        
        entityManager =entityManagerFactory.createEntityManager();
        openWeatherService = new OpenWeatherService();
    }
    /**
     * 
     */
    public void updateActualData()
    {
    	 List<WeatherActual> lt= openWeatherService.getActualWeatherData(getCityList());
         deleteWeatherActualAll();
         insertWeatherActual(lt);
    }
    
    public void updateForecastData()
    {
    	List<WeatherForecast> weatherForecasts = openWeatherService.getForecastWeatherData(getCityList());
        insertWeatherForecast(weatherForecasts);  
    }
        
    /**
     * 
     * @return
     */
    public List<City> getCityList()
    {
        Query qWa = entityManager.createNamedQuery("City.findAll");
        List<City> result = qWa.getResultList();
        return result;
    }
    
    /**
     * @param cityID
     * @return
     */
    public City getCity(int cityID)
    {
        Query qWa = entityManager.createNamedQuery("City.findById");
        qWa.setParameter("id", cityID);
        City rsl = (City) qWa.getSingleResult();
        return rsl;
    }
    
    
    /**
     * @param conditionID
     * @return
     */
    public Condition getCondition(int conditionID)
    {
        Query qWa = entityManager.createNamedQuery("Condition.findById");
        qWa.setParameter("id", conditionID);
        Condition condition = (Condition)qWa.getSingleResult();
        return condition;
    }

    /**
     * @return
     */
    public List<WeatherActual> getWeatherActualAll()
    {
        Query qWa = entityManager.createNamedQuery("WeatherActual.findAll");
         List result = qWa.getResultList(); 
        return result;
    }
    
    
    /**
     * @param cityParameter
     * @return
     */
    public List<WeatherActual> getWeatherActualByCity(List cityParameter)
    {
        Query qWa = entityManager.createNamedQuery("WeatherActual.findByCityList");
        qWa.setParameter("cityId", cityParameter);
        List result = qWa.getResultList(); 
        return result;
    }
    
    
    
    
    
    /**
     * 
     */
    public void deleteWeatherActualAll()
    {
        Query qWa = entityManager.createNamedQuery("WeatherActual.findAll");
        List <WeatherActual> result = qWa.getResultList(); 
        entityManager.getTransaction().begin();
        for(WeatherActual wa :result)
        {
            entityManager.remove(wa);   
        }
        entityManager.getTransaction().commit();
    }
    
    /**
     * @param dt
     */
    public void insertWeatherActual (List<WeatherActual> dt)
    {
        entityManager.getTransaction().begin();
        for (WeatherActual wa : dt)
        {
            wa.setCity(getCity(wa.getCityId()));
            wa.setConditionId(getCondition(wa.getConditionId().getId()));
            
            entityManager.persist(wa);
        }
        entityManager.getTransaction().commit();
    }
    
    /**
     * @param weatherForecasts
     */
    public void insertWeatherForecast(List<WeatherForecast> weatherForecasts)
    {
        entityManager.getTransaction().begin();
        for (WeatherForecast wf : weatherForecasts)
        {
            Query qw = entityManager.createNamedQuery("WeatherForecast.findByPkey");
            qw.setParameter("datetime", wf.getWeatherForecastPK().getDatetime());
            qw.setParameter("cityId", wf.getWeatherForecastPK().getCityId());
            
            try
            {
                Object rsl = qw.getSingleResult();
                if (rsl == null)
                {
                    entityManager.persist(wf);    
                }
            }
            catch (Exception e)
            {
                entityManager.persist(wf);
            }
          
        }
        entityManager.getTransaction().commit();   
    }
    
    public List<WeatherForecast> getWeatherForecastByDate(Date startDate, Date endDate , City city )
    {
        Query qw = entityManager.createNamedQuery("WeatherForecast.findDateRange");
        qw.setParameter("startDate",startDate);
        qw.setParameter("endDate",endDate);
        qw.setParameter("cityId", city.getId());
        List result = qw.getResultList(); 
        return result;
    }
    
    public List<WeatherForecast> getWeatherForecastByDateList(List<Date> dates , City city )
    {
        for (Date dt: dates)
        {
            System.out.println(dt);
        }
        
        
        Query qw = entityManager.createNamedQuery("WeatherForecast.findByDateList");
        qw.setParameter("datetimes",dates);
        qw.setParameter("cityId", city.getId());
        List result = qw.getResultList(); 
        return result;
    }
    
    /**
     * @return
     */
    public List<WeatherForecast> getMinMaxTemprature()
    {
         Query qWa = entityManager.createNamedQuery("WeatherForecast.findMinMax");
         List <WeatherForecast> result = qWa.getResultList(); 
         return result;
    }
}
