/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 */
public class Eweather
{

	/**
	 * Η φορμα με το βασικο menu της εφαρμογης
	 */
	private static MainView central;

	/**
	 * Η φόρμα "Ο καιρός τώρα"
	 */
	private static WeatherActualView actualWeather;

	/**
	 * Η φόρμα Ποβλεψης 5 ημερών 
 	 */
	private static WeatherForecastView forecastWeather;

	/**
	 * Η φόρμα των στατιστικών
	 */
	private static WeatherStatisticsView statistic;

	/**
	 * Προσβαση στης ρουτινες της κλασης. 
	 * Η δημιουργια γινεται κατα την εκινηση της εφαρμογης 
	 *  
	 */
	private static Eweather instance;

	/**
	 * Το κυριο Frame της εφαρμογής
	 */
	private static JFrame mainFrame;
	/**
	 * Προσβαση στις ρουτινες δεδομενων  
	 */
	private Controller controller;
	/**
	 * 
	 * @return 
	 */
	public Controller getController()
	{
		return controller;
	}

	/**
	 * Class Constructor
	 * Η δημιουργια της Κλάσης γινεται απο την Main 
	 * 
	 */
	public Eweather()
	{
		//Δημιουργια του controller 
		controller = new Controller();
		//Κληση μεσω του Controller για της ανανεωση των τρεχουσων καιρικων συνθηκών
		controller.updateActualData();
		//Κληση μεσω του Controller για της ανανεωση των  καιρικων συνθηκών για τις επομενες μερες
		controller.updateForecastData();
	}

	/**
	 * @param arg the command line arguments
	 * 
	 */
	public static void main(String[] args)
	{
		//Δημιουργια αντικειμενου της κλάσσης Eweather
		// 
		instance = new Eweather();
		//Δημιουργία της κυριας φορμας 
		central = new MainView(instance);
		//Δημιουργια της φορμας για την απεικονιση των τρεχουσων καιρικών συνθηκων
		actualWeather = new WeatherActualView(instance);
		//Δημιουργια της φορμας για την πρ'οβλεψη του καιρού
		forecastWeather = new WeatherForecastView(instance);
		//Δημιουργια της φορμας για την απεικονηση των στατιστικων
		statistic = new WeatherStatisticsView(instance);
		
		// 
		mainFrame = new JFrame("weather ");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		//Εμφανιση της κυριας φορμας με το menu
		instance.switchboard(0);
	}

	/**
	 * Η ρουτίνα ειναι υπευθυνη για την απεικονιση 
	 * της σωστης φορμας βασης των επιλογων του χρηστη 
	 * @param i : 	
	 * 			0 = Κυρια φορμα 
	 * 			1 = Ο καιρος τώρα 
	 * 			2 = Προβλεψη καιρου
	 * 			3 = Φορμα στατιστικων 
	 * 		
	 */
	public void switchboard(int i)
	{
		if (i == 0)
		{
			mainFrame.remove(actualWeather);
			mainFrame.remove(forecastWeather);
			mainFrame.remove(statistic);
			central.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
			mainFrame.add(central, BorderLayout.CENTER);
			mainFrame.revalidate();
			mainFrame.repaint();
		} else if (i == 1)
		{
			actualWeather.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
			mainFrame.remove(central);
			mainFrame.add(actualWeather);
			mainFrame.revalidate();
			mainFrame.repaint();

		} else if (i == 2)
		{
			forecastWeather.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
			mainFrame.remove(central);
			mainFrame.add(forecastWeather);
			mainFrame.revalidate();
			mainFrame.repaint();

		} else if (i == 3)
		{
			statistic.setBounds(1, 1, mainFrame.getWidth() - 20, mainFrame.getHeight() - 20);
			mainFrame.remove(central);
			mainFrame.add(statistic);
			mainFrame.revalidate();
			mainFrame.repaint();
		} else if (i == 4)
		{
			System.exit(0);
		}

		else
		{
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
	 * Η ρουτινα αναλαμβανει την εμφανιση μυνηματων διαλογου (dialog)
	 * @param message :Το μυνημα που θα εμφανιστει
	 * @param title   : Ο τίτλος 
	 */
	public void showdialog(String message, String title)
	{
		JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
