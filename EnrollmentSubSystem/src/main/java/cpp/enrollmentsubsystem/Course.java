package cpp.enrollmentsubsystem;


public class Course {
    
    private int courseID;
    private String name;
    private int units;
    private Course[] prerequisites;

    public int getCourseID() {return this.courseID;}
    public String getName() {return this.name;}
    public int getUnits() {return this.units;}

    public void setCourseID(int courseID) {this.courseID = courseID;}
    public void setName(String name) {this.name = name;}
    public void setUnits(int units) {this.units = units;}
    
    public Course(){
        
    }
    

}
