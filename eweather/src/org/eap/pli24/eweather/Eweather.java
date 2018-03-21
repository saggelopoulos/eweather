package org.eap.pli24.eweather;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eap.pli24.eweather.controller.Controller;
import org.eap.pli24.eweather.view.WeatherActualView;
import org.eap.pli24.eweather.view.MainView;
import org.eap.pli24.eweather.view.WeatherForecastView;
import org.eap.pli24.eweather.view.WeatherStatisticsView;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */
public class Eweather{

    /**
     * Η φόρμα με το βασικό menu της εφαρμογής
     */
    private static MainView central;

    /**
     * Η φόρμα "Ο καιρός τώρα"
     */
    private static WeatherActualView actualWeather;

    /**
     * Η φόρμα Πρόβλεψης 5 ημερών 
     */
    private static WeatherForecastView forecastWeather;
    
    /**
     * Η φόρμα των στατιστικών
     */
    private static WeatherStatisticsView statistic;

    /**
     * Πρόσβαση στης ρουτίνες της κλάσης. 
     * Η δημιουργία γίνεται κατά την εκκίνηση της εφαρμογής 
     */
    private static Eweather instance;

    /**
     * Το κύριο Frame της εφαρμογής
     */
    private static JFrame mainFrame;
    /**
     * Πρόσβαση στις ρουτίνες δεδομένων  
     */
    private Controller controller;
   
    public Controller getController(){
		return controller;
    }

    /**
     * Class Constructor
     * Η δημιουργία της Κλάσης γίνεται από την Main
     */
    public Eweather(){
        //Δημιουργια του controller 
        controller = new Controller();
        //Κλήση μέσω του Controller για της ανανέωση των τρεχουσων καιρικων συνθηκών
	controller.updateActualData();
	//Κληση μεσω του Controller για της ανανεωση των  καιρικων συνθηκών για τις επομενες μερες
	controller.updateForecastData();
    }

    /**
     * @param arg the command line arguments 
     */
    public static void main(String[] args){
        //Δημιουργια αντικειμενου της κλάσσης Eweather	
	instance = new Eweather();
	//Δημιουργία της κυριας φορμας 
	central = new MainView(instance);
	//Δημιουργία της φόρμας για την απεικόνιση των τρεχουσών καιρικών συνθηκών
	actualWeather = new WeatherActualView(instance);
	//Δημιουργία της φόρμας για την πρόβλεψη του καιρού
	forecastWeather = new WeatherForecastView(instance);
	//Δημιουργία της φόρμας για την απεικόνιση των στατιστικών
	statistic = new WeatherStatisticsView(instance);
	
	mainFrame = new JFrame("weather ");
	mainFrame.setSize(800, 600);
	mainFrame.setLayout(new BorderLayout());
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainFrame.setVisible(true);
		
	//Εμφάνιση της κύριας φόρμας με το menu
	instance.switchboard(0);
    }

    /**
     * Η ρουτίνα είναι υπεύθυνη για την απεικόνιση 
     * της σωστής φόρμας βάση των επιλογών του χρήστη 
     * @param i : 	
     * 			0 = Κύρια φόρμα 
     * 			1 = Ο καιρός τώρα 
     * 			2 = Πρόβλεψη καιρού
     * 			3 = Φόρμα στατιστικών	
     */
    public void switchboard(int i){
        if (i == 0){
            mainFrame.remove(actualWeather);
            mainFrame.remove(forecastWeather);
            mainFrame.remove(statistic);
            central.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
            mainFrame.add(central, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();
	} else if (i == 1){
            actualWeather.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
            mainFrame.remove(central);
            mainFrame.add(actualWeather);
            mainFrame.revalidate();
            mainFrame.repaint();
	} else if (i == 2){
            forecastWeather.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
            mainFrame.remove(central);
            mainFrame.add(forecastWeather);
            mainFrame.revalidate();
            mainFrame.repaint();
        } else if (i == 3){
            statistic.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
            mainFrame.remove(central);
            mainFrame.add(statistic);
            mainFrame.revalidate();
            mainFrame.repaint();
	} else if (i == 4){
            System.exit(0);
	}else{
            mainFrame.remove(actualWeather);
            mainFrame.remove(forecastWeather);
            mainFrame.remove(statistic);
            central.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
            mainFrame.add(central, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }

    /**
     * Η ρουτίνα αναλαμβάνει την εμφάνιση μηνυμάτων διαλόγου (dialog)
     * @param message :Το μήνυμα που θα εμφανιστεί
     * @param title   : Ο τίτλος 
     */
    public void showdialog(String message, String title){
        JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
