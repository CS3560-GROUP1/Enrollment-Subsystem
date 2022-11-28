
package cpp.enrollmentsubsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
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
     */
    public UserSchedulesPanel(Dimension size, Connection con, String studentID){
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
                    
                    for(int i=1; i<=classCount; i++){
                        sectionIDs[i-1] = RS.getString(i);
                    }
                    
                    for(String s : sectionIDs){
                        sql = "SELECT sectionID FROMenrolled_classes WHERE studentID=".concat(studentID);
                        RS = sta.executeQuery(sql);
                        
                        //UserSchedulesInnerPanel USIP = new UserSchedulesInnerPanel(size, studentID, s, s, WIDTH, s);
                    }
                    
                    
                    
                    
                    
                    
                    
                    /**
                     * JPanel usePanel; // Will be moved to its own panel class
                    for(int i=0; i< entries; i++){
                    usePanel = new JPanel();
                    usePanel.setBounds( 0, i*(int)(size.getHeight() * 0.25), (innerBottomPanel.getWidth()), (int)(size.getHeight() * 0.25));
                    usePanel.setBackground(new Color(i*5, i*10, i*12));

                    innerBottomPanel.add(usePanel);
                     */
                    
                    
                
                }
            }
            
            
        } catch (SQLException ex){
            System.err.println("User Schedules Panel Error: " + ex.toString());
        }
        
    }
    
    public UserSchedulesPanel(Dimension size){
        this(size,null,"");
    }

    
}

/**
 * 
    int entries = 20;
    JPanel innerBottomPanel = new JPanel(null); // TO BE Moved to method with sql query to reach for information
    innerBottomPanel.setSize( (int)(size.getWidth() * 0.8), (entries * ((int)(size.getHeight() * 0.25)) ) ); // inner panell size set
    innerBottomPanel.setPreferredSize(innerBottomPanel.getSize()); // scroll requires a preferred size

    JPanel usePanel; // Will be moved to its own panel class
    for(int i=0; i< entries; i++){
        usePanel = new JPanel();
        usePanel.setBounds( 0, i*(int)(size.getHeight() * 0.25), (innerBottomPanel.getWidth()), (int)(size.getHeight() * 0.25));
        usePanel.setBackground(new Color(i*5, i*10, i*12));

        innerBottomPanel.add(usePanel);
    }
 * 
 */