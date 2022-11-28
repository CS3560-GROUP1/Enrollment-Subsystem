/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;  
import java.awt.event.*;   
/**
 *
 * @author LeothEcRz
 */
public class UserSchedulesInnerPanel extends JPanel {
    
    
    public UserSchedulesInnerPanel(Dimension size, String course_Name, String subject, String term, int units, String professor, String ID, Vector<String> dropList){
        super();
        setBackground(new Color(Integer.parseInt(ID) * 10, Integer.parseInt(ID) * 20, Integer.parseInt(ID) * 15));
        
        setLayout(new GridBagLayout());
        GridBagConstraints c =new GridBagConstraints();
        
        c.ipadx = (int)(size.getWidth() * 0.03);
        
        JLabel c_nameLabel = new JLabel(course_Name);
        c.gridx = 1;
        c.gridy = 1;
        
        add(c_nameLabel, c);
        
        JLabel subjectLabel = new JLabel(subject);
        c.gridx = 1;
        c.gridy = 3;
        
        add(subjectLabel, c);
        
        JLabel termLabel = new JLabel(term);
        c.gridx = 3;
        c.gridy = 1;
        
        add(termLabel, c);
        
        JLabel unitLabel = new JLabel("Units: ".concat(String.valueOf(units)));
        c.gridx = 3;
        c.gridy = 3;
        
        add(unitLabel , c);
        
        JLabel profJLabel = new JLabel(professor);
        c.gridx = 5;
        c.gridy = 1;
        
        add(profJLabel, c);
        
        JCheckBox checkbox = new JCheckBox();
        c.gridx = 7;
        c.gridy = 1;
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (cb.isSelected()) {
                    dropList.add(ID);
                } else {
                    dropList.removeElementAt(dropList.indexOf(ID));
                }
            }
        });
        add(checkbox, c);
        
        
    }
    /**
    * JPanel usePanel; // Will be moved to its own panel class
   for(int i=0; i< entries; i++){
   usePanel = new JPanel();
   usePanel.setBackground(new Color(i*5, i*10, i*12));

   innerBottomPanel.add(usePanel);
    */
    
}
