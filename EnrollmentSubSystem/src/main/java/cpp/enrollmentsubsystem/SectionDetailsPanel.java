/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import static cpp.enrollmentsubsystem.EnrollmentSubSystem.getSQLConnection;
import static cpp.enrollmentsubsystem.LoginPanel.currentStudentID;
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
import javax.swing.SwingUtilities;


/**
 *
 * @author lpera00
 */
public class SectionDetailsPanel extends JFrame{
        
    public SectionDetailsPanel(Section section, CourseCart cart,
            ArrayList<String> sectionIDs, ArrayList<String> courseIDs, 
            ArrayList<String> courseNames, String searchTerm, String searchCourseNum, boolean debug){
        //have to use final variables to avoid errors when passing to add function
        final Section displaySection = new Section();
        final CourseCart displayCart = new CourseCart();
        if(section == null){
            //mock data for testing
            displaySection.setTerm("Fall 2022");
            displaySection.setTime("MW 5:30PM-6:45PM");
            displaySection.setLocation("Building 8 Room 302");
            displaySection.setEnrollCapacity(40);
            displaySection.setWaitCapcity(10);
            Course course = new Course();
            course.setCourseID(3560);
            course.setName("Object Oriented Design");
            course.setUnits(3);
            Course prerequisite = new Course();
            prerequisite.setCourseID(2400);
            prerequisite.setName("Data Structures");
            prerequisite.setUnits(3);
            prerequisite.setPrerequisites(null);
            Course[] prerequisites = {prerequisite};
            course.setPrerequisites(prerequisites);
            Professor prof = new Professor();
            prof.setID(1);
            prof.setDepartment("Computer Science");
            prof.setName("Frank Collins");
            displaySection.setCourse(course);
            displaySection.setProfessor(prof);
            displaySection.setNumber(1);
        }
        else{
            //get data from parameters
            displaySection.setCourse(section.getCourse());
            displaySection.setEnrollCapacity(section.getEnrollCapcity());
            displaySection.setLocation(section.getLocation());
            displaySection.setWaitCapcity(section.getWaitCapacity());
            displaySection.setNumber(section.getNumber());
            displaySection.setProfessor(section.getProfessor());
            displaySection.setTerm(section.getTerm());
            displaySection.setTime(section.getTime());
        }
        if(cart == null){
            //mock data for testing
            Student student = new Student();
            student.setID(123);
            student.setName("Adam Smith");
            student.setEmail("asmith@cpp.edu");
            student.setMajor("Computer Science");
            student.setPassword("pw");
            displayCart.setStudent(student);
        }
        else{
            //get data from parameters
            displayCart.setStudent(cart.getStudent());
        }
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("Section Details");
        
        if(debug){
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
                
        JPanel container = new JPanel(null);
        //back button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close this window and reload search window
                String[] args = {};
                SearchResultPanel.main(args, sectionIDs, courseIDs, 
                        courseNames, searchTerm, searchCourseNum, debug);
                dispose();
            }
        });
        container.add(back);
        //title
        JLabel title = new JLabel();
        title.setText("Section Details");
        title.setBounds(250, 10, 150, 20);
        container.add(title);
        //course name
        JLabel courseNameLabel = new JLabel();
        courseNameLabel.setText("Course Name:");
        courseNameLabel.setBounds(165, 40, 100, 20);
        container.add(courseNameLabel);
        JLabel courseName = new JLabel();
        courseName.setText(displaySection.getCourse().getName());
        courseName.setBounds(300, 40, 200, 20);
        container.add(courseName);
        //course number
        JLabel courseNumLabel = new JLabel();
        courseNumLabel.setText("Course Number:");
        courseNumLabel.setBounds(165, 70, 100, 20);
        container.add(courseNumLabel);
        JLabel courseNum = new JLabel();
        courseNum.setText(((Integer)displaySection.getCourse().getCourseID()).toString());
        courseNum.setBounds(300, 70, 200, 20);
        container.add(courseNum);
        //section number
        JLabel sectionNumLabel = new JLabel();
        sectionNumLabel.setText("Section Number:");
        sectionNumLabel.setBounds(165, 100, 100, 20);
        container.add(sectionNumLabel);
        JLabel sectionNum = new JLabel();
        sectionNum.setText(((Integer)displaySection.getNumber()).toString());
        sectionNum.setBounds(300, 100, 200, 20);
        container.add(sectionNum);
        //time
        JLabel timeLabel = new JLabel();
        timeLabel.setText("Time:");
        timeLabel.setBounds(165, 130, 100, 20);
        container.add(timeLabel);
        JLabel time = new JLabel();
        time.setText(displaySection.getTime());
        time.setBounds(300, 130, 200, 20);
        container.add(time);
        //location
        JLabel locationLabel = new JLabel();
        locationLabel.setText("Location:");
        locationLabel.setBounds(165, 160, 100, 20);
        container.add(locationLabel);
        JLabel location = new JLabel();
        location.setText(displaySection.getLocation());
        location.setBounds(300, 160, 200, 20);
        container.add(location);
        //term
        JLabel termLabel = new JLabel();
        termLabel.setText("Term:");
        termLabel.setBounds(165, 190, 100, 20);
        container.add(termLabel);
        JLabel term = new JLabel();
        term.setText(displaySection.getTerm());
        term.setBounds(300, 190, 200, 20);
        container.add(term);
        //enrollment capacity
        JLabel enrollCapLabel = new JLabel();
        enrollCapLabel.setText("Enrollment Capacity:");
        enrollCapLabel.setBounds(165, 220, 150, 20);
        container.add(enrollCapLabel);
        JLabel enrollCap = new JLabel();
        enrollCap.setText(((Integer)displaySection.getEnrollCapcity()).toString());
        enrollCap.setBounds(300, 220, 200, 20);
        container.add(enrollCap);
        //#enrolled
        JLabel enrolledLabel = new JLabel();
        enrolledLabel.setText("Enrolled:");
        enrolledLabel.setBounds(400, 220, 150, 20);
        container.add(enrolledLabel);
        JLabel enrolled = new JLabel();
        //this info would be from the database
        int numEnrolled = 0;
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            sql = "SELECT COUNT(studentID) as num_enrolled FROM enrolled_classes WHERE enrolled_classes.sectionID = '"+displaySection.getNumber()+"'";
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("num enrolled: "+result.getString("num_enrolled"));
                    numEnrolled = Integer.parseInt(result.getString("num_enrolled"));
                }
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        if(numEnrolled > 0){
            //assuming numEnrolled includes total of both enrolled and waitlisted
            if(numEnrolled > displaySection.getEnrollCapcity()){
                enrolled.setText(((Integer)displaySection.getEnrollCapcity()).toString());
            }
            else{
                enrolled.setText(((Integer)numEnrolled).toString());
            }
        }
        else{
                enrolled.setText("0");
        }
        //enrolled.setText("30"); //for testing purposes we will hard code this for now
        enrolled.setBounds(500, 220, 200, 20);
        container.add(enrolled);
        //#waitlisted
        JLabel waitlistedLabel = new JLabel();
        waitlistedLabel.setText("Waitlisted:");
        waitlistedLabel.setBounds(400, 250, 150, 20);
        container.add(waitlistedLabel);
        JLabel waitlisted = new JLabel();
        if(numEnrolled > 0){
            //assuming numEnrolled includes total of both enrolled and waitlisted
            //so total - enrollment capacity = waitlisted
            //and the cap to not allow any additions is enrollment capacity + waitlist capacity
            if(numEnrolled > displaySection.getEnrollCapcity()){
                Integer wl = numEnrolled - displaySection.getEnrollCapcity();
                waitlisted.setText(wl.toString());
            }
            else{
                //in this system no one would be in the waitlist if #enrolled < enrollment capacity
                waitlisted.setText("0");
            }
        }
        else{
            waitlisted.setText("0");
        }
        waitlisted.setBounds(500, 250, 200, 20);
        container.add(waitlisted);
        //waitlist capacity
        JLabel waitCapLabel = new JLabel();
        waitCapLabel.setText("Waitlist Capacity:");
        waitCapLabel.setBounds(165, 250, 150, 20);
        container.add(waitCapLabel);
        JLabel waitCap = new JLabel();
        waitCap.setText(((Integer)displaySection.getWaitCapacity()).toString());
        waitCap.setBounds(300, 250, 200, 20);
        container.add(waitCap);
        //units
        JLabel unitLabel = new JLabel();
        unitLabel.setText("Units:");
        unitLabel.setBounds(165, 280, 100, 20);
        container.add(unitLabel);
        JLabel units = new JLabel();
        units.setText(((Integer)displaySection.getCourse().getUnits()).toString());
        units.setBounds(300, 280, 200, 20);
        container.add(units);
        //prerequisites
        JLabel prerequsitesLabel = new JLabel();
        prerequsitesLabel.setText("Prerequisites:");
        prerequsitesLabel.setBounds(165, 310, 100, 20);
        container.add(prerequsitesLabel);
        JLabel prerequsites = new JLabel();
        //if course has no prerequisites
        if(displaySection.getCourse().getPrerequisites() == null || displaySection.getCourse().getPrerequisites()[0].getCourseID() == 0  ){
            prerequsites.setText("none");
        }
        else{
            //display all prerequisites
            String prerequisiteList = "";
            for(int i = 0; i < displaySection.getCourse().getPrerequisites().length; i++){
                prerequisiteList += ((Integer)displaySection.getCourse().getPrerequisites()[i].getCourseID()).toString();
                //put commas/spaces if there is more than 1
                if(i < displaySection.getCourse().getPrerequisites().length - 1){
                    prerequisiteList += ", ";
                }
            }
           prerequsites.setText(prerequisiteList);
        }
        prerequsites.setBounds(300, 310, 200, 20);
        container.add(prerequsites);
        //professor
        JLabel profLabel = new JLabel();
        profLabel.setText("Professor:");
        profLabel.setBounds(165, 340, 100, 20);
        container.add(profLabel);
        JLabel prof = new JLabel();
        prof.setText(displaySection.getProfessor().getName());
        prof.setBounds(300, 340, 200, 20);
        container.add(prof);
        //add class button
        JButton add = new JButton("Add Class to Cart");
        add.setBounds(425, 10, 150, 20);
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                methods m = new methods();
                //m.addToCart(displaySection, currentStudentID);
                //only add the section if there are no conflicts
                String errorMsg = "";
                if(!m.checkConflicts(displaySection, currentStudentID, errorMsg)){
                    try{
                        Connection con;
                        con = getSQLConnection();
                        Statement statement = con.createStatement();
                        String sql = "";
                        sql = "INSERT INTO student_cart_entries (studentID, sectionID)" + 
                        " values " + 
                        " ("+ currentStudentID +", "+ displaySection.getNumber() +");";
                        statement.executeUpdate(sql);
                        JOptionPane.showMessageDialog( getParent(), 
                        "Section added to cart!\n"
                        + "Returning to search results", 
                        "Add section success", 
                        JOptionPane.INFORMATION_MESSAGE);
                        //close this window and reload search window
                        String[] args = {};
                        SearchResultPanel.main(args, sectionIDs, courseIDs, 
                                courseNames, searchTerm, searchCourseNum, debug);
                        dispose();
                    }catch (SQLException ex) {
                        System.err.println(ex.toString());
                        JOptionPane.showMessageDialog( getParent(), 
                        "Error adding section", 
                        "Add section error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        container.add(add);
        add(container);
    }
    
     public static void main(String[] args, Section section, CourseCart cart,
            ArrayList<String> sectionIDs, ArrayList<String> courseIDs, 
            ArrayList<String> courseNames, String searchTerm, String searchCourseNum, boolean debug){
        SwingUtilities.invokeLater( () -> {
            new SectionDetailsPanel(section, cart, sectionIDs, courseIDs, 
            courseNames, searchTerm, searchCourseNum, debug).setVisible(true);
        });
    }
}

