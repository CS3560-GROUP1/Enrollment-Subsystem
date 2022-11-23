/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author LeothEcRz
 */
public class LoginPanel extends JFrame{
    
    private String username;
    private String password;
    
    private JTextField usernameJTextField;
    private JTextField passwordJTextField;
    
    
    public LoginPanel() {
        super();
        setTitle("Mock LoginPanel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
        Dimension size = new Dimension( 
                (int)(ScreenInformation.getWidth() * 0.2),
                (int)(ScreenInformation.getHeight() * 0.5) 
        );
        setSize(size);
        setLocationRelativeTo(null);
        
        JPanel mockLogin = new JPanel();
        mockLogin.setPreferredSize(getPreferredSize());
        mockLogin.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JLabel mainLogo = new JLabel("\n Login Page \n");
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 2;
        c.ipady = (int)(size.getHeight()/10);
        mockLogin.add(mainLogo, c);
        c.ipady = 0;
        
        ActionListener loginListener = evt -> {
            
            switch (evt.getActionCommand()) {
                case "Sign in" -> {
                    username = usernameJTextField.getText();
                    password = passwordJTextField.getText();

                    System.out.print(username + password);
                    
                    if( (username != null) && (password != null) ){
                        //Username Length Check
                        if(username.length() > 40){
                            JOptionPane.showMessageDialog(mockLogin, "Username is not valid. Too many characters");
                            return;
                        }
                        
                        try {
                            
                            Connection con = EnrollmentSubSystem.getSQLConnection();
                            Statement stament = con.createStatement();
                            
                            String sql = "select * from Logins where username='" + username + "';";
                            ResultSet RS = stament.executeQuery(sql);
                            if(!RS.isBeforeFirst()){
                                
                                System.out.println("No Username Matches");
                                
                            }else{
                                if(RS.next()){
                                    
                                    String rsUser = RS.getString("username");
                                    String passHex = RS.getString("password_Hash");
                                    String saltHex = RS.getString("password_Salt");
                                    String studentID = RS.getString("studentID");

                                    byte[] salt = EnrollmentSubSystem.hexStringToBit(saltHex);
                                    String inputPassHex = EnrollmentSubSystem.bitArrayToHex( EnrollmentSubSystem.passwordHash(password, salt) );

                                    if (passHex.equals(inputPassHex)){
                                        //System.out.println(rsUser + "\n" + passHex + "\n" + saltHex + "\n" + studentID);
                                        con.close();
                                        new HomePanel().setVisible(true);
                                        dispose();

                                    } else {
                                        JOptionPane.showMessageDialog(mockLogin, "Password is incorrect", "Incorrect Password", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                
                            }
                            con.close();
                        } catch (SQLException ex) {
                            System.err.println(ex.toString());
                        }
                        
                    } else if (username != null){ // No Password Given
                        JOptionPane.showMessageDialog(mockLogin, "Password is not valid. No Entry Given");
                        return;
                    } else {
                        
                    }
                    
                }
                
                case "Forgot Password" ->{
                
                }
                
                case "Create Account" -> {
                    
                }
                    
                default -> {
                    System.err.println(evt.toString());
                }
            }
            
        };
        
        JLabel usernameJLabel = new JLabel("Username: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        mockLogin.add(usernameJLabel, c);
        
        usernameJTextField = new JTextField();
        c.gridx = 2;
        c.gridy = 5;
        mockLogin.add(usernameJTextField, c);

        JLabel passwordJLabel = new JLabel("Password: ");
        c.gridx = 2;
        c.gridy = 7;
        mockLogin.add(passwordJLabel, c);

        passwordJTextField = new JTextField();
        c.gridx = 2;
        c.gridy = 8;
        passwordJTextField.addActionListener(loginListener);
        passwordJTextField.setActionCommand("Sign in");
        mockLogin.add(passwordJTextField, c);
        
        JButton signJButton = new JButton("Sign in");
        c.gridx = 2;
        c.gridy = 10;
        signJButton.addActionListener(loginListener);
        signJButton.setActionCommand("Sign in");
        mockLogin.add(signJButton, c);

        JButton forgotJButton = new JButton("Forgot Password");
        c.gridx = 1;
        c.gridy = 12;
        forgotJButton.addActionListener(loginListener);
        forgotJButton.setActionCommand("Forgot Password");
        mockLogin.add(forgotJButton, c);

        JButton createAccJButton = new JButton("Create Account");
        c.gridx = 3;
        c.gridy = 12;
        createAccJButton.addActionListener(loginListener);
        createAccJButton.setActionCommand("Create Account");
        mockLogin.add(createAccJButton, c);

        add(mockLogin);
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
                new LoginPanel().setVisible(true);
        });
    }
    
}