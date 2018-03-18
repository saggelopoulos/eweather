package org.eap.pli24.eweather.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.eap.pli24.eweather.Eweather;
import org.eap.pli24.eweather.model.City;
import org.eap.pli24.eweather.model.WeatherForecast;
import org.eclipse.persistence.internal.helper.Helper;


/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */
public class WeatherForecastView extends javax.swing.JPanel {
    private List<City> cities;
    private Eweather eweather;    
    private SimpleDateFormat dateFormat;
     /**
     * Creates new form ForecastWeather
     */
    public WeatherForecastView(Eweather eweather) {
        
        this.eweather = eweather;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        initComponents();
        showCity();
        setJtable(0);
    }
    
    /**
     * Εμφανιση των ονομάτων Πολεων 
     */
    private void showCity()
    {
        cities=  eweather.getController().getCityList();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Επιλογή");
        for (City ct : cities)
        {
            model.addElement(ct.getName());
        }
        CityListUI.setModel(model); 
    }
    
    /**
     * Ρυθμιση του τροπου εμφανισης του πινακα (columns and rows)
     * @param mode 
     */
    private void setJtable(int mode)
    {
         DefaultTableModel model; 
         int numRows=0;
         if (mode==0)
         {
             numRows = 8;
         }
         else
         {
             numRows = 6;
         }
         String col[] = {"Ημερομηνία","Καιρός","Θερμοκρασία","Σύνεφα" ,"Άνεμος", "Βροχή", "Χιόνι" }; 
             String rows[][] = new String[numRows][7];
             for (int i = 0 ; i<numRows-1; i++)
             {
                 for (int j =0 ; j<7 ; j++)
                 {
                     rows[i][j] ="";
                 } 
             }
             
             
             
           model = new DefaultTableModel(rows, col);              
          jTable1.setModel(model);
          
     }        
     
    /**
     * Εμφανιση αποτελεσματων για τις επομενες 24 ωρες και για 
     * συγκεκριμενη πολη. 
     */
    private void ShowNext24Hours()
     {
        if ("Επιλογή" == CityListUI.getSelectedItem())
        {
            eweather.showdialog("Παρακαλω Επιλέξτε Πόλη!", "Προσοχή");
            return ;
        }
        
        City currentCity ; 
        for ( City ct :cities)
        {
            if  (ct.getName() == CityListUI.getSelectedItem())
            {
                currentCity = ct;
                Date begindate = new Date();
                Date endDate = new Date( begindate.getTime() + (24*60*60*1000));
                System.out.println(begindate);
                  System.out.println(endDate);       
                
                List<WeatherForecast> results = eweather.getController().getWeatherForecastByDate(begindate, endDate, currentCity);
                int i =0;
                
                for ( WeatherForecast wf  : results)
                {
                     jTable1.getModel().setValueAt(  dateFormat.format(wf.getWeatherForecastPK().getDatetime()) , i, 0);
                     jTable1.getModel().setValueAt(wf.getConditionId() , i, 1);
                     jTable1.getModel().setValueAt(wf.getTemprature() , i, 2);
                     jTable1.getModel().setValueAt(wf.getClounds().toPlainString(), i, 3);
                     jTable1.getModel().setValueAt(wf.getWindSpeed().toPlainString() , i, 4);
                     jTable1.getModel().setValueAt(wf.getRain().toPlainString() , i, 5);
                     jTable1.getModel().setValueAt(wf.getSnow().toPlainString(), i, 6);
                     i++;
                }
            }
        }
     }
    
    /**
     * Εμφανιση αποτελεσματων για τις επομενες 5 ημερες  
     * και για συγκεκριμενη πολη. 
     */
     public void showNext5Days()
     {
        if ("Επιλογή" == CityListUI.getSelectedItem())
        {
            eweather.showdialog("Παρακαλω Επιλέξτε Πόλη!", "Προσοχή");
            return ;
        }
        ArrayList<Date> dates= new ArrayList();
        Date today  = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int currentHours = calendar.get(Calendar.HOUR_OF_DAY);
        int hours=0;
        
        switch (currentHours)
        {
            case 23:
            case 0:
            case 1:
                
            {
                
                hours =2;
                break;
            }
            case 2:
            case 3:   
            case 4:
            {
                hours =5;
                break;
            }
            case 5:
            case 6:    
            case 7: 
            {
                hours =8;
                break;
            
            }
            case 8:
            case 9:    
            case 10: 
            {
                hours =11;
                break;
            
            }
            case 11:
            case 12:    
            case 13: 
            {
                hours =14;
                break;
            }
            case 14:
            case 15:    
            case 16: 
            {
                hours =17;
                break;
            }
            case 17:
            case 18:    
            case 19: 
            {
                hours =20;
                break;
            }
            case 20:
            case 21:    
            case 22: 
            {
                hours =23;
                break;
            }
            
        }
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        dates.add(calendar.getTime());
        for (int i =1 ; i< 6 ;i++)
        {
            Date dt = new Date();
            calendar.setTime(dt);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+ i);
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            
            dates.add(calendar.getTime());   
            
            
        }
        
        City currentCity ; 
        for ( City ct :cities)
        {
            if  (ct.getName() == CityListUI.getSelectedItem())
            {
                 currentCity = ct;
                 List<WeatherForecast> results = eweather.getController().getWeatherForecastByDateList(dates, currentCity);
                 
                int i=0; 
                for ( WeatherForecast wf  : results)
                {
                     jTable1.getModel().setValueAt(  dateFormat.format(wf.getWeatherForecastPK().getDatetime()) , i, 0);
                     jTable1.getModel().setValueAt(wf.getConditionId() , i, 1);
                     jTable1.getModel().setValueAt(wf.getTemprature() , i, 2);
                     jTable1.getModel().setValueAt(wf.getClounds().toPlainString(), i, 3);
                     jTable1.getModel().setValueAt(wf.getWindSpeed().toPlainString() , i, 4);
                     jTable1.getModel().setValueAt(wf.getRain().toPlainString() , i, 5);
                     jTable1.getModel().setValueAt(wf.getSnow().toPlainString(), i, 6);
                     i++;
                }
                 
                         
                         
                         
            }
        
        }
        
     }
       
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        CityListUI = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setName("Form"); // NOI18N

        jButton1.setText("Επιστροφή");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Προβλεψη Καιρού 1ης Ημέρας");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Πρόβλεψη Καιρού 5 ημερών");
        jButton3.setToolTipText("");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("Ανανέωση Πρόβλεψης Καιρού");
        jButton4.setToolTipText("");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        CityListUI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CityListUI.setName("CityListUI"); // NOI18N

        jLabel1.setText("Πόλη");
        jLabel1.setToolTipText("");
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(CityListUI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 309, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jButton2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton1))
                    .add(layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(CityListUI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton2)
                    .add(jButton3)
                    .add(jButton1)
                    .add(jButton4))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        eweather.switchboard(0);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        setJtable(0);
        ShowNext24Hours();
  
    }//GEN-LAST:event_jButton2MouseClicked
    /*
    * Five days
    */
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        setJtable(1);
        showNext5Days();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        setJtable(0);
        eweather.getController().updateForecastData();
        ShowNext24Hours();
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CityListUI;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
