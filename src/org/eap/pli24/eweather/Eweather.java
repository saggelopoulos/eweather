/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eap.pli24.eweather;

import java.util.List;
import org.eap.pli24.eweather.controller.Controller;
import org.eap.pli24.eweather.model.WeatherForecast;

/**
 *
 * @author aggelopoulos
 */
public class Eweather {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    
    {
            
        Controller controller = new Controller();
        
        List<WeatherForecast>  minMaxTemprature = controller.getMinMaxTemprature();
       
   
   
    
    }
    
}
