/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eap.pli24.eweather.model.City;
import org.eap.pli24.eweather.model.Condition;
import org.eap.pli24.eweather.model.WeatherActual;
import org.eap.pli24.eweather.model.WeatherForecast;

/**
 *
 * @author aggelopoulos
 */
public class Controller 
{
    
    EntityManagerFactory  entityManagerFactory;
    EntityManager entityManager ;
    
    
    public Controller()
    {
        entityManagerFactory =Persistence.createEntityManagerFactory("eweatherPU");        
        entityManager =entityManagerFactory.createEntityManager();
    }
        
        
    public List<City> getCityList()
    {
        Query qWa = entityManager.createNamedQuery("City.findAll");
        List result = qWa.getResultList();
        return result;
    }
    
    public City getCity(int cityID)
    {
        Query qWa = entityManager.createNamedQuery("City.findById");
        qWa.setParameter("id", cityID);
        City rsl = (City) qWa.getSingleResult();
        return rsl;
    }
    
    public Condition getCondition(int conditionID)
    {
        Query qWa = entityManager.createNamedQuery("Condition.findById");
        qWa.setParameter("id", conditionID);
        Condition condition = (Condition)qWa.getSingleResult();
        return condition;
    }
    
    
    
    
    
    
    public List<WeatherActual> getWeatherActualAll()
    {
        Query qWa = entityManager.createNamedQuery("WeatherActual.findAll");
         List result = qWa.getResultList(); 
        return result;
    }
    
    
    public List<WeatherActual> getWeatherActualByCity(List cityParameter)
    {
        Query qWa = entityManager.createNamedQuery("WeatherActual.findByCityList");
        qWa.setParameter("cityId", cityParameter);
        List result = qWa.getResultList(); 
        return result;
    }
    
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
    
    public void insertWeatherForecast(List<WeatherForecast> weatherForecasts)
    {
        entityManager.getTransaction().begin();
        for (WeatherForecast wf : weatherForecasts)
        {
            Query qw = entityManager.createNamedQuery("WeatherForecast.findByPkey");
            qw.setParameter("datetime", wf.getWeatherForecastPK().getDatetime());
            qw.setParameter("cityId", wf.getWeatherForecastPK().getCityId());
            if (qw.getSingleResult()==null)
            {
                //wf.setCity(getCity(wf.getCity().getId()Id()));
                //wf.setConditionId(getCondition(wf.getConditionId().getId()));
            
                entityManager.persist(wf);
            }
        }
        entityManager.getTransaction().commit();
        
    }
    
    public List<WeatherForecast> getMinMaxTemprature()
    {
         Query qWa = entityManager.createNamedQuery("WeatherForecast.findMinMax");
         List <WeatherForecast> result = qWa.getResultList(); 
         return result;
    }
}
