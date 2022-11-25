
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
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
    
    private JFrame frame;
    
    private boolean debugMode = false;
        
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
                String[] args = {};
                SignUpPanel.main(args);
                frame.dispose();
            });
            createAcct.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(createAcct);

            JButton sectionDetails = new JButton(" 'Section Details' screen (MOCK)");
            sectionDetails.addActionListener((ActionEvent e) -> {
                String[] args = {};
                SectionDetailsPanel.main(args, null, null);
                frame.dispose();
            });
            sectionDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
            debugPanel.add(sectionDetails);
            
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
     * 
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
            }
            if(!(tableSQLExist(con, "rooms"))){
                sql = "create table rooms ( " +
                    " roomID varchar(10) unique not null primary key, " +
                    " enrollment_Capacity smallint not null default '0', " +
                    " wait_List_Capacity smallint not null default '0' " +
                    " );";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "courses"))){
                sql = "create table courses ( " +
                    " courseID varchar(10) unique not null primary key, " +
                    " course_Name varchar(30) not null, " +
                    " units smallint not null " +
                    " );";
                statement.executeUpdate(sql);
            }
            if(!(tableSQLExist(con, "sections"))){
                sql = "create table sections ( " +
                    " sectionID varchar(10) unique not null primary key, " +
                    " courseID varchar(10) unique not null, " +
                    " professorID varchar(10) unique not null, " +
                    " term varchar(20), " +
                    " roomID varchar(10) unique not null, " +
                    " foreign key (courseID) references courses(courseID), " +
                    " foreign key (professorID) references professors(professorID), " +
                    " foreign key (roomID) references rooms(roomID) " +
                    " ); ";
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
                ESS.startUP();
            } else {
                System.err.println("ESS FAILURE");
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
        
        Connection con;
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
     * 
     * @param pass 
     */
    public static void createPasswordHashSaltInfo(String pass){
        byte[] salt = createSalt();
        byte[] hash = passwordHash(pass, salt);
        
        System.out.println("Hash: "+bitArrayToHex(hash));
        System.out.println("Salt: "+bitArrayToHex(salt));
    }
    
    /**
     * 
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
     * 
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
     * slow
     * @param hash
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
     * 
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
     * 
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
