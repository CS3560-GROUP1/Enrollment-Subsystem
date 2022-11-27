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
    
    public SearchResultPanel(ArrayList<String> sectionIDs, ArrayList<String> courseIDs, ArrayList<String> courseNames, String searchTerm, String searchCourseNum){
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("Search Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
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
                String[] args = {};
                SearchPanel.main(args);
                dispose();
            }
        });
        add(back);
        JPanel layoutPanel = new JPanel();
        layoutPanel.setBounds(20, 40, 550, 1000);
        layoutPanel.setLayout(null);
        if(sectionIDs.size() > 0){
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
                        SectionDetailsPanel.main(args, setSection(chosenSectionID, chosenCourseID, chosenCourseName), null, 
                        sectionIDs, courseIDs, courseNames, searchTerm, searchCourseNum);
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
    
    public static void main(String[] args, ArrayList<String> sectionIDs, ArrayList<String> courseIDs, ArrayList<String> courseNames, String searchTerm, String searchCourseNum){
        SwingUtilities.invokeLater( () -> {
            new SearchResultPanel(sectionIDs, courseIDs, courseNames, searchTerm, searchCourseNum).setVisible(true);
        });
    }
    
    public Section setSection(String sectionID, String courseID, String course_name){
        //this function is meant to copy info from the database into the class format
        //so the details screen can display it
        Section section = new Section();
        Course course = new Course();
        Professor prof = new Professor();
        try {
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            //sectionID, courseID, course name
            section.setNumber(Integer. parseInt(sectionID));
            course.setCourseID(Integer. parseInt(courseID));
            course.setName(course_name);
            //term
            sql = "SELECT sections.term FROM sections WHERE sections.sectionID = '" + sectionID + "' AND sections.courseID = '"+ courseID +"';";
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("term: "+result.getString("term"));
                    //these queries should only return 1 value so no need for arraylists
                    section.setTerm(result.getString("term")); 
            }
            //units
            sql = "SELECT courses.units FROM courses WHERE courses.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("units: "+result.getString("units"));
                    course.setUnits(Integer.parseInt(result.getString("units"))); 
            }
            section.setCourse(course);
            //professorID
            sql = "SELECT sections.professorID FROM sections WHERE sections.sectionID = '" + sectionID + "' AND sections.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("profID: "+result.getString("professorID"));
                    prof.setID(Integer.parseInt(result.getString("professorID"))); 
            }
            //professor name
            String prof_name = ""; //combine first and last name into 1 string
            //first name
            sql = "SELECT professors.first_Name FROM professors WHERE professors.professorID = '" + prof.getID() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    prof_name = result.getString("first_Name");
            }
            //last name
            sql = "SELECT professors.last_Name FROM professors WHERE professors.professorID = '" + prof.getID() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    prof_name += " " + result.getString("last_Name");
            }
            //set professor name
            prof.setName(prof_name);
            section.setProfessor(prof);
            //----------------above working below not working-----------------------
            //roomID
            sql = "SELECT sections.roomID FROM sections WHERE sections.sectionID = '" + sectionID + "' AND sections.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("room: "+result.getString("roomID"));
                    section.setLocation(result.getString("roomID")); 
            }
            //enrollment capacity
            sql = "SELECT rooms.enrollment_Capacity FROM rooms WHERE rooms.roomID = '" + section.getLocation() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("enrollCap: "+result.getString("enrollment_Capacity"));
                    section.setEnrollCapacity(Integer.parseInt(result.getString("enrollment_Capacity"))); 
            }
            //waitlist capacity
            sql = "SELECT rooms.wait_List_Capacity FROM rooms WHERE rooms.roomID = '" + section.getLocation() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("WLCap: "+result.getString("wait_List_Capacity"));
                    section.setWaitCapcity(Integer.parseInt(result.getString("wait_List_Capacity"))); 
            }
            //time
                //days
                String time = ""; //final string will show entire schedule of this section
                sql = "SELECT section_schedules.Monday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onMonday: "+result.getBoolean("Monday"));
                        if(result.getBoolean("Monday")){
                            time += "M";
                        }
                }
                sql = "SELECT section_schedules.Tuesday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onTuesday: "+result.getBoolean("Tuesday"));
                        if(result.getBoolean("Tuesday")){
                            time += "Tu";
                        }
                }
                sql = "SELECT section_schedules.Wednesday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onWednesday: "+result.getBoolean("Wednesday"));
                        if(result.getBoolean("Wednesday")){
                            time += "W";
                        }
                }
                sql = "SELECT section_schedules.Thursday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onThursday: "+result.getBoolean("Thursday"));
                        if(result.getBoolean("Thursday")){
                            time += "Th";
                        }
                }
                sql = "SELECT section_schedules.Friday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onFriday: "+result.getBoolean("Friday"));
                        if(result.getBoolean("Friday")){
                            time += "F";
                        }
                }
                sql = "SELECT section_schedules.Saturday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onSaturday: "+result.getBoolean("Saturday"));
                        if(result.getBoolean("Saturday")){
                            time += "Sa";
                        }
                }
                sql = "SELECT section_schedules.Sunday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("onSunday: "+result.getBoolean("Sunday"));
                        if(result.getBoolean("Sunday")){
                            time += "Su";
                        }
                }
                sql = "SELECT section_schedules.start_time FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("start time: "+result.getString("start_time"));
                        time += " " + result.getString("start_time") + "-"; 
                }
                sql = "SELECT section_schedules.end_time FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("end time: "+result.getString("end_time"));
                        time += result.getString("end_time");
                }
                section.setTime(time);
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return section;
    }
}
