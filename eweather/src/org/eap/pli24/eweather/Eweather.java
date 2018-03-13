/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.eap.pli24.eweather.controller.Controller;
import org.eap.pli24.eweather.model.WeatherActual;
import org.eap.pli24.eweather.model.WeatherForecast;
import org.eap.pli24.eweather.view.ActualWeather;
import org.eap.pli24.eweather.view.Central;
import org.eap.pli24.eweather.view.ForecastWeather;
import org.eap.pli24.eweather.view.Statistics;
import org.eap.pli24.eweather.wservice.OpenWeatherService;

/**
 *
 * @author aggelopoulos
 */
public class Eweather 
{

    
    private  static Central central;
    
    private static ActualWeather actualWeather;
    
    private static ForecastWeather forecastWeather;
    
    private static Statistics statistic;
           
    private static Eweather instance;
    
    private static JFrame mainFrame;
    
    private  Controller controller;

    public Controller getController() 
    {
        return controller;
    }
    
    public OpenWeatherService openWeatherService;
    
    
    public Eweather()
    {
        controller = new Controller();
        openWeatherService = new OpenWeatherService();
        
        List<WeatherActual> lt= openWeatherService.getActualWeather(controller.getCityList());
        controller.deleteWeatherActualAll();
        controller.insertWeatherActual(lt);
            
        List<WeatherForecast> weatherForecasts = openWeatherService.getForecastWeather(controller.getCityList());
        controller.insertWeatherForecast(weatherForecasts);
        System.out.println( weatherForecasts.size());
        
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) 
    {
        instance = new Eweather();
        central = new Central(instance);
        actualWeather = new ActualWeather(instance);
        forecastWeather = new ForecastWeather(instance);
        statistic = new Statistics(instance);
        
        //List<WeatherForecast>  minMaxTemprature = controller.getMinMaxTemprature();
        
        mainFrame= new JFrame("weather ");
        mainFrame.setSize(800,600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        instance.switchboard(0);
    }
    
    public  void switchboard(int i)
    {
        if (i ==0 )
        {
            mainFrame.remove(actualWeather);
            mainFrame.remove(forecastWeather);
            mainFrame.remove(statistic);
            central.setBounds(1, 1, mainFrame.getWidth()-20, mainFrame.getHeight()-20);
            mainFrame.add(central, BorderLayout.CENTER);   
            mainFrame.revalidate();
            mainFrame.repaint();
        }
        else if (i==1)
        {        
            actualWeather.setBounds(1, 1, mainFrame.getWidth()-20, mainFrame.getHeight()-20);
            mainFrame.remove(central);
            mainFrame.add(actualWeather);
            mainFrame.revalidate();
            mainFrame.repaint();
         
        }   
        else if (i==2)
        {
            forecastWeather.setBounds(1, 1, mainFrame.getWidth()-20, mainFrame.getHeight()-20);
            mainFrame.remove(central);
            mainFrame.add(forecastWeather);
            mainFrame.revalidate();
            mainFrame.repaint();  
        
        }
        else if (i==3)
        {
            statistic.setBounds(1, 1, mainFrame.getWidth()-20, mainFrame.getHeight()-20);
            mainFrame.remove(central);
            mainFrame.add(statistic);
            mainFrame.revalidate();
            mainFrame.repaint();
        }  
        else if (i==4)
        {
            System.exit(0);
        }
        
        else 
        {
            mainFrame.remove(actualWeather);
            mainFrame.remove(forecastWeather);
            mainFrame.remove(statistic);
            central.setBounds(1, 1, mainFrame.getWidth()-20, mainFrame.getHeight()-20);
            mainFrame.add(central, BorderLayout.CENTER);   
            mainFrame.revalidate();
            mainFrame.repaint();
            
        }
        
       
        
    }
    public void showdialog( String message,String title )
    {
            JOptionPane.showMessageDialog(mainFrame,message,title, JOptionPane.INFORMATION_MESSAGE) ;
    }
    
}
