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

public class CartPanel extends JFrame{
    
    public CartPanel(String studentID){
        final CourseCart viewCart = new CourseCart();
        methods m = new methods();
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("View Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        JLabel title = new JLabel();
        title.setText("View Cart");
        title.setBounds(270, 20, 100, 20);
        add(title);
        //back button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close this window and open menu window
                String[] args = {};
                EnrollmentSubSystem.main(args);
                dispose();
            }
        });
        add(back);
        JPanel layoutPanel = new JPanel();
        layoutPanel.setBounds(20, 40, 550, 1000);
        layoutPanel.setLayout(null);
//        if(cart == null){
//            //mock data for testing
//            viewCart.setSections(setMockData());
//            Student testStudent = new Student();
//            testStudent.setEmail("asmith@cpp.edu");
//            testStudent.setID(1234);
//            testStudent.setMajor("CS");
//            testStudent.setName("Adam Smith");
//            testStudent.setPassword("pw");
//            viewCart.setStudent(testStudent);
//        }
//        else{
//            viewCart.setSections(cart.getSections());
//            viewCart.setStudent(cart.getStudent());
//        }
        ArrayList<String> sectionIDs = new ArrayList<String>();        
        ArrayList<String> courseIDs = new ArrayList<String>();
        ArrayList<String> courseNames = new ArrayList<String>();
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            sql = "SELECT student_cart_entries.sectionID FROM student_cart_entries WHERE student_cart_entries.studentID = '" + studentID + "';";
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                    System.out.println("sectionID: "+result.getString("sectionID"));
                    sectionIDs.add(result.getString("sectionID")); 
            }
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        if(sectionIDs.size() > 0){
            int offset = 80;
            //get courseIDs and course names
            for(int i = 0; i < sectionIDs.size(); i++){
                try{
                Connection con;
                con = getSQLConnection();
                Statement statement = con.createStatement();
                String sql = "";
                sql = "SELECT sections.courseID FROM sections WHERE sections.sectionID = '" + sectionIDs.get(i) + "';";
                ResultSet result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("courseID: "+result.getString("courseID"));
                        courseIDs.add(result.getString("courseID")); 
                }
                sql = "SELECT courses.course_Name FROM courses WHERE courses.courseID = '" + courseIDs.get(i) + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        System.out.println("course Name: "+result.getString("course_Name"));
                        courseNames.add(result.getString("course_Name")); 
                }
                }catch (SQLException ex) {
                    System.err.println(ex.toString());
                }
            }
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
                JButton remove = new JButton("Remove");
                String toRemove = sectionIDs.get(i);
                int position = i + 1;
                remove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        m.removeFromCart(toRemove, studentID);
                        //reset this window to display new info
                        String[] args = {};
                        CartPanel.main(args, studentID);
                        dispose();
                    }
                });
                remove.setBounds(420, 20, 80, 30);
                listItem.add(remove);
                layoutPanel.add(listItem);
            }
        }
        else{
            //no classes to display
            JLabel none = new JLabel();
            none.setText("no classes to display");
            none.setBounds(220, 100, 200, 20);
            layoutPanel.add(none);
        }
        layoutPanel.setPreferredSize(new Dimension(550,600));
        JScrollPane scrollPanel = new JScrollPane(layoutPanel);
        scrollPanel.setBounds(20, 40, 550, 250);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPanel);
        //remove all button
        JButton removeAll = new JButton("Remove All");
        removeAll.setBounds(200, 300, 100, 30);
        removeAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(sectionIDs.size() > 0){
                    for(int i = 0; i < sectionIDs.size(); i++){
                        String toRemove = sectionIDs.get(i);
                        m.removeFromCart(toRemove, studentID);
                    }
                    //remove all sections from cart
                    //viewCart.setSections(new Section[0]);
                    //reset this window to display new info
                    String[] args = {};
                    CartPanel.main(args, studentID);
                    dispose();
                }
                else{
                    JFrame alertFrame = new JFrame();
                    alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    alertFrame.setVisible(false);
                    JOptionPane.showMessageDialog(alertFrame, 
                        "No courses to remove!", 
                        "Remove error", 
                        JOptionPane.ERROR_MESSAGE);
                    alertFrame.dispose();
                }
            }
        });
        add(removeAll);
        //finalize button
        JButton finalize = new JButton("Finalize and Enroll All");
        finalize.setBounds(350, 300, 200, 30);
        finalize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //confirm/error dialog
                if(sectionIDs.size() > 0){
                    m.finalizeCart(studentID, sectionIDs);
                    JFrame alertFrame = new JFrame();
                    alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    alertFrame.setVisible(false);
                    JOptionPane.showMessageDialog(alertFrame, 
                        "All courses enrolled!", 
                        "Courses Enrolled", 
                        JOptionPane.INFORMATION_MESSAGE);
                    alertFrame.dispose();
                    //remove all section from cart
                    //viewCart.setSections(new Section[0]);
                    //reset this window to display new info
                    String[] args = {};
                    CartPanel.main(args, studentID);
                    dispose();
                }
                else{
                    JFrame alertFrame = new JFrame();
                    alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    alertFrame.setVisible(false);
                    JOptionPane.showMessageDialog(alertFrame, 
                        "No courses to enroll!", 
                        "Enroll error", 
                        JOptionPane.ERROR_MESSAGE);
                    alertFrame.dispose();
                }
            }
        });
        add(finalize);
    }
    public Section[] setMockData(){
        //set up mock data for 4 classes to display in this view
        //section 1
        Section section1 = new Section();
        section1.setTerm("Fall 2022");
        section1.setTime("MW 5:30PM-6:45PM");
        section1.setLocation("Building 8 Room 302");
        section1.setEnrollCapacity(40);
        section1.setWaitCapcity(10);
        Course course1 = new Course();
        course1.setCourseID(3560);
        course1.setName("Object Oriented Design");
        course1.setUnits(3);
        Course prerequisite1 = new Course();
        prerequisite1.setCourseID(2400);
        prerequisite1.setName("Data Structures");
        prerequisite1.setUnits(3);
        prerequisite1.setPrerequisites(null);
        Course[] prerequisites1 = {prerequisite1};
        course1.setPrerequisites(prerequisites1);
        Professor prof1 = new Professor();
        prof1.setID(1);
        prof1.setDepartment("Computer Science");
        prof1.setName("Frank Collins");
        section1.setCourse(course1);
        section1.setProfessor(prof1);
        section1.setNumber(1);
        //section 2
        Section section2 = new Section();
        section2.setTerm("Fall 2022");
        section2.setTime("MW 4:00PM-5:15PM");
        section2.setLocation("Building 15 Room 1807");
        section2.setEnrollCapacity(45);
        section2.setWaitCapcity(15);
        Course course2 = new Course();
        course2.setCourseID(1150);
        course2.setName("Basic Biology");
        course2.setUnits(3);
        course2.setPrerequisites(null);
        Professor prof2 = new Professor();
        prof2.setID(2);
        prof2.setDepartment("Biology");
        prof2.setName("Jose Pena");
        section2.setCourse(course2);
        section2.setProfessor(prof2);
        section2.setNumber(2);
        //section 3
        Section section3 = new Section();
        section3.setTerm("Fall 2022");
        section3.setTime("TuTh 1:00PM-2:15PM");
        section3.setLocation("Building 9 Room 401");
        section3.setEnrollCapacity(35);
        section3.setWaitCapcity(10);
        Course course3 = new Course();
        course3.setCourseID(1320);
        course3.setName("Discrete Structures");
        course3.setUnits(3);
        Course prerequisite2 = new Course();
        prerequisite2.setCourseID(2400);
        prerequisite2.setName("Data Structures");
        prerequisite2.setUnits(3);
        prerequisite2.setPrerequisites(null);
        Course prerequisite3 = new Course();
        prerequisite3.setCourseID(1140);
        prerequisite3.setName("Calculus I");
        prerequisite3.setUnits(3);
        prerequisite3.setPrerequisites(null);
        Course[] prerequisites2 = {prerequisite2, prerequisite3};
        course3.setPrerequisites(prerequisites2);
        Professor prof3 = new Professor();
        prof3.setID(3);
        prof3.setDepartment("Engineering");
        prof3.setName("Mary Smith");
        section3.setCourse(course3);
        section3.setProfessor(prof3);
        section3.setNumber(1);
        //section 4
        Section section4 = new Section();
        section4.setTerm("Fall 2022");
        section4.setTime("TuTh 2:30PM-3:45PM");
        section4.setLocation("Building 8 Room 4");
        section4.setEnrollCapacity(40);
        section4.setWaitCapcity(15);
        Course course4 = new Course();
        course4.setCourseID(4700);
        course4.setName("Game Development");
        course4.setUnits(3);
        course4.setPrerequisites(null);
        section4.setCourse(course4);
        section4.setProfessor(prof1);
        section4.setNumber(2);
        Section[] sections = {section1, section2, section3, section4};
        return sections;
    }
    
    public static void main(String[] args, String studentID){
        SwingUtilities.invokeLater( () -> {
            new CartPanel(studentID).setVisible(true);
        });
    }
}
