
package cpp.enrollmentsubsystem;

import static cpp.enrollmentsubsystem.LoginPanel.currentStudentID;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 */
public class EnrollmentSubSystem {
    
    private boolean debugMode = false;
        
    private JFrame frame;
    private JPanel face;
    private CardLayout layout;
    
    private LoginPanel loginPanel;
    private JPanel debugPanel;
    
    public EnrollmentSubSystem(boolean mode){
        
        debugMode = mode;
        
        checkAndCreateSQLTables();
        
        if(debugMode){ 
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
            Dimension size = new Dimension( 
                    (int)(ScreenInformation.getWidth() * 0.6),
                    (int)(ScreenInformation.getHeight() * 0.4) 
            );
            frame.setSize(size);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setTitle("Enrollment Subsystem");

            face = new JPanel();
            layout = new CardLayout(0, 0);
            face.setLayout(layout);
            
            debugPanel = new JPanel();
            debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));

            JLabel title = new JLabel();
            title.setText("Enrollment Subsystem DEBUG UI");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(title);

            JButton login = new JButton(" 'Log-In' screen");
            login.addActionListener((ActionEvent e) -> {
                String[] args = {};
                LoginPanel.main(args);
                frame.dispose();
            });
            login.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(login);

            JButton search = new JButton(" 'Search' screen");
            search.addActionListener((ActionEvent e) -> {
                String[] args = {};
                SearchPanel.main(args);
                frame.dispose();
            });
            search.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(search);

            JButton createAcct = new JButton(" 'Create Account' screen");
            createAcct.addActionListener((ActionEvent e) -> {
                JFrame tempFrame = new JFrame();
                tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tempFrame.setSize(500,500);
                
                ActionListener tempListener = evt -> {

                };

                tempFrame.add(new SignUpPanel( new Dimension(400,600) ,tempListener));
                tempFrame.setVisible(true);
                frame.dispose();
            });
            createAcct.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(createAcct);


            //            JButton sectionDetails = new JButton(" 'Section Details' screen (MOCK)");
//            sectionDetails.addActionListener((ActionEvent e) -> {
//                String[] args = {};
//                SectionDetailsPanel.main(args, null, null);
//                frame.dispose();
//            });
//            sectionDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
//            debugPanel.add(sectionDetails);
            
            JButton viewCart = new JButton(" 'View Cart' screen (MOCK)");
            viewCart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] args = {};
                    //change "currentStudentID" to "[your id in the mock data]" to test adding to cart and cart view in debug mode
                    //add to cart through search in normal mode, then relaunch in debug and view cart/enroll
                    CartPanel.main(args, currentStudentID,null);
                    frame.dispose();
                }
            });
            viewCart.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(viewCart);
            
            JButton waitEnrollTest = new JButton("test waitlisted or enrolled");
            waitEnrollTest.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    methods m = new methods();
                    m.enrolledOrWaitlisted("0", "3560"); //hard coded to test
                }
            });
            waitEnrollTest.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(waitEnrollTest);
            
            face.add(debugPanel, "debugPanel");
            layout.show(face, "debugPanel");
            frame.add(face);
            frame.setVisible(true);
            
        } else {
            
            loginPanel = new LoginPanel();
            
            
        }   
    }
    
    /**
     * 
     */
    public void startUP(){
        
        if(!debugMode)
            loginPanel.setVisible(true);

    }
    
    /**
     * Mock Data Filler. If the 'enrollmentdb' databases exists AND
     * the EnrollmentUser exist it will create the required tables formatted for their use if they 
     * do not exist allready. MockData is only filled if the table needed to be created.
     * Will not create a table that already exists.
     */
    public static void checkAndCreateSQLTables(){
        
        try {
            Connection con;
            con = getSQLConnection();
            Statement statement = con.createStatement();
            String sql;
            
            if(!(tableSQLExist(con, "students")) ){ 
                sql = "create table students ( " +
                    " studentID varchar(10) unique not null primary key, " +
                    " first_Name varchar(25) not null, " +
                    " last_Name varchar(25) not null, " +
                    " major varchar(20) " +
                    " );";
                statement.executeUpdate(sql);
                
                //MOCK DATA
                sql = "insert into students (studentID, first_Name, last_Name, major) " +
                    " values" +
                    " ('0','Leonardo','D','CompSci'), " +
                    " ('1','Kimtaiyo','M','?'), " +
                    " ('2','Lindsey','P','?'), " +
                    " ('3','Nicholas','N','?') " +
                    " ;";
                statement.executeUpdate(sql);
            }
            
            if(!(tableSQLExist(con, "logins"))){
                sql = "create table logins ( " +
                    " username varchar(40) unique primary key, " +
                    " password_Hash varchar(64), " +
                    " password_Salt varchar(32), " +
                    " studentID varchar(10) unique not null, " +
                    " foreign key (studentID) references students(studentID) " +
                    "); ";
                statement.executeUpdate(sql);
                
                //MOCK DATA
                sql = "insert into logins (username, password_Hash, password_Salt, studentID) " +
                    " values " +
                    " ('Leonardo','E095F2330853CD6B3A5E2642E9E36569B30FCA0B24E20CD8312E6199F3AB1D27', '0B93FAD115938BCD4A1CAA27BFB6952A', '0'), " +
                    " ('Kimtaiyo','DC7DAC61C84B4E81CC28B37550C2602B0CB4FA35C1CD57BDCD14ADFA41F0A9FA', 'D03146554487FD9CDE36199251E6A811', '1'), " +
                    " ('Lindsey','7480A8F3C1F494AE017491CCEDFB1C7B798A54FE6A5F9AFB35D706314DD90D04', '112F75606C8E83DE4321737E7FB48777', '2'), " +
                    " ('Nicholas','D2B0F72B65A5851DB3F1CD36F9AD54550426253C46E9FA911B824157A69D8612', '362DBEC5B9F4C3D559445B320215FF25', '3') " +
                    " ; ";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "professors"))){
                sql = "create table professors ( " +
                    " professorID varchar(10) unique not null primary key, " +
                    " first_Name varchar(25) not null, " +
                    " last_Name varchar(25) not null, " +
                    " department varchar(20) not null " +
                    " ); ";
                statement.executeUpdate(sql);
                
                sql = "insert into professors (professorID, first_Name, last_Name, department) " +
                    " values " + 
                    " ('1', 'Tannaz', 'Rezaei Damavandi', 'Computer Science'), " +
                    " ('2', 'Patrick', 'Polk', 'Philosophy'), " +
                    " ('3', 'Sander', 'Eller', 'Computer Science'), " +
                    " ('4', 'Paula', 'Propst', 'Music'), " +

                    " ('5', 'Tony', 'Diaz', 'Computer Science'), " +
                    " ('6', 'Lorena', 'Turner', 'Art') " +
                    " ; ";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "rooms"))){
                sql = "create table rooms ( " +
                    " roomID varchar(10) unique not null primary key, " +
                    " enrollment_Capacity smallint not null default '0', " +
                    " wait_List_Capacity smallint not null default '0' " +
                    " );";
                statement.executeUpdate(sql);
                
                sql = "insert into rooms (roomID, enrollment_Capacity, wait_List_Capacity) " +
                    " values " +
                    " ('2643', '35', '10'), " +
                    " ('348', '30', '11'), " +
                    " ('2005', '42', '15'), " +
                    " ('1029', '28', '8'), " +

                    " ('1570', '34', '8'), " +
                    " ('2230', '30', '10'), " +
                    " ('2400', '30', '10'), " +
                    " ('1350', '30', '10') " +
                    " ; ";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "courses"))){
                sql = "create table courses ( " +
                    " courseID varchar(10) unique not null primary key, " +
                    " course_Name varchar(30) not null, " +
                    " units smallint not null, " +
                    " subject varchar(25) not null, " +
                    " term varchar(12) not null," +
                    " prerequisiteID varchar(10)" +
                    " );";
                statement.executeUpdate(sql);
                
                //sql = "INSERT INTO courses (courseID, course_Name, units, term)";
                //statement.executeUpdate(sql);
                
                //set 3560 as a prerequisite for 3750
                //just to test prerequisites
                //(also this technically only supports each course having only 1 prerequisite)
                sql = "insert into courses (courseID, course_Name, units, subject, term, prerequisiteID) " + 
                    " values " + 
                    " ('3560', 'Obj-Oriented Design and Prog', '3', 'Computer Science', 'FALL',''), " +
                    " ('3500', 'Creative Prcss Theory Practice', '4', 'Philosophy', 'FALL',''), " +
                    " ('3750', 'Computers and Society', '3', 'Computer Science', 'SPRING', '3560'), " +
                    " ('1030', 'World of Music', '3', 'Music', 'SUMMER',''), " +

                    " ('2450', 'User Interface Dsng and Prgmng', '3', 'Computer Science', 'WINTER',''), " +
                    " ('2260', 'Prob and Stats for CS Engr', '3', 'Mathematics', 'WINTER',''), " +
                    " ('2280', 'Undrstnding and Apprctng Image', '3', 'ART', 'FALL','') " +
                    " ; ";
                statement.executeUpdate(sql);
                
            }
            if(!(tableSQLExist(con, "sections"))){
                sql = "create table sections ( " +
                    " sectionID varchar(10) unique not null primary key, " +
                    " courseID varchar(10) not null, " +
                    " professorID varchar(10) not null, " +
                    " term varchar(20), " +
                    " subject varchar(20), " +
                    " roomID varchar(10) unique not null, " +
                    " foreign key (courseID) references courses(courseID), " +
                    " foreign key (professorID) references professors(professorID), " +
                    " foreign key (roomID) references rooms(roomID) " +
                    " ); ";
                statement.executeUpdate(sql);
                
                sql = "insert into sections (sectionID, courseID, professorID, term, subject, roomID) " + 
                    " values " + 
                    " ('1', '3560', '1', 'FALL', 'Computer Science', '2643'), " +
                    " ('2', '3500', '2', 'FALL', 'Philosophy', '348'), " +
                    " ('3', '3750', '3', 'SPRING', 'Computer Science', '2005'), " +
                    " ('4', '1030', '4', 'SUMMER', 'Music', '1029'), " +

                    " ('5', '2450', '5', 'WINTER', 'Computer Science', '1570'), " +
                    " ('7', '2450', '5', 'WINTER', 'Computer Science', '2230'), " +
                    " ('6', '2260', '6', 'WINTER', 'Computer Science', '2400'), " +
                    " ('8', '2280', '4', 'FALL', 'ART', '1350') "  +

                    " ; ";
                statement.executeUpdate(sql);

            }
            if(!(tableSQLExist(con, "enrolled_classes"))){
                sql = "create table enrolled_classes (  " +
                    " sectionID varchar(10) not null, " +
                    " studentID varchar(10) not null, " +
                    " foreign key (sectionID) references sections(sectionID), " +
                    " foreign key (studentID) references students(studentID), " +
                    " primary key (sectionID, studentID) " +
                    ");" ;
                statement.executeUpdate(sql);
                
                sql = "insert into enrolled_classes (sectionID, studentID) " + 
                    " values " + 
                    " ('1', '0'), " +
                    " ('2', '0'), " +
                    " ('3', '0'), " +
                    " ('4', '0') " +
                    " ; ";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "student_cart_entries"))){
                sql = "create table student_cart_entries ( " +
                    " SCEID int auto_increment unique not null primary key, " +
                    " studentID varchar(10) not null, " +
                    " sectionID varchar(10) " +
                    " ); ";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "section_schedules"))){
                sql = "create table section_schedules ( " +
                    "	sectionID varchar(10) not null primary key, " +
                    " start_time time, " +
                    " end_time time, " +
                    " Monday BIT, " +
                    " Tuesday BIT, " +
                    " Wednesday BIT, " +
                    " Thursday BIT, " +
                    " Friday BIT, " +
                    " Saturday BIT, " +
                    " Sunday BIT " +
                    " ); ";
                statement.executeUpdate(sql);
                
                /*
                1. 5:30PM-6:45PM MW
                2. 4:00PM-5:00PM MWF
                3. 1:00PM-2:15PM TuTh
                4. 2:30PM-3:30PM MTuW
                */
                sql = "insert into section_schedules (sectionID, start_time, end_time, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday) " + 
                    " values " + 
                    " ('1', '17:30', '18:45', 1, 0, 1, 0, 0, 0, 0), " +
                    " ('2', '16:00', '17:00', 1, 0, 1, 0, 1, 0, 0), " +
                    " ('3', '13:00', '14:15', 0, 1, 0, 1, 0, 0, 0), " +
                    " ('4', '14:30', '15:30', 1, 1, 1, 0, 0, 0, 0), " +

                    " ('5', '16:00', '17:00', 0, 1, 0, 1, 0, 0, 0), " +
                    " ('6', '13:00', '14:15', 1, 0, 1, 0, 0, 0, 0), " +
                    " ('7', '19:00', '20:00', 1, 0, 1, 0, 0, 0, 0), " +
                    " ('8', '17:30', '18:45', 1, 0, 1, 0, 0, 0, 0) " +
                    " ; ";
                statement.executeUpdate(sql);
            }

            con.close();
        } catch (SQLException ex) {
            
            System.err.println(ex.toString());
            
        }
        
    }
    
    
    /**
     * Entry Point
     * @param args 
     */
    public static void main(String[] args){
        
        SwingUtilities.invokeLater(() -> {
            
            EnrollmentSubSystem ESS = null;
            boolean madeChoice = false;
            
            System.out.println("DebugMode: Y/N: ");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();

            while(!madeChoice){
                switch (choice.charAt(0)) {
                    case 'Y', 'y' -> {
                        ESS = new EnrollmentSubSystem(true);
                        madeChoice = true;
                    }
                    case 'N', 'n' -> {
                        ESS = new EnrollmentSubSystem(false);
                        madeChoice = true;
                    }

                    default -> {
                        System.out.println("Not an Option!, Y/N: ");
                        choice = input.nextLine();
                    }
                }
            }

            input.close();

            if(ESS != null){
                ESS.startUP(); // ENTRY POINT
            } else {
                System.err.println("ESS FAILURE");
                System.exit(999);
            }
           
        });
    }
    
    /**
     * On Local MySQL server run the following:
     *  CREATE DATABASE enrollmentdb;
     *  CREATE USER 'EnrollmentUser'@'localhost' IDENTIFIED BY '';
     *  GRANT ALL ON enrollmentdb.* TO 'EnrollmentUser'@'locahost';
     * 
     *  Don't Forget To Close Connections
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Connection getSQLConnection() throws SQLException{
        
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/enrollmentdb"; // database name: enrollmentdb
        String user = "EnrollmentUser";
        String pass = "";
        
        con = DriverManager.getConnection(url, user, pass);
        if(con != null){
            System.out.println("Connection Success: " + con.toString());
        }else{
            System.out.println("Connection Failed: Connetcion == null");

        }
        
        return con;
    }
    
    /**
     * Debuging Method. Prints to the console associated inforamtion in creating a hashed password.
     * A salt is randomly created. The salt is used to encrypt the given String using PBKDF2. Both the salt
     * and 64 character lenth hexadecimal hash are printed on the console.
     * @param pass - The String to be encrypted
     */
    public static void createPasswordHashSaltInfo(String pass){
        byte[] salt = createSalt();
        byte[] hash = passwordHash(pass, salt);
        
        System.out.println("Hash: "+bitArrayToHex(hash));
        System.out.println("Salt: "+bitArrayToHex(salt));
    }
    
    /**
     * Given a byte[] salt and a string password use the salt to encrypt the given string.
     * @param pass
     * @param salt
     * @return null on failure; 
     */
    public static byte[] passwordHash(String pass, byte[] salt){
        
        int iterations = 1000;
        int HASH_BIT_SIZE = 256;
        byte[] hash = null;
        
        //Password, Salt, Iterations, Length
        KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, iterations, HASH_BIT_SIZE);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println(ex.toString());
        }
        
        return hash;
    }
    
    /**
     * Creates a random byte[] containing a salt to be used for password encryption.
     * @return 
     */
    public static byte[] createSalt(){
        int saltSize = 16;
        
        SecureRandom random = new SecureRandom();
        byte[] salt = null;
        salt = new byte[saltSize];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * SLOW
     * Converts a given byte array into a corresponding Hexadecimal string.
     * @param hash aray to be converted
     * @return 
     */
    public static String bitArrayToHex(byte[] hash){
        
        String hex = "";
        
        for(byte i : hash){
            String s = String.format("%02X", i);
            hex = hex.concat(s);
        }
        
        return hex;
    }
    
    /**
     * Converts a given hexadecimal string into a byte array.
     * @param hex
     * @return 
     */
    public static byte[] hexStringToBit(String hex){
        
        byte[] bitArray = new byte[ (hex.length() / 2) ];
        for(int i=0; i<bitArray.length; i++){
            int index = i*2; //modify position for string
            int z = Integer.parseInt(hex.substring(index, index+2), 16); // parse in hex
            bitArray[i] = (byte) z;
        }
        return bitArray;
    }
    
    /**
     * Checks is a table exisr with the given name, on a given connection.
     * @param con
     * @param tableName
     * @return
     * @throws SQLException 
     */
    public static boolean tableSQLExist(Connection con, String tableName) throws SQLException{
        
        PreparedStatement statement = con.prepareStatement("Select count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ? "
                + "LIMIT 1; ");
        
        statement.setString(1, tableName);
        
        ResultSet RS = statement.executeQuery();
        RS.next();
        return RS.getInt(1) != 0;
    } 
    
}
