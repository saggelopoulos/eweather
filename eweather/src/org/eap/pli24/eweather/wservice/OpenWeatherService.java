/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather.wservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import org.eap.pli24.eweather.model.City;
import org.eap.pli24.eweather.model.Condition;
import org.eap.pli24.eweather.model.WeatherActual;
import org.eap.pli24.eweather.model.WeatherForecast;
import org.eap.pli24.eweather.model.WeatherForecastPK;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 *
 */
public class OpenWeatherService
{

    /**
     *  Το API_KEY  
     */
    private static final String APPLICATION_KEY = "8cddfad4933646de13adebd70d2651a8";

    /**
     * Το service url 
     */
    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/";

    /**
     * 
     */
    private static final String GROUP = "group";

    /**
     * 
     */
    private static final String FORECAST = "forecast";

    /**
     * 
     */
    private static String UNIT = "metric";

    
    /**
     *  Class Constructor
     */
    public OpenWeatherService()
    {

    }

    
    /**
     * Κληση του service για τις τρεχουσες καιρικες συνθηκες και για μια λιστα απο πολεις 
     * @param city : Λιστα απο αντικειμενα τυπου {@link org.eap.pli24.eweather.model.City}
     * @return		:Λιστα απο αντικειμενα τυπου {@link org.eap.pli24.eweather.model.WeatherActual}
     */
    public List<WeatherActual> getActualWeatherData(List<City> city)
    {
        ArrayList<WeatherActual> results = new ArrayList<>();
        try
        {
            String cityIds = "";
            for (City ct : city)
            {
                if (cityIds == "")
                {
                    cityIds = ct.getId().toString();
                } else
                {
                    cityIds = cityIds + "," + ct.getId().toString();
                }

            }
            String urlString = OPEN_WEATHER_MAP_URL + GROUP + "?" + "id=" + cityIds + "&units=" + UNIT + "&appid=" + APPLICATION_KEY;
            //put the cities here 
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonRaw = new StringBuilder(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
            {
                jsonRaw.append(tmp).append("\n");
            }
            reader.close();
            JsonReader jsonData = Json.createReader(new StringReader(jsonRaw.toString()));
            JsonObject data = jsonData.readObject();
            int cnt = data.getJsonNumber("cnt").intValue();
            if (cnt != city.size())
            {
                return null;
            }
            JsonArray cityData = data.getJsonArray("list");
            for (int i = 0; i < cityData.size(); i++)
            {

                JsonObject currentData = cityData.getJsonObject(i);
                int id = currentData.getInt("id");
                JsonObject main = currentData.getJsonObject("main");
                JsonNumber temprature = main.getJsonNumber("temp");
                int contitionID = currentData.getJsonArray("weather").getJsonObject(0).getInt("id");
                JsonNumber clouds = currentData.getJsonObject("clouds").getJsonNumber("all");
                JsonNumber windSpeed = currentData.getJsonObject("wind").getJsonNumber("speed");
                BigDecimal rainActual = BigDecimal.ZERO;
                JsonObject rain = currentData.getJsonObject("rain");
                if (rain != null)
                {
                    JsonNumber rain3h = rain.getJsonNumber("3h");
                    rainActual = rain3h.bigDecimalValue();
                }
                BigDecimal snowActual = BigDecimal.ZERO;
                JsonObject snow = currentData.getJsonObject("snow");
                if (snow != null)
                {
                    JsonNumber snow3h = snow.getJsonNumber("3h");
                    snowActual = snow3h.bigDecimalValue();
                }
                WeatherActual weatherActual = new WeatherActual();
                weatherActual.setCityId(id);
                weatherActual.setConditionId(new Condition(contitionID));
                weatherActual.setTemprature(temprature.bigDecimalValue());
                weatherActual.setWindSpeed(windSpeed.bigDecimalValue());
                weatherActual.setClounds(clouds.bigDecimalValue());
                weatherActual.setRain(rainActual);
                weatherActual.setSnow(snowActual);
                results.add(weatherActual);
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return results;
    }

    /**
     * Κληση του service για τις καιρικες συνθηκες 5 ημερων και για μια λιστα απο πολεις 
     * @param city  :Λιστα απο αντικειμενα τυπου {@link org.eap.pli24.eweather.model.City}
     * @return:Λιστα απο αντικειμενα τυπου {@link org.eap.pli24.eweather.model.WeatherForecast}
     */
    public List<WeatherForecast> getForecastWeatherData(List<City> city)
    {
        ArrayList<WeatherForecast> results = new ArrayList<>();
        try
        {
            for (City ct : city)
            {

                String urlString = OPEN_WEATHER_MAP_URL + FORECAST + "?" + "id=" + ct.getId() + "&units=" + UNIT + "&appid=" + APPLICATION_KEY;
                //put the cities here 
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonRaw = new StringBuilder(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                {
                    jsonRaw.append(tmp).append("\n");
                }
                reader.close();
                JsonReader jsonData = Json.createReader(new StringReader(jsonRaw.toString()));
                JsonObject data = jsonData.readObject();
                String cod = data.getString("cod");
                if (!"200".equals(cod))
                {
                    continue;
                }
                JsonArray cityData = data.getJsonArray("list");

                for (int i = 0; i < cityData.size(); i++)
                {
                    JsonObject currentData = cityData.getJsonObject(i);
                    JsonNumber timestamp = currentData.getJsonNumber("dt");
                    Date currentDate = new Date(timestamp.longValue()*1000);
                    JsonObject main = currentData.getJsonObject("main");
                    JsonNumber temprature = main.getJsonNumber("temp");

                    int contitionID = currentData.getJsonArray("weather").getJsonObject(0).getInt("id");
                    JsonNumber clouds = currentData.getJsonObject("clouds").getJsonNumber("all");
                    JsonNumber windSpeed = currentData.getJsonObject("wind").getJsonNumber("speed");

                    BigDecimal rainActual = BigDecimal.ZERO;
                    JsonObject rain = currentData.getJsonObject("rain");

                    if (rain != null)
                    {
                      
                        JsonNumber rain3h = rain.getJsonNumber("3h");
                        if (rain3h != null )
                            rainActual = rain3h.bigDecimalValue();
                    }

                    BigDecimal snowActual = BigDecimal.ZERO;
                    JsonObject snow = currentData.getJsonObject("snow");
                    if (snow != null)
                    {
                        JsonNumber snow3h = snow.getJsonNumber("3h");
                        snowActual = snow3h.bigDecimalValue();
                    }

                    WeatherForecast weatherForecast = new WeatherForecast(new WeatherForecastPK(currentDate,ct.getId() ));
                    weatherForecast.setConditionId(new Condition(contitionID));
                    weatherForecast.setTemprature(temprature.bigDecimalValue());
                    weatherForecast.setClounds(clouds.bigDecimalValue());
                    weatherForecast.setWindSpeed(windSpeed.bigDecimalValue());
                    weatherForecast.setRain(rainActual);
                    weatherForecast.setSnow(snowActual);
                    results.add(weatherForecast);
                    
                    
                }

            }

        } 
        catch (Exception e)
        {

        }
        return results;
    }
}
