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
 *
 * @author saggelopoulos
 */
public class OpenWeatherService
{

    private static final String APPLICATION_KEY = "8cddfad4933646de13adebd70d2651a8";

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/";

    private static final String GROUP = "group";

    private static final String FORECAST = "forecast";

    private static String UNIT = "metric";

    //private static final String OPEN_WEATHER_MAP_API ="http://api.openweathermap.org/data/2.5/forecast?id=264371&units=metric&appid=8cddfad4933646de13adebd70d2651a8";
    public OpenWeatherService()
    {

    }

    public List<WeatherActual> getActualWeather(List<City> city)
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
            if (reader == null)
            {
                System.out.println("request error receive null !!!");
            }
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

    public List<WeatherForecast> getForecastWeather(List<City> city)
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
                if (reader == null)
                {
                    System.out.println("request error receive null !!!");
                }
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

        } catch (Exception e)
        {

        }
        return results;
    }
    /*
    public static JsonObject getJSON()
    {
        try
        {
            URL url = new URL(OPEN_WEATHER_MAP_API);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
           
                
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            if (reader == null)
                System.out.println("Divyaing null!!!");
            StringBuilder json = new StringBuilder(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
              
            
            javax.json.JsonReader jr = javax.json.Json.createReader(new StringReader(json.toString()));
            javax.json.JsonObject data = jr.readObject();
         
            
            //This value will be 404 if the request was not
            // successful
            if(!"200".equals(data.getString("cod")))
            {
                  return null;
            }
           
             JsonArray ar =   data.getJsonArray("list");
             
             for (int i=0 ;i < ar.size() ; i++)
             {
                 JsonObject ob =ar.getJsonObject(i);
                 System.out.println(ob.getJsonNumber("dt"));
                 JsonObject main = ob.getJsonObject("main");
                 System.out.println(main.getJsonNumber("temp")); 
                 
             }
            
            return data;
        }
           catch(Exception e)
        {
            //System.out.println(Errore.toString() );
            return null;
        }
    }

     */

}
