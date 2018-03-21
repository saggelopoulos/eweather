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
import org.eap.pli24.eweather.model.WeatherForecastStatistics;
import org.eap.pli24.eweather.wservice.OpenWeatherService;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */
public class Controller{
    
    private EntityManagerFactory  entityManagerFactory;
    
    private EntityManager entityManager ;
    
    private OpenWeatherService openWeatherService;
    
    /**
     * Class Constructor
     */
    public Controller(){
        entityManagerFactory =Persistence.createEntityManagerFactory("eweatherPU");        
        entityManager =entityManagerFactory.createEntityManager();
        // Πρόσβαση στα δεδομένα του openweather
        openWeatherService = new OpenWeatherService();
    }
    
    /**
     * Ενημέρωση της βάσης δεδομένων με τις τρέχουσες καιρικές συνθήκες  
     */
    public void updateActualData(){
    	 List<WeatherActual> lt= openWeatherService.getActualWeatherData(getCityList());
         deleteWeatherActualAll();
         insertWeatherActual(lt);
    }
    
    /**
     * Ενημέρωση της βάσης δεδομένων με τις προβλέψεις για 5 ήμερες 
     */
    public void updateForecastData(){
    	List<WeatherForecast> weatherForecasts = openWeatherService.getForecastWeatherData(getCityList());
        insertWeatherForecast(weatherForecasts);  
    }
        
    /**
     * Διαβάζει τα στοιχεία των πόλεων 
     * @return Μια λίστα με αντικείμενα τύπου {@link org.eap.pli24.eweather.model.City}
     */
    public List<City> getCityList(){
        Query qWa = entityManager.createNamedQuery("City.findAll");
        List<City> result = qWa.getResultList();
        return result;
    }
    
    /**
     * Αναζητά και επιστρέφει την πόλη σύμφωνα με τον κωδικό της
     * @param cityID : Ο κωδικός της πόλης  
     * @return Ένα αντικείμενο τύπου {@link org.eap.pli24.eweather.model.City}
     */
    public City getCity(int cityID){
        Query qWa = entityManager.createNamedQuery("City.findById");
        qWa.setParameter("id", cityID);
        City rsl = (City) qWa.getSingleResult();
        return rsl;
    }
    
    /**
     * Αναζητά και επιστρέφει την συνθήκη σύμφωνα με τον κωδικό της 
     * @param conditionID :Ο κωδικός της συνθήκης 
     * @return  Ένα αντικείμενο τύπου {@link org.eap.pli24.eweather.model.Condition}
     */
    public Condition getCondition(int conditionID){
        Query qWa = entityManager.createNamedQuery("Condition.findById");
        qWa.setParameter("id", conditionID);
        Condition condition = (Condition)qWa.getSingleResult();
        return condition;
    }

    /**
     * Επιστρέφει όλες τις εγγραφές που είναι καταχωρημένες στο σύστημα 
     * @return Μία λίστα με αντικείμενα τύπου {@link org.eap.pli24.eweather.model.WeatherActual}
     */
    public List<WeatherActual> getWeatherActualAll(){
        Query qWa = entityManager.createNamedQuery("WeatherActual.findAll");
         List result = qWa.getResultList(); 
        return result;
    }
    
    /**
     * @param cityParameter
     * @return
     */
    public List<WeatherActual> getWeatherActualByCity(List cityParameter){
        Query qWa = entityManager.createNamedQuery("WeatherActual.findByCityList");
        qWa.setParameter("cityId", cityParameter);
        List result = qWa.getResultList(); 
        return result;
    }

    /**
     * Διαγραφή όλων των εγγράφων που βρίσκονται καταχωρημένες στον πινακα
     *  WEATHER_ACTUAL
     */
    public void deleteWeatherActualAll(){
        Query qWa = entityManager.createNamedQuery("WeatherActual.findAll");
        List <WeatherActual> result = qWa.getResultList(); 
        entityManager.getTransaction().begin();
        for(WeatherActual wa :result){
            entityManager.remove(wa);   
        }
        entityManager.getTransaction().commit();
    }
    
    /**
     * Εισαγωγή όλων των εγγράφων της λίστας στον πινάκα WEATHER_ACTUAL
     * @param dt : Λίστα με αντικείμενα τύπου {@link org.eap.pli24.eweather.model.WeatherActual}
     */
    public void insertWeatherActual (List<WeatherActual> dt){
        entityManager.getTransaction().begin();
        for (WeatherActual wa : dt){
            wa.setCity(getCity(wa.getCityId()));
            wa.setConditionId(getCondition(wa.getConditionId().getId()));
            
            entityManager.persist(wa);
        }
        entityManager.getTransaction().commit();
    }
    
    /**
     * Εισαγωγή όλων των εγγραφών της λίστας στον πίνακα WEATHER_FORECAST 
     * @param weatherForecasts :Λιστα με αντικειμενα τυπου {@link org.eap.pli24.eweather.model.WeatherForecast}
     */
    public void insertWeatherForecast(List<WeatherForecast> weatherForecasts){
        entityManager.getTransaction().begin();
        for (WeatherForecast wf : weatherForecasts){
            Query qw = entityManager.createNamedQuery("WeatherForecast.findByPkey");
            qw.setParameter("datetime", wf.getWeatherForecastPK().getDatetime());
            qw.setParameter("cityId", wf.getWeatherForecastPK().getCityId());
            try{
                Object rsl = qw.getSingleResult();
                if (rsl == null){
                    entityManager.persist(wf);    
                }
            }catch (Exception e){
                entityManager.persist(wf);
            }
        }
        entityManager.getTransaction().commit();   
    }
    
    /**
     * Λήψη των δεδομένων από τον πίνακα WEATHER_FORECAST μεταξύ των ημερομηνιών startDate και endDate 
     * Για την πολη city
     * @param startDate : Από ημερομηνία 
     * @param endDate	: Έως Ημερομηνία
     * @param city		: Πόλη
     * @return			: Λίστα με αντικείμενα τύπου {@link org.eap.pli24.eweather.model.WeatherForecast}
     */
    public List<WeatherForecast> getWeatherForecastByDate(Date startDate, Date endDate , City city ){
        Query qw = entityManager.createNamedQuery("WeatherForecast.findDateRange");
        qw.setParameter("startDate",startDate);
        qw.setParameter("endDate",endDate);
        qw.setParameter("cityId", city.getId());
        List result = qw.getResultList(); 
        return result;
    }
    
    
    /**
     * Λήψη των δεδομένων από τον πίνακα WEATHER_FORECAST για τις ημερομηνίες που βρίσκονται στην λίστα dates 
     * Για την πόλη city 
     * @param dates :Λίστα αντικειμένων τύπου {@link java.util.Date}
     * @param city  :Αντικείμενο τύπου {@link org.eap.pli24.eweather.model.City} 
     * @return 		:Λίστα με αντικείμενα  τύπου {@link org.eap.pli24.eweather.model.WeatherForecast}
     */
    public List<WeatherForecast> getWeatherForecastByDateList(List<Date> dates , City city ){
        for (Date dt: dates){
            System.out.println(dt);
        }
        Query qw = entityManager.createNamedQuery("WeatherForecast.findByDateList");
        qw.setParameter("datetimes",dates);
        qw.setParameter("cityId", city.getId());
        List result = qw.getResultList(); 
        return result;
    }
    
    public List<WeatherForecast> getMinMaxTemprature(){
         Query qWa = entityManager.createNamedQuery("WeatherForecast.findMinMax");
         List <WeatherForecast> result = qWa.getResultList(); 
         return result;
    }
    
    /**
     * Λήψη των δεδομένων από το View WEATHER_FORECAST_STATISTICS για κάποια συγκεκριμένη πόλη. 
     * @param city 	:Αντικείμενο τύπου {@link org.eap.pli24.eweather.model.City}
     * @return 		:Λίστα αντικειμένων τυπου {@link org.eap.pli24.eweather.model.WeatherForecastStatistics}
     */
    public List<WeatherForecastStatistics>  getCityStatistics(City city){
        Query qw = entityManager.createNamedQuery("WeatherForecastStatistics.findByCityId");
        qw.setParameter("cityId", city.getId());
        List result = qw.getResultList(); 
        return result;
    }
    /**
     * Λήψη των δεδομένων από το View WEATHER_FORECAST_STATISTICS για ολες τις πολεις.
     * @return	:Λίστα αντικειμένων τύπου {@link org.eap.pli24.eweather.model.WeatherForecastStatistics}
     */
    public List<WeatherForecastStatistics>  getStatistics(){
        Query qw = entityManager.createNamedQuery("WeatherForecastStatistics.findAll");
        List result = qw.getResultList(); 
        return result;
    }
}
