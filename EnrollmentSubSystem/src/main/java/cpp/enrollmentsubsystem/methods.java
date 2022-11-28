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

    /**
     * 
     * @param username
     * @param password 
     */
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

    /**
     * 
     * @param term
     * @param courseNum
     * @param subject
     * @param matchOption 
     */
    public void search(String term, String courseNum, String subject, String matchOption, boolean debug){
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
                
                SearchResultPanel.main(args, sectionIDs, courseIDs, courseNames, term, courseNum , debug);
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
                SearchResultPanel.main(args, sectionIDs, courseIDs, courseNames, term, courseNum, debug);
            }
            con.close();
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void addToCart(Section section, String studentID){
        //check if added section will cause any conflicts
        if (checkConflicts(section, studentID, "") == false) {
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

    public boolean checkConflicts(Section section, String studentID, String errorMsg){
        //false = no conflict, true = conflict
        boolean conflictCheck = false;
        //convert everything to Section class objects
        ArrayList<String> sections = new ArrayList<String>();             
        ArrayList<String> courses = new ArrayList<String>();    
        ArrayList<String> courseNames = new ArrayList<String>(); 
        ArrayList<Section> allSections = new ArrayList<Section>();
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            //enrolled and in cart section IDs
            sql = "SELECT student_cart_entries.sectionID FROM student_cart_entries WHERE student_cart_entries.studentID = '"+studentID+"'";
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                    sections.add(result.getString("sectionID"));
                }
            sql = "SELECT enrolled_classes.sectionID FROM enrolled_classes WHERE enrolled_classes.studentID = '"+studentID+"'";
            result = statement.executeQuery(sql);
            while(result.next()){
                    sections.add(result.getString("sectionID"));
                }
            //course IDs
            if(sections.size() > 0){
                for(int i = 0; i < sections.size(); i++){
                    sql = "SELECT sections.courseID FROM sections WHERE sections.sectionID = '"+sections.get(i)+"'";
                    result = statement.executeQuery(sql);
                    while(result.next()){
                        courses.add(result.getString("courseID"));
                    }
                }
            }
            //course names
            if(courses.size() > 0){
                for(int i = 0; i < courses.size(); i++){
                    sql = "SELECT courses.course_Name FROM courses WHERE courses.courseID = '"+courses.get(i)+"'";
                    result = statement.executeQuery(sql);
                    while(result.next()){
                        courseNames.add(result.getString("course_Name"));
                    }
                }
            }
            if(sections.size() > 0){
                for(int i = 0; i < sections.size(); i++){
                    allSections.add(setSection(sections.get(i), courses.get(i), courseNames.get(i)));
                }
            }
            //schedule conflict
            //compare time and term of all sections enrolled and in cart to section being checked
            //if any are exactly the same, there is a conflict
            if(allSections.size() > 0){
                for(int i = 0; i < allSections.size(); i++){
                    if(allSections.get(i).getTime().equals(section.getTime()) 
                        && allSections.get(i).getTerm().equals(section.getTerm())){
                        System.out.println("schedule conflict");
                        errorMsg = "schedule conflict";
                        conflictCheck = true;
                        return conflictCheck;
                    }
                }
            }
            //max units conflict
            //add up units of all courses enrolled and in cart and add units of section being checked
            //if this is greater than the max num of units, there is a conflict
            int totalUnits = 0;
            int maxUnits = 16; //hard-coded max unit value
            if(allSections.size() > 0){
                for(int i = 0; i < allSections.size(); i++){
                    totalUnits += allSections.get(i).getCourse().getUnits();
                }
                if(totalUnits + section.getCourse().getUnits() > maxUnits){
                    System.out.println("max unit conflict");
                    errorMsg = "max unit conflict";
                    conflictCheck = true;
                    return conflictCheck;
                }
            }
            //enroll cap conflict
            //combines enrollment and waitlist capacities of section being checked into 1 max value
            //if adding this student would increase enrollment above that value,
            //there is a conflict
            int numEnrolled = 0;
            sql = "SELECT COUNT(studentID) as num_enrolled FROM enrolled_classes WHERE enrolled_classes.sectionID = '"+section.getNumber()+"'";
            result = statement.executeQuery(sql);
            while(result.next()){
                System.out.println("num enrolled: "+result.getString("num_enrolled"));
                numEnrolled = Integer.parseInt(result.getString("num_enrolled"));
            }
            if(numEnrolled + 1 > section.getEnrollCapcity() + section.getWaitCapacity()){
                System.out.println("enroll cap conflict");
                errorMsg = "enroll cap conflict";
                conflictCheck = true;
                return conflictCheck;
            }
            //prerequisite conflict
            //checks enrolled and in cart for the prerequisite of the section being checked
            //(if it has one). if it doesn't find the prerequisite, there is a conflict
            //(this technically counts all prerequisites as corequisites)
            //matches should = # of prerequisites if all prerequisites are enrolled/in cart
            int matches = 0;
            if(section.getCourse().getPrerequisites()!=null && section.getCourse().getPrerequisites()[0].getCourseID() != 0){
                //for every prerequisite of this section
                for(int i = 0; i < section.getCourse().getPrerequisites().length; i++){
                    //check if it matches any class enrolled/in cart
                   for(int j = 0; j < allSections.size(); j++){
                       if(section.getCourse().getPrerequisites()[i].getCourseID() ==
                         allSections.get(j).getCourse().getCourseID()){
                           matches++;
                       }
                    } 
                }
                if(matches < section.getCourse().getPrerequisites().length){
                    System.out.println("prerequisite conflict");
                    errorMsg = "prerequisite conflict";
                    conflictCheck = true;
                    return conflictCheck;
                }
            }
            
        }catch (SQLException ex) {
            System.err.println(ex.toString());
            JOptionPane.showMessageDialog( null, 
                            "Error adding section", 
                            "Schedule conflict!", 
                            JOptionPane.ERROR_MESSAGE);
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

    public void finalizeCart(String studentID, ArrayList<String> sectionIDs){
        System.out.println("finalize cart called");
        //moves sections in cart table to enrolled table
        //and remove all secitons in cart table
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "";
            if(sectionIDs.size() > 0){
                for(int i = 0; i < sectionIDs.size(); i++){
                    sql = "INSERT INTO enrolled_classes (sectionID, studentID) VALUES ('"+sectionIDs.get(i)+"','"+studentID+"')";
                    statement.executeUpdate(sql);
                    removeFromCart(sectionIDs.get(i), studentID);
                }
            }
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void enroll(Student student, Section section){
        //enroll student in section
        //INSERT INTO enrolled_classes (student_ID, course_ID, section_ID)
        //VALUES ('"+student.getID()+"', '"+section.getCourse().getCourseID()+"', '"+section.getNumber()+"');
        System.out.println("enroll "+ student.getName()+" in "+section.getCourse().getName());
    }

    public void dropSection(String studentID, String sectionID){
        //drop enrolled section
        //DELETE FROM enrolled_classes WHERE enrolled_classes.student_ID = '"+student.getID()+"'
        //AND enrolled_classes.course_ID = '"+section.getCourse().getCourseID()+"'
        //AND enrolled_classes.section_ID = '"+section.getNumber()+"';
        try{
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql = "DELETE FROM enrolled_classes WHERE enrolled_classes.studentID = '" + studentID + "'" + " AND enrolled_classes.sectionID = '" + sectionID + "';";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
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
                    //System.out.println("term: "+result.getString("term"));
                    //these queries should only return 1 value so no need for arraylists
                    section.setTerm(result.getString("term")); 
            }
            //units
            sql = "SELECT courses.units FROM courses WHERE courses.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    //System.out.println("units: "+result.getString("units"));
                    course.setUnits(Integer.parseInt(result.getString("units"))); 
            }
            /**
            //prerequisites
            ArrayList<Course> prerequisiteList = new ArrayList<Course>();
            sql = "SELECT courses.prerequisiteID FROM courses WHERE courses.courseID = '" + courseID + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                //System.out.println("term: "+result.getString("term"));
                Course nextPrerequisite = new Course();
                //only really need ID for prerequisites
                //all other info can be gathered from ID if needed
                if(!result.getString("prerequisiteID").isBlank()){
                nextPrerequisite.setCourseID(Integer.parseInt(result.getString("prerequisiteID"))); 
                }
                prerequisiteList.add(nextPrerequisite);
            }
            
            //convert arraylist to array
            //(there's probably a better way to do this)
            if(!prerequisiteList.isEmpty()){
                Course[] prerequisites = new Course[prerequisiteList.size()];
                for(int i = 0; i < prerequisiteList.size(); i++){
                    prerequisites[i] = prerequisiteList.get(i);
                }
                course.setPrerequisites(prerequisites);
            }
            */
            
            section.setCourse(course);
            //professorID
            sql = "SELECT sections.professorID FROM sections WHERE sections.sectionID = '" + sectionID + "' AND sections.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    //System.out.println("profID: "+result.getString("professorID"));
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
            //roomID
            sql = "SELECT sections.roomID FROM sections WHERE sections.sectionID = '" + sectionID + "' AND sections.courseID = '"+ courseID +"';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    //System.out.println("room: "+result.getString("roomID"));
                    section.setLocation(result.getString("roomID")); 
            }
            //enrollment capacity
            sql = "SELECT rooms.enrollment_Capacity FROM rooms WHERE rooms.roomID = '" + section.getLocation() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    //System.out.println("enrollCap: "+result.getString("enrollment_Capacity"));
                    section.setEnrollCapacity(Integer.parseInt(result.getString("enrollment_Capacity"))); 
            }
            //waitlist capacity
            sql = "SELECT rooms.wait_List_Capacity FROM rooms WHERE rooms.roomID = '" + section.getLocation() + "';";
            result = statement.executeQuery(sql);
            while(result.next()){
                    //System.out.println("WLCap: "+result.getString("wait_List_Capacity"));
                    section.setWaitCapcity(Integer.parseInt(result.getString("wait_List_Capacity"))); 
            }
            //time
                //days
                String time = ""; //final string will show entire schedule of this section
                sql = "SELECT section_schedules.Monday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onMonday: "+result.getBoolean("Monday"));
                        if(result.getBoolean("Monday")){
                            time += "M";
                        }
                }
                sql = "SELECT section_schedules.Tuesday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onTuesday: "+result.getBoolean("Tuesday"));
                        if(result.getBoolean("Tuesday")){
                            time += "Tu";
                        }
                }
                sql = "SELECT section_schedules.Wednesday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onWednesday: "+result.getBoolean("Wednesday"));
                        if(result.getBoolean("Wednesday")){
                            time += "W";
                        }
                }
                sql = "SELECT section_schedules.Thursday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onThursday: "+result.getBoolean("Thursday"));
                        if(result.getBoolean("Thursday")){
                            time += "Th";
                        }
                }
                sql = "SELECT section_schedules.Friday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onFriday: "+result.getBoolean("Friday"));
                        if(result.getBoolean("Friday")){
                            time += "F";
                        }
                }
                sql = "SELECT section_schedules.Saturday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                       // System.out.println("onSaturday: "+result.getBoolean("Saturday"));
                        if(result.getBoolean("Saturday")){
                            time += "Sa";
                        }
                }
                sql = "SELECT section_schedules.Sunday FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("onSunday: "+result.getBoolean("Sunday"));
                        if(result.getBoolean("Sunday")){
                            time += "Su";
                        }
                }
                sql = "SELECT section_schedules.start_time FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("start time: "+result.getString("start_time"));
                        time += " " + result.getString("start_time") + "-"; 
                }
                sql = "SELECT section_schedules.end_time FROM section_schedules WHERE section_schedules.sectionID = '" + sectionID + "';";
                result = statement.executeQuery(sql);
                while(result.next()){
                        //System.out.println("end time: "+result.getString("end_time"));
                        time += result.getString("end_time");
                }
                section.setTime(time);
                //System.out.println("time: "+time);
        }catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return section;
    }


}
