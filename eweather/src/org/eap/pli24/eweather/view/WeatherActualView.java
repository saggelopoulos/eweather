package org.eap.pli24.eweather.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.TableColumn;
import org.eap.pli24.eweather.Eweather;
import org.eap.pli24.eweather.model.City;
import org.eap.pli24.eweather.model.WeatherActual;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */
public class WeatherActualView extends javax.swing.JPanel {

    private List<City> cities;
    
    private Eweather eweather;
    
    /**
     * Creates new form ActualWeather
     */
    public WeatherActualView(Eweather eweather){
        this.eweather = eweather;
        initComponents();
        TableColumn tc = weatherTableUI.getColumnModel().getColumn(1);
        tc.setCellRenderer(new ImageRenderer());
        showCity();
    }
    
    /**
     *Εμφανιση των ονομάτων Πολεων 
     */
    private void showCity(){
        cities=  eweather.getController().getCityList();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (City ct : cities){
            model.addElement(ct.getName());
        }
        cityListUI.setModel(model); 
    }
        
    /**
     * Εμφανιση των αποτελεσμάτων στην οθόνη
     */
    private void showWeatherData(){
         if (cityListUI.getSelectedIndices().length ==0){
           eweather.showdialog("Δεν εχει επιλεγεί καμια πολη", "Πρόβλημα");
           return ;
        }
        
        List<Integer> selectCityID  = new ArrayList<>();
        int i=0;        
        for (int idx : cityListUI.getSelectedIndices()){
            City ct = cities.get(idx);
            selectCityID.add(ct.getId());
            i++;
            System.out.println(ct.getId());
        }
    
        List<WeatherActual> weatherActualsList =eweather.getController().getWeatherActualByCity(selectCityID);
        
        int inx=0 ;
        for( i=0 ; i<5 ; i++){
            weatherTableUI.getModel().setValueAt("", i, 0);
            weatherTableUI.getModel().setValueAt(null, i, 1);
            weatherTableUI.getModel().setValueAt("", i, 2);
            weatherTableUI.getModel().setValueAt("", i, 3);
            weatherTableUI.getModel().setValueAt("", i, 4);
            weatherTableUI.getModel().setValueAt("", i, 5);
        }
      
        for (WeatherActual wa : weatherActualsList){              
            weatherTableUI.getModel().setValueAt(wa.getCity().getName(), inx, 0);
            weatherTableUI.getModel().setValueAt(wa.getIcon(), inx, 1);
            weatherTableUI.getModel().setValueAt(wa.getConditionId(), inx, 2);
            weatherTableUI.getModel().setValueAt(wa.getTemprature().intValue() + " °C", inx, 3);
            weatherTableUI.getModel().setValueAt(wa.getWindSpeed().floatValue() + " m/s", inx, 4);    
            weatherTableUI.getModel().setValueAt(wa.getClounds().intValue() + " %", inx, 5);    
            inx++;
        }   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cityListUI = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        weatherTableUI = new javax.swing.JTable();

        jButton1.setText("Ο Καιρός Τώρα");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Ανανέωση");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Επιστροφή");
        jButton3.setToolTipText("");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton3MouseClicked(evt);
            }
        });

        cityListUI.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        cityListUI.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                cityListUIValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(cityListUI);

        weatherTableUI.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        weatherTableUI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String []
            {
                "Πόλη", "Icon", "Καιρός", "Θερμοκρασία", "Ανεμος", "Σύνεφα"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        weatherTableUI.setColumnSelectionAllowed(true);
        weatherTableUI.setRowHeight(30);
        jScrollPane2.setViewportView(weatherTableUI);
        weatherTableUI.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        eweather.switchboard(0);
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        showWeatherData();
    }//GEN-LAST:event_jButton1MouseClicked

    private void cityListUIValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cityListUIValueChanged
        for (int idx : cityListUI.getSelectedIndices()){
            City ct = cities.get(idx);
            System.out.println(ct.getId());
        }
        System.out.println("");
        System.out.println(cityListUI.getSelectedIndices().length);
    }//GEN-LAST:event_cityListUIValueChanged

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        eweather.getController().updateActualData();
        showWeatherData();
    }//GEN-LAST:event_jButton2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> cityListUI;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable weatherTableUI;
    // End of variables declaration//GEN-END:variables
}
