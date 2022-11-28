package cpp.enrollmentsubsystem;

import static cpp.enrollmentsubsystem.EnrollmentSubSystem.getSQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class methods {

    public methods() {
        
    }

    public void createAccount(int id, String first_name, String last_name, String major){
        //create new student account
        //INSERT INTO students (studentID, first_Name, last_Name, major) VALUES (id, first_name, last_name, major);
    }

    public void logIn(String username, int password){
        //authenticate username and password input
        //password will be hashed and compared to the hashes stored in the databse
        //SELECT COUNT(student_ID) FROM students WHERE student_username = username AND student_password = password;
        ArrayList<Integer> matches = new ArrayList<Integer>(); //query should return an ArrayList with only 1 value
        if(matches.get(0) > 0){
            //login success
        }
        else{
            //error message
            //creates a new frame for the alert dialog to appear on
            //then destroys it when user clicks OK
            //(there's probably a better way to do this)
            JFrame alertFrame = new JFrame();
            alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            alertFrame.setVisible(false);
            JOptionPane.showMessageDialog(alertFrame, 
                "Username and/or password is incorrect", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            alertFrame.dispose();
        }
    }

    public void search(String term, String courseNum, String subject, String matchOption){
        //search for class by choosing a subject and inputting a course number
        try {
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            String[] args = {};
            if(courseNum.isBlank()){
                //if only term is chosen
                if(subject.equals("Select"))
                    sql = "SELECT sections.sectionID FROM sections WHERE sections.term = '" + term + "';";
                else
                    sql = "SELECT sections.sectionID FROM sections WHERE sections.term = '" + term + "' AND sections.subject = '"+ subject +"';";
                ResultSet result = statement.executeQuery(sql);
                ArrayList<String> sectionIDs = new ArrayList<String>();
                while(result.next()){
                    System.out.println("sectionID: "+result.getString("sectionID"));
                    sectionIDs.add(result.getString("sectionID"));
                }
                if(subject.equals("Select"))
                    sql = "SELECT sections.courseID FROM sections WHERE sections.term = '" + term + "';";
                else
                    sql = "SELECT sections.courseID FROM sections WHERE sections.term = '" + term + "' AND sections.subject = '"+ subject +"';";
                result = statement.executeQuery(sql);
                ArrayList<String> courseIDs = new ArrayList<String>();
                while(result.next()){
                    System.out.println("courseID: "+result.getString("courseID"));
                    courseIDs.add(result.getString("courseID"));
                }
                ArrayList<String> courseNames = new ArrayList<String>();
                for(int i = 0; i < courseIDs.size(); i++){
                    sql = "SELECT courses.course_Name FROM courses WHERE courses.courseID = '" + courseIDs.get(i) + "';";
                    result = statement.executeQuery(sql);
                    while(result.next()){
                        System.out.println("course name: "+result.getString("course_Name"));
                        courseNames.add(result.getString("course_Name"));
                    }
                }
                SearchResultPanel.main(args, sectionIDs, courseIDs, courseNames, term, courseNum);
            }
            else{ //if course num is also specified

                if(subject.equals("Select"))
                    sql = "SELECT sections.sectionID FROM sections WHERE sections.term = '" + term + "'";
                else
                    sql = "SELECT sections.sectionID FROM sections WHERE sections.term = '" + term + "' AND sections.subject = '"+ subject +"'";
                sql += " AND sections.courseID";
                switch (matchOption) {
                    case "is exactly":
                        sql += " = ";
                        break;
                    case "greater than or equal to":
                        sql += " >= ";
                        break;
                    case "less than or equal to":
                        sql += " <= ";
                        break;
                }
                sql += courseNum + ";";
                ResultSet result = statement.executeQuery(sql);
                ArrayList<String> sectionIDs = new ArrayList<String>();
                while(result.next()){
                    System.out.println("sectionID: "+result.getString("sectionID"));
                    sectionIDs.add(result.getString("sectionID"));
                }
                if(subject.equals("Select"))
                    sql = "SELECT sections.courseID FROM sections WHERE sections.term = '" + term + "'";
                else
                    sql = "SELECT sections.courseID FROM sections WHERE sections.term = '" + term + "' AND sections.subject = '"+ subject +"'";
                sql += " AND sections.courseID";
                switch (matchOption) {
                    case "is exactly":
                        sql += " = ";
                        break;
                    case "greater than or equal to":
                        sql += " >= ";
                        break;
                    case "less than or equal to":
                        sql += " <= ";
                        break;
                }
                sql += courseNum + ";";
                result = statement.executeQuery(sql);
                ArrayList<String> courseIDs = new ArrayList<String>();
                while(result.next()){
                    System.out.println("courseID: "+result.getString("courseID"));
                    courseIDs.add(result.getString("courseID"));
                }
                ArrayList<String> courseNames = new ArrayList<String>();
                for(int i = 0; i < courseIDs.size(); i++){
                    sql = "SELECT courses.course_Name FROM courses WHERE courses.courseID = '" + courseIDs.get(i) + "';";
                    result = statement.executeQuery(sql);
                    while(result.next()){
                        System.out.println("course name: "+result.getString("course_Name"));
                        courseNames.add(result.getString("course_Name"));
                    }
                }
                SearchResultPanel.main(args, sectionIDs, courseIDs, courseNames, term, courseNum);
            }
            con.close();
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void addToCart(Section section, CourseCart cart){
        //check if added section will cause any conflicts
        if (checkConflicts(section, cart) == false) {
            //if there are no conflicts, add section to cart
        }
        else{
            //error message
            JFrame alertFrame = new JFrame();
            alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            alertFrame.setVisible(false);
            JOptionPane.showMessageDialog(alertFrame, 
                        "Conflict found when attempting to add this class", 
                        "Conflict Found", 
                        JOptionPane.ERROR_MESSAGE);
            alertFrame.dispose();
        }
        System.out.println("add to cart called");
    }

    public boolean checkConflicts(Section section, CourseCart cart){
        //false = no conflict, true = conflict
        boolean conflictCheck = false;
        //all this student's currently and previously enrolled courses
        //("currently" or "previously" enrolled is denoted by the "term" attribute):
        //SELECT course_ID FROM enrolled_classes WHERE enrolled_classes.student_ID = '"+cart.getStudent()+"';
        ArrayList<Integer> enrolledCourses = new ArrayList<Integer>(); //query results will be added to ArrayLists
        //prerequisite conflict
            //get all the prerequisites from the seciton being checked's course:
            //SELECT prerequisite_ID FROM courses WHERE courses.course_ID = '"+section.getCourse().getCourseID()+"';
            ArrayList<Integer> coursePrerequisites = new ArrayList<Integer>();
            //only do this check if the course has prerequisites
            if(coursePrerequisites.size() > 0){
                //if the course has prerequisites and the student hasn't taken any other courses, there is already a conflict
                if(enrolledCourses.size() == 0){
                    conflictCheck = true;
                    return conflictCheck;
                }
                else{
                    //count the matches between prerequisites and the student's previously taken courses
                    //if the student has taken all prerequisites this number will = the number of prerequisites
                    int matchCount = 0; 
                    //for each prerequisite the course has, check if the student has taken that prerequisite
                    for(int i = 0; i < coursePrerequisites.size(); i++){
                        for(int j = 0; j < enrolledCourses.size(); j++){
                            if(enrolledCourses.get(j) == coursePrerequisites.get(i)){
                                matchCount++;
                            }
                        }
                    }
                    if(matchCount != coursePrerequisites.size()){
                        conflictCheck = true;
                        return conflictCheck;
                    }
                }
            }
        //schedule conflict
            //get the time for each section student is enrolled in this term 
            //("sections" is the database table, "section" is the section being checked):
            //SELECT time FROM sections INNER JOIN enrolled_classes ON sections.course_ID = enrolled_classes.course_ID 
            //AND sections.section_ID = enrolled_classes.section_ID AND enrolled_classes.student_ID = '"+cart.getStudent()+"' 
            //AND sections.term = '"+section.getTerm()+"';
            ArrayList<String> enrolledTimes = new ArrayList<String>();
            //compare each enrolled time this term to that of the section being checked; if any match, there is a conflict
            if(enrolledTimes.size() > 0){
                for(int i = 0; i < enrolledTimes.size(); i++){
                    if(enrolledTimes.get(i) == section.getTime()){
                        conflictCheck = true;
                        return conflictCheck;
                    }
                }
            }
        //max units conflict
            //get the units for each section student is enrolled in this term:
            //SELECT units FROM courses INNER JOIN enrolled_classes ON courses.course_ID = enrolled_classes.course_ID 
            //INNER JOIN course_section ON enrolled_classes.section_ID = course_section.section_ID 
            //AND enrolled_classes.course_ID = course_section.course_ID 
            //AND enrolled_classes.student_ID = '"+cart.getStudent()+"' AND course_section.term = '"+section.getTerm()+"';
            ArrayList<Integer> enrolledUnits = new ArrayList<Integer>();
            //add all units student is enrolled in this term to the units of the seciton being checked
            //if this total > max allowed units, there is a unit conflict
             if(enrolledUnits.size() > 0){
                int totalUnits = 0;
                final int MAX_UNITS = 16;
                for(int i = 0; i < enrolledUnits.size(); i++){
                    totalUnits += enrolledUnits.get(i);
                }
                totalUnits += section.getCourse().getUnits();
                if(totalUnits > MAX_UNITS){
                    conflictCheck = true;
                    return conflictCheck;
                }
            }
        //enrollment is full
        //(this checks if both enrollment and waitlist capacities are full, not each separately)
            //count number of students enrolled in this section
            //SELECT COUNT(student_ID) FROM enrolled_classes WHERE course_ID = '"+section.getCourse().getCourseID()+"' 
            //AND section_ID = '"+section.getNumber()+"';
            //this should return an ArrayList with only 1 value
            ArrayList<Integer> numEnrolled = new ArrayList<Integer>();
            //if this value + this student is > enrollmentCap + waitlistCap, there is a capacity conflict
            if(numEnrolled.size() > 0){
                if(numEnrolled.get(0) + 1 > section.getEnrollCapcity() + section.getWaitCapacity()){
                    conflictCheck = true;
                    return conflictCheck;
                }
            }
        //if there are no conflicts, this should return false
        return conflictCheck;
    }

    public void removeFromCart(String sectionID, String studentID){
        //remove selected section from student's cart
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            sql = "DELETE FROM student_cart_entries WHERE student_cart_entries.studentID = "
                    + "'"+studentID+"' AND student_cart_entries.sectionID = '"+sectionID+"';";
            statement.executeUpdate(sql);
            }catch (SQLException ex) {
                System.err.println(ex.toString());
            }
        System.out.println("remove from cart called");
    }

    public void viewCart(CourseCart cart) {
        
    }

    public void finalizeCart(CourseCart cart){
        System.out.println("finalize cart called");
        //iterate over all sections in student's cart and enroll student in each section
        if(cart != null){
            if(cart.courses.length > 0){
                for (int i = 0; i < cart.courses.length; i++) {
                    enroll(cart.student, cart.courses[i]);
                }
            }
        }
    }

    public void enroll(Student student, Section section){
        //enroll student in section
        //INSERT INTO enrolled_classes (student_ID, course_ID, section_ID)
        //VALUES ('"+student.getID()+"', '"+section.getCourse().getCourseID()+"', '"+section.getNumber()+"');
        System.out.println("enroll "+ student.getName()+" in "+section.getCourse().getName());
    }

    public void dropSection(Student student, Section section){
        //drop enrolled section
        //DELETE FROM enrolled_classes WHERE enrolled_classes.student_ID = '"+student.getID()+"'
        //AND enrolled_classes.course_ID = '"+section.getCourse().getCourseID()+"'
        //AND enrolled_classes.section_ID = '"+section.getNumber()+"';
    }

    public void viewSchedule(Student student){
        //display sections student is currently enrolled in
        //in order of time and date
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
