package cpp.enrollmentsubsystem;

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
    }

    public boolean checkConflicts(Section section, CourseCart cart){
        boolean conflictCheck = false;
        //prerequisite conflict
            //for each prerequisite the section's course has
            //iterate through the student's previously taken courses
            //if none match the prerequisite:
                conflictCheck = true;
        //schedule conflict
            //iterate through student's currently enrolled sections 
            //and sections currently in the student's cart
            //if any are during the same time on the same day:
                conflictCheck = true;
        //max units conflict
            /*iterate through student's currently enrolled sections
            and sections currently in the student's cart
            add units of these sections together
            as well as the units of the section the student is trying to add
            if this number > max number of units:*/
                conflictCheck = true;
        //enrollment is full
            //if student is trying to enroll rather than waitlist
            //and the enrollment total for the class == its enrollment capacity:
                conflictCheck = true;
        //waitlist is full 
            //if student is trying to waitlist
            //and the waitlist total for the class == its waitlist capacity:
                conflictCheck = true;
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
