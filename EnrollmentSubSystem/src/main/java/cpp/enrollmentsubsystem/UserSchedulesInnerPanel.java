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
import java.sql.Time;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;   
/**
 *
 * @author LeothEcRz
 */
public class UserSchedulesInnerPanel extends JPanel {
    
    
    public UserSchedulesInnerPanel(Dimension size, String course_Name, String subject, String term, int units, String professor, String ID, Vector<String> dropList, Time start_time, Time end_time, boolean Monday, boolean Tuesday,  boolean Wednesday,  boolean Thursday,  boolean Friday,  boolean Saturday, boolean Sunday){
        super();
        setBackground(new Color(Integer.parseInt(ID) * 5, Integer.parseInt(ID) * 10, Integer.parseInt(ID) * 7));
        
        setLayout(new GridBagLayout());
        GridBagConstraints c =new GridBagConstraints();
        
        
        
        c.ipadx = (int)(size.getWidth() * 0.03);
        
        JLabel c_nameLabel = new JLabel(course_Name);
        c.gridx = 1;
        c.gridy = 1;
        c_nameLabel.setForeground(Color.WHITE);
        
        add(c_nameLabel, c);
        
        JLabel subjectLabel = new JLabel(subject);
        c.gridx = 1;
        c.gridy = 3;
        subjectLabel.setForeground(Color.WHITE);

        
        add(subjectLabel, c);
        
        JLabel termLabel = new JLabel(term);
        c.gridx = 3;
        c.gridy = 1;
        termLabel.setForeground(Color.WHITE);

        
        add(termLabel, c);
        
        JLabel unitLabel = new JLabel("Units: ".concat(String.valueOf(units)));
        c.gridx = 3;
        c.gridy = 3;
        unitLabel.setForeground(Color.WHITE);
        
        add(unitLabel , c);
        
        JLabel profJLabel = new JLabel(professor);
        c.gridx = 5;
        c.gridy = 1;
        profJLabel.setForeground(Color.WHITE);
        
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
        JLabel startTimeJLabel = new JLabel( "Start Time: ".concat(start_time.toString()) );
        c.gridx = 1;
        c.gridy = 2;
        startTimeJLabel.setForeground(Color.WHITE);
        
        add(startTimeJLabel, c);
        
        JLabel endTimeJLabel = new JLabel( "End Time: ".concat(end_time.toString()) );
        c.gridx = 3;
        c.gridy = 2;
        endTimeJLabel.setForeground(Color.WHITE);
        
        add(endTimeJLabel, c);
        
        JLabel meetsJLabel = new JLabel( "Meets: " );
        c.gridx = 4;
        c.gridy = 2;
        meetsJLabel.setForeground(Color.WHITE);
        
        add(meetsJLabel, c);
        
        
        if(Monday){
            JLabel mondayJLabel = new JLabel("M");
            c.gridx = 5;
            c.gridy = 2;
            mondayJLabel.setForeground(Color.WHITE);
            
            add(mondayJLabel,c);
        }
        if(Tuesday){
            JLabel tuesdayJLabel = new JLabel("T");
            c.gridx = 6;
            c.gridy = 2;
                        tuesdayJLabel.setForeground(Color.WHITE);

            add(tuesdayJLabel,c);
        }
        if(Wednesday){
            JLabel wednesdayJLabel = new JLabel("W");
            c.gridx = 7;
            c.gridy = 2;
                        wednesdayJLabel.setForeground(Color.WHITE);

            add(wednesdayJLabel,c);
        }
        if(Thursday){
            JLabel thursdayJLabel = new JLabel("Th");
            c.gridx = 8;
            c.gridy = 2;
                        thursdayJLabel.setForeground(Color.WHITE);

            add(thursdayJLabel,c);
        }
        if(Friday){
            JLabel fridayJLabel = new JLabel("F");
            c.gridx = 9;
            c.gridy = 2;
                        fridayJLabel.setForeground(Color.WHITE);

            add(fridayJLabel,c);
        }
        if(Saturday){
            JLabel saturdayJLabel = new JLabel("Sa");
            c.gridx = 10;
            c.gridy = 2;
                        saturdayJLabel.setForeground(Color.WHITE);

            add(saturdayJLabel,c);
        }
        if(Sunday){
            JLabel sundayJLabel = new JLabel("Su");
            c.gridx = 11;
            c.gridy = 2;
                        sundayJLabel.setForeground(Color.WHITE);

            add(sundayJLabel,c);
        }
        
    }
}
