/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import org.eap.pli24.eweather.controller.Controller;
import org.eap.pli24.eweather.model.WeatherForecast;
import org.eap.pli24.eweather.view.ActualWeather;
import org.eap.pli24.eweather.view.Central;

/**
 *
 * @author aggelopoulos
 */
public class Eweather {

    
    private static ActualWeather actualWeather;
    
    private static Central central ;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    
    {
            
        Controller controller = new Controller();
        
        List<WeatherForecast>  minMaxTemprature = controller.getMinMaxTemprature();
               
        JFrame frame = new JFrame("weather ");
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // as per your requirement.
        central = new Central();
       // actualWeather = new ActualWeather();
      //  actualWeather.setSize(600,500);
        //actualWeather.setBounds(1, 1, 600, 500);
        //actualWeather.setVisible(true);
          
      
        //central.MainPanel.add(actualWeather ,BorderLayout.CENTER);
      
        
          frame.add(central, BorderLayout.CENTER);
        frame.setVisible(true);

        
   
    
    }
    
}
