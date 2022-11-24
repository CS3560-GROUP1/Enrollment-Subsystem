/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
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
    
    private JPanel facePanel;
    private CardLayout layout;
    
    public LoginPanel() {
        super();
        setTitle("Mock LoginPanel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
        Dimension size = new Dimension( 
                (int)(ScreenInformation.getWidth() * 0.2),
                (int)(ScreenInformation.getHeight() * 0.5) 
        );
        setSize(size);
        setLocationRelativeTo(null);
        
        layout = new CardLayout(0, 0);
        facePanel = new JPanel(layout);
               
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
                            PreparedStatement statement= con.prepareStatement("select * from logins "
                                    + " where username= ? ;" );
                            statement.setString(1, username);
                            ResultSet RS = statement.executeQuery();
                            
                            if(!RS.isBeforeFirst()){
                                
                                JOptionPane.showMessageDialog(mockLogin, "Cant Find Account", "No Accounts", JOptionPane.INFORMATION_MESSAGE);
                                
                            }else{
                                if(RS.next()){
                                    
                                    String passHex = RS.getString("password_Hash");
                                    String saltHex = RS.getString("password_Salt");

                                    byte[] salt = EnrollmentSubSystem.hexStringToBit(saltHex);
                                    String inputPassHex = EnrollmentSubSystem.bitArrayToHex( EnrollmentSubSystem.passwordHash(password, salt) );

                                    if (passHex.equals(inputPassHex)){
                                        //System.out.println(rsUser + "\n" + passHex + "\n" + saltHex + "\n" + studentID);
                                        con.close();
                                        new HomePanel().startUp();
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
                
                case "Recovery Back" -> {
                    layout.show(facePanel, "login");
                }
                
                case "Recovery Submit" -> {
                    
                }
                
                case "Forgot Password" ->{
                    layout.show(facePanel, "password");
                }
                
                case "Create Account" -> {
                    new SignUpPanel().setVisible(true);
                    dispose();
                    
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

        facePanel.add(mockLogin, "login");
        
        // Password Recovery Panel
        JPanel passwordRecoveryPanel = new JPanel();
        passwordRecoveryPanel.setLayout(new GridBagLayout());
        
        JLabel informationJLabel = new JLabel();
        informationJLabel.setText("Password Recovery");
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        passwordRecoveryPanel.add(informationJLabel, c);
        c.gridheight = 1;
        
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        c.gridx = 1;
        c.gridy = 3;
        passwordRecoveryPanel.add(usernameLabel,c);
        
        
        JTextField recoveryUsernameJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        passwordRecoveryPanel.add(recoveryUsernameJTextField,c);
        
        JLabel studentIDJLabel = new JLabel();
        studentIDJLabel.setText("StudentID: ");
        c.gridx = 1;
        c.gridy = 6;
        passwordRecoveryPanel.add(studentIDJLabel, c);
        
        JTextField studentIDJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 7;
        passwordRecoveryPanel.add(studentIDJTextField,c);
        
        JButton passwordRecoveryJButton = new JButton();
        passwordRecoveryJButton.setText("Submit");
        passwordRecoveryJButton.setActionCommand("Recovery Submit");
        passwordRecoveryJButton.addActionListener(loginListener);
        
        c.gridx = 2;
        c.gridy = 9;
        passwordRecoveryPanel.add(passwordRecoveryJButton,c);
        
        JButton passRecBackButton = new JButton();
        passRecBackButton.setText("Back");
        passRecBackButton.setActionCommand("Recovery Back");
        passRecBackButton.addActionListener(loginListener);
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 12;
        passwordRecoveryPanel.add(passRecBackButton,c);
        
        facePanel.add(passwordRecoveryPanel, "password");
        
        add(facePanel);
        layout.show(facePanel, "login");
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
                new LoginPanel().setVisible(true);
        });
    }
    
}