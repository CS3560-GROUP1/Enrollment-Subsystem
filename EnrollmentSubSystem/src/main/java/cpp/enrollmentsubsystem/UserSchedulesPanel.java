
package cpp.enrollmentsubsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author LeothEcRz
 */
public class UserSchedulesPanel extends JPanel {
    
    
    /**
     * Is not responsible for Connection con.
     * @param size
     * @param con
     * @param studentID 
     * @param dropList 
     */
    public UserSchedulesPanel(Dimension size, Connection con, String studentID, ArrayList<String> dropList){
        super();
        this.setBackground(Color.lightGray);
        setLayout(null);

        this.setSize(new Dimension( (int)(size.getWidth() * 0.8),
                (int)(size.getHeight() * 0.64)  ));
        this.setPreferredSize(this.getSize());
        
        if(con == null) 
            return;
        
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT COUNT(*) FROM enrolled_classes WHERE studentID=".concat(studentID);
            ResultSet RS = sta.executeQuery(sql);
            
            if ( !(RS.isBeforeFirst()) ){
                
            } else { 
                if( RS.next() ){
                    int classCount = RS.getInt(1);
                    
                    if(classCount == 0){ // Empty Schedule
                        
                    } else if( (int)(classCount * (size.getHeight() * 0.30)) > this.getSize().height ){
                        setSize(getSize().width, (int)(classCount * size.getHeight() * 0.30));
                        setPreferredSize(this.getSize());
                    }
                    
                    sql = "SELECT sectionID FROM enrolled_classes WHERE studentID=".concat(studentID);
                    RS = sta.executeQuery(sql);
                    String[] sectionIDs = new String[classCount];
                    if(!(RS.isBeforeFirst())){
                        
                    }else {
                        if(RS.next()){
                            for(int i=1; i<=classCount; i++){
                                sectionIDs[i-1] = RS.getString(1);
                                RS.next();
                            }
                        }
                    }
                    
                    for(int j=0; j<sectionIDs.length; j++){
                        
                        String courseID = "";
                        String professorID = "";
                        String term = "";
                        String sectionSubject = "";
                        String roomID = "";
                        String courseName = "";
                        String courseSubject = "";
                        String proffirstName = "";
                        String proflastName = "";
                        
                        Time start_time = null;
                        Time end_time = null;
                        boolean Monday = false; 
                        boolean Tuesday = false; 
                        boolean Wednesday = false; 
                        boolean Thursday= false;
                        boolean Friday = false;
                        boolean Saturday = false; 
                        boolean Sunday = false;
                        int units = 0;

                        
                        sql = "SELECT * FROM sections WHERE sectionID=".concat(sectionIDs[j]);
                        RS = sta.executeQuery(sql);
                        
                        if(!(RS.isBeforeFirst())){
                            
                        }else {
                            if(RS.next()){
                                
                                courseID = RS.getString("courseID");
                                professorID = RS.getString("professorID");
                                term = RS.getString("term");
                                sectionSubject = RS.getString("subject");
                                roomID = RS.getString("roomID");
                                
                                sql = "SELECT * FROM courses WHERE courseID=".concat(courseID);
                                RS = sta.executeQuery(sql);
                                if(!RS.isBeforeFirst()){
                                    
                                }else {
                                    if(RS.next()){
                                        courseName = RS.getString("course_Name");
                                        units = RS.getInt("units");
                                        courseSubject = RS.getString("subject");
                                    }
                                }
                                
                                sql = "SELECT * FROM professors WHERE professorID=".concat(professorID);
                                RS = sta.executeQuery(sql);
                                if(!RS.isBeforeFirst()){
                                    
                                }else {
                                    if(RS.next()){
                                        proffirstName = RS.getString("first_Name");
                                        proflastName = RS.getString("last_Name");
                                    }
                                }
                                
                                sql = "SELECT * FROM section_schedules WHERE sectionID=".concat(sectionIDs[j]);
                                RS = sta.executeQuery(sql);
                                if(!RS.isBeforeFirst()){
                                    
                                }else {
                                    if(RS.next()){
                                        
                                        start_time = RS.getTime("start_time");
                                        end_time = RS.getTime("end_time");
                                        Monday = RS.getBoolean("Monday");
                                        Tuesday = RS.getBoolean("Tuesday");
                                        Wednesday = RS.getBoolean("Wednesday");
                                        Thursday = RS.getBoolean("Thursday");
                                        Friday = RS.getBoolean("Friday");
                                        Saturday = RS.getBoolean("Saturday");
                                        Sunday = RS.getBoolean("Sunday");
                                        
                                    }
                                }
                                
                                UserSchedulesInnerPanel USIP = new UserSchedulesInnerPanel(size, courseName, courseSubject, term, units, proffirstName.concat(" ".concat(proflastName)),sectionIDs[j],dropList,start_time,end_time,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday);
                                
                                USIP.setBounds(0, (int)(j * size.getHeight() * 0.25), this.getSize().width, (int)(size.getHeight() * 0.25) );
                                add(USIP);
                                
                            }
                        }
                    }
                }
            }
            
            
        } catch (SQLException ex){
            System.err.println("User Schedules Panel Error: " + ex.toString());
        }
        
    }
    
    public UserSchedulesPanel(Dimension size){
        this(size,null,"",null);
    }

    
}
