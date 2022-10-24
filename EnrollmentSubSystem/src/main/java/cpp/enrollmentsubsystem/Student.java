package cpp.enrollmentsubsystem;

public class Student {
    
    private int ID;
    private String name;
    private String major;
    private String email;
    private int password;    // password will be stored in hash code format
    
    public int getID() {
        return this.ID;
    }
    public String getName() {
        return this.name;
    }
    public String getMajor() {
        return this.major;
    }
    public String getEmail() {
        return this.email;
    }
    public int getPassword() {
        return this.password;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    public Student(){

    }

    
}
