package org.eap.pli24.eweather.view;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 */
public class ImageRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
        JLabel lbl =((JLabel)super.getTableCellRendererComponent(table,"",isSelected,hasFocus,row,column));
    
        URL url = null;
        try{
            if (value != null){
                url = new URL((String)value);
                lbl.setIcon(new ImageIcon(url));
            }else{
                lbl.setIcon(null);
            }
        } catch (MalformedURLException ex){
            //Logger.getLogger(ImageRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    return lbl;
    }
}