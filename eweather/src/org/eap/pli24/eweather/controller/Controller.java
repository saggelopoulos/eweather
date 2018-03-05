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
    public List<WeatherForecast> getMinMaxTemprature()
    {
         Query qWa = entityManager.createNamedQuery("WeatherForecast.findMinMax");
         List <WeatherForecast> result = qWa.getResultList(); 
         return result;
    }
}