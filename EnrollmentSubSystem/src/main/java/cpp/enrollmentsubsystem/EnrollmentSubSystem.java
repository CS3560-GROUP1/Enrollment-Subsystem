
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HexFormat;
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
    
    private Dimension ScreenInformation;
    
    private JPanel face;
    private CardLayout layout;
    
    private JPanel debugPanel;
    
    public EnrollmentSubSystem(boolean mode){
        
        debugMode = mode;
        if(debugMode){ 
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
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
            
            new LoginPanel().setVisible(true);
            
        }
        
    }
    
    
    public void start(){
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
            if(ESS != null){
                ESS.start();
            } else {
                System.err.println("ESS FAILURE");
            }
           
        });
    }
    
    /**
     * On Local MySQL server run the following:
     *  CREATE DATABASE EnrollmentDB;
     *  CREATE USER 'EnrollmentUser'@'localhost' IDENTIFIED BY '';
     *  GRANT ALL ON EnrollmentDB.* TO 'EnrollmentUser'@'locahost';
     * 
     *  Don't Forget To Close Connections
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Connection getSQLConnection() throws SQLException{
        
        Connection con;
        String url = "jdbc:mysql://localhost:3306/enrollmentdb";
        String user = "EnrollmentUser";
        String pass = "";
        
        con = DriverManager.getConnection(url, user, pass);
        if(con != null){
            System.out.println(con.toString());
        }
        
        return con;
    }
    
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
    
}
