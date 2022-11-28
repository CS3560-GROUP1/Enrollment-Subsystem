/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import static cpp.enrollmentsubsystem.EnrollmentSubSystem.getSQLConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class SearchResultPanel extends JFrame{
    
    public SearchResultPanel(ArrayList<String> sectionIDs, ArrayList<String> courseIDs, ArrayList<String> courseNames, String searchTerm, String searchCourseNum, boolean debug){
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("Search Results");
        if(debug){
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }        setLayout(null);
        JLabel title = new JLabel();
        String search = "Term: " + searchTerm;
        if(!searchCourseNum.isBlank()){
            search += " Course Number: " + searchCourseNum;
        }
        title.setText("Results for search: " + search);
        title.setBounds(100, 20, 500, 20);
        add(title);
        //back button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close this window and return to search window
                new SearchPanel(debug).setVisible(true);
                dispose();
                
            }
        });
        add(back);
        JPanel layoutPanel = new JPanel();
        layoutPanel.setBounds(20, 40, 550, 1000);
        layoutPanel.setLayout(null);
        if(!sectionIDs.isEmpty()){
            int offset = 80;
            
            //display all classes in cart
            for(int i = 0; i < sectionIDs.size(); i++){
                JPanel listItem = new JPanel(null);
                listItem.setBackground(Color.white);
                JLabel courseNum = new JLabel(); 
                courseNum.setBounds(10, 20, 100, 20);
                JLabel courseName = new JLabel();
                courseName.setBounds(50, 20, 200, 20);
                JLabel sectionNum = new JLabel();
                sectionNum.setBounds(300, 20, 100, 20);
                listItem.setBounds(10, 10+i*offset, 525, 70);
                courseNum.setText(courseIDs.get(i));
                courseName.setText(courseNames.get(i));
                sectionNum.setText("section "+ sectionIDs.get(i));
                listItem.add(courseNum);                
                listItem.add(courseName);
                listItem.add(sectionNum);
                JButton details = new JButton("Details");
                String chosenSectionID = sectionIDs.get(i);                
                String chosenCourseID = courseIDs.get(i);
                String chosenCourseName = courseNames.get(i);
                details.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        //close this window and open section details window
                        //section details window needs the arraylists to recreate the search result panel
                        String[] args = {};
                        methods m = new methods();
                        SectionDetailsPanel.main(args, m.setSection(chosenSectionID, chosenCourseID, chosenCourseName), null, 
                        sectionIDs, courseIDs, courseNames, searchTerm, searchCourseNum, debug);
                        dispose();
                    }
                });
                details.setBounds(420, 20, 80, 30);
                listItem.add(details);
                layoutPanel.add(listItem);
            }
        }
        else{
            //no classes to display
            JLabel none = new JLabel();
            none.setText("no classes matched this search");
            none.setBounds(170, 100, 200, 20);
            layoutPanel.add(none);
        }
        layoutPanel.setPreferredSize(new Dimension(550,600));
        JScrollPane scrollPanel = new JScrollPane(layoutPanel);
        scrollPanel.setBounds(20, 40, 550, 250);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPanel);
    }
    
    public static void main(String[] args, ArrayList<String> sectionIDs, ArrayList<String> courseIDs, ArrayList<String> courseNames, String searchTerm, String searchCourseNum, boolean debug){
        SwingUtilities.invokeLater( () -> {
            new SearchResultPanel(sectionIDs, courseIDs, courseNames, searchTerm, searchCourseNum, debug).setVisible(true);
        });
    }
    
}
