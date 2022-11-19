package cpp.enrollmentsubsystem;

import java.util.ArrayList;

public class methods {

    public methods() {
        
    }

    public void createAccount(){
        //create new student account
    }

    public void logIn(String username, String password){
        //authenticate username and password input
        //password will be hashed and compared to the hashes stored in the databse
    }

    public void search(String keyword){
        //search for class using keyword input
    }

    public void addToCart(Section section, CourseCart cart){
        //check if added section will cause any conflicts
        if (checkConflicts(section, cart) == false) {
            //if there are no conflicts, add section to cart
            //at cart.courses[i] where i is the next empty slot in the array
        }
        else{
            //error message
        }
    }

    public boolean checkConflicts(Section section, CourseCart cart){
        //false = no conflict, true = conflict
        boolean conflictCheck = false;
        //all this student's currently and previously enrolled courses
        //("currently" or "previously" enrolled is denoted by the "term" attribute):
        //SELECT course_ID FROM enrollment WHERE enrollment.student_ID = cart.getStudent();
        ArrayList<Integer> enrolledCourses = new ArrayList<Integer>(); //query results will be added to ArrayLists
        //prerequisite conflict
            //get all the prerequisites from the seciton being checked's course:
            //SELECT prerequisite_ID FROM course WHERE course.course_ID = section.getCourse().getCourseID();
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
            //("course_section" is the database table, "section" is the section being checked):
            //SELECT time FROM course_section INNER JOIN enrollment ON course_section.course_ID = enrollment.course_ID 
            //AND course_section.section_ID = enrollment.section_ID AND enrollment.student_ID = cart.getStudent() 
            //AND course_section.term = section.getTerm();
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
            //SELECT units FROM course INNER JOIN enrollment ON course.course_ID = enrollment.course_ID 
            //INNER JOIN course_section ON enrollment.section_ID = course_section.section_ID 
            //AND enrollment.course_ID = course_section.course_ID 
            //AND enrollment.student_ID = cart.getStudent() AND course_section.term = section.getTerm();
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
            //SELECT COUNT(student_ID) FROM enrollment WHERE course_ID = section.getCourse().getCourseID() 
            //AND section_ID = section.getNumber();
            //this should return an ArrayList with only 1 value
            ArrayList<Integer> numEnrolled = new ArrayList<Integer>();
            //if this value + this student is > enrollmentCap + waitlistCap, there is a capacity conflict
            if(numEnrolled.get(0) + 1 > section.getEnrollCapcity() + section.getWaitCapacity()){
                conflictCheck = true;
                return conflictCheck;
            }
        //if there are no conflicts, this should return false
        return conflictCheck;
    }

    public void removeFromCart(Section section, CourseCart cart){
        //remove selected section from student's cart
    }

    public void viewCart(CourseCart cart) {
        //display the cart's contents
    }

    public void finalizeCart(CourseCart cart){
        //iterate over all sections in student's cart and enroll student in each section
        for (int i = 0; i < cart.courses.length; i++) {
            enroll(cart.student, cart.courses[i]);
        }
    }

    public void enroll(Student student, Section section){
        //enroll student in section
    }

    public void dropSection(Student student, Section section){
        //drop enrolled section
    }

    public void viewSchedule(Student student){
        //display sections student is currently enrolled in
        //in order of time and date
    }


}
