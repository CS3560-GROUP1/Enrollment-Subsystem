
package cpp.enrollmentsubsystem;

/**
 *
 */
public class CourseCart {

    public Student student;
    public Section[] courses;

    public Student getStudent() {return this.student;}
    public Section[] getSections() {return this.courses;}

    public void setStudent(Student student) {this.student = student;}
    public void setSections(Section[] sections) {this.courses = sections;}

    public CourseCart(){
        
    }
    
}
