package cpp.enrollmentsubsystem;

public class Section {
    
    private Course course;
    private int number;
    private Professor prof;
    private int enrollment_capacity;
    private int waitlist_capacity;
    private String time;    // format is <days>_<hours> e.g MWF_5:30-6:45
    private String location;    // format is Bld<building number>-Rm<room number> e.g Bld8-Rm302
    private String term;    // contains the semester and year in the format <season><year> e.g fall2022
    
    public Course getCourse() { return this.course;}
    public int getNumber() {return this.number;}
    public Professor getProfessor() {return this.prof;}
    public int getEnrollCapcity() {return this.enrollment_capacity;}
    public int getWaitCapacity() {return this.waitlist_capacity;}
    public String getTime() {return this.time;}
    public String getLocation() {return this.location;}
    public String getTerm() {return this.term;}

    public void setCourse(Course course) {this.course = course;}
    public void setNumber(int number) {this.number = number;}
    public void setProfessor(Professor prof) {this.prof = prof;}
    public void setEnrollCapacity(int enrollment_capacity) {this.enrollment_capacity = enrollment_capacity;}
    public void setWaitCapcity(int waitlist_capacity) {this.waitlist_capacity = waitlist_capacity;}
    public void setTime(String time) {this.time = time;}
    public void setLocation(String location) {this.location = location;}
    public void setTerm(String term) {this.term = term;}

    public Section(){
        
    }
    
}
