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
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author LeothEcRz
 */
public class LoginPanel extends JFrame{
    
    private String username;
    private String password;
    public static String currentStudentID;
    
    private SignUpPanel signUpPanel;
    
    private JTextField usernameJTextField;
    private JTextField passwordJTextField;
    
    private JTextField recoveryUsernameJTextField;
    private JTextField studentIDJTextField;
    
    private JPanel facePanel;
    private CardLayout layout;
    
    public LoginPanel() {
        super();
        setTitle("Mock LoginPanel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
        Dimension size = new Dimension( 
                (int)(ScreenInformation.getWidth() * 0.3),
                (int)(ScreenInformation.getHeight() * 0.6) 
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
                                
                            }else{ // Not Empty
                                if(RS.next()){
                                    
                                    String passHex = RS.getString("password_Hash");
                                    String saltHex = RS.getString("password_Salt");
                                    String ID = RS.getString("studentID");

                                    byte[] salt = EnrollmentSubSystem.hexStringToBit(saltHex);
                                    String inputPassHex = EnrollmentSubSystem.bitArrayToHex( EnrollmentSubSystem.passwordHash(password, salt) );

                                    if ( passHex.equals(inputPassHex) ){
                                        //System.out.println(rsUser + "\n" + passHex + "\n" + saltHex + "\n" + studentID);
                                        con.close();
                                        currentStudentID = ID;
                                        new HomePanel(ID).populateHomePanel();
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
                    
                    String recoveryUsername = recoveryUsernameJTextField.getText();
                    String studentID = studentIDJTextField.getText();
                    
                    System.out.println( recoveryUsername + " " + studentID );
                    System.out.println( recoveryUsername.isBlank() + " " + studentID.isBlank() );

                    
                    if( !recoveryUsername.isBlank() || !studentID.isBlank() ){
                        
                        try{
                            Connection con = EnrollmentSubSystem.getSQLConnection();
                            String sql = "SELECT * FROM logins WHERE username=? AND studentID=?; ";
                            PreparedStatement sta = con.prepareStatement(sql);
                            sta.setString(1, recoveryUsername);
                            sta.setString(2, studentID);
                            
                            ResultSet RS = sta.executeQuery();
                            
                            if(!(RS.isBeforeFirst())){
                                JOptionPane.showMessageDialog(getParent(), "No account has been found using the given information", "No Account", JOptionPane.ERROR_MESSAGE);
                            }else{
                                if(RS.next()){
                                    
                                    JDialog newPasswordDialog = new JDialog(this.getOwner());
                                    newPasswordDialog.setLayout(null);
                                    newPasswordDialog.setSize( (int)(ScreenInformation.getWidth() * 0.3), (int)(ScreenInformation.getHeight() * 0.2));
                                    newPasswordDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                    newPasswordDialog.setLocation( (int)(getX()+(getWidth()/2) - ( newPasswordDialog.getWidth()/2 )),
                                            (int)(getY()+(getHeight()/2) - (newPasswordDialog.getHeight()/2)) );
                                    newPasswordDialog.setModal(true);
                                        
                                        newPasswordDialog.setTitle("New Password");
                                        newPasswordDialog.setResizable(false);
                                        
                                        JLabel dialogInfoLabel = new JLabel(("A New Password will be made for " + recoveryUsername + ". StudentID: " + studentID +"."));
                                        dialogInfoLabel.setBounds( (int)(newPasswordDialog.getWidth() * 0.1), (int)(newPasswordDialog.getHeight() * 0.1), dialogInfoLabel.getPreferredSize().width, dialogInfoLabel.getPreferredSize().height);
                                        newPasswordDialog.add(dialogInfoLabel);
                                        
                                        JLabel newpassLabel = new JLabel("New Password: ");
                                        newpassLabel.setBounds((int)(newPasswordDialog.getWidth() * 0.1), (int)(newPasswordDialog.getHeight() * 0.2), newpassLabel.getPreferredSize().width, newpassLabel.getPreferredSize().height);
                                        newPasswordDialog.add(newpassLabel);
                                        
                                        JTextField newpassTextField = new JTextField();
                                        newpassTextField.setBounds((int)(newPasswordDialog.getWidth() * 0.1), (int)(newPasswordDialog.getHeight() * 0.3), (int)(newPasswordDialog.getWidth() * 0.5), (int)(newPasswordDialog.getHeight() * 0.1));
                                        newPasswordDialog.add(newpassTextField);
                                        
                                        JLabel newpassconLabel = new JLabel("Confirm Password: ");
                                        newpassconLabel.setBounds((int)(newPasswordDialog.getWidth() * 0.1), (int)(newPasswordDialog.getHeight() * 0.5), newpassLabel.getPreferredSize().width, newpassLabel.getPreferredSize().height);
                                        newPasswordDialog.add(newpassconLabel);
                                        
                                        JTextField newpassconTextField = new JTextField();
                                        newpassconTextField.setBounds((int)(newPasswordDialog.getWidth() * 0.1), (int)(newPasswordDialog.getHeight() * 0.6), (int)(newPasswordDialog.getWidth() * 0.5), (int)(newPasswordDialog.getHeight() * 0.1));
                                        newPasswordDialog.add(newpassconTextField);
                                        
                                        JButton newpassSubmit = new JButton("Submit");
                                        newpassSubmit.setBounds( (int)(newPasswordDialog.getWidth() * 0.70 ), (int)(newPasswordDialog.getHeight() * 0.5), (int)(newPasswordDialog.getWidth() * 0.2), (int)(newPasswordDialog.getHeight() * 0.2) );
                                        
                                        ActionListener newPasswordAL = ev -> {
                                            String newPass = newpassTextField.getText();
                                            if(newPass.equals(newpassconTextField.getText())){
                                                byte[] salt = EnrollmentSubSystem.createSalt();
                                                byte[] hash = EnrollmentSubSystem.passwordHash(newPass, salt);
                                                
                                                String saltHex = EnrollmentSubSystem.bitArrayToHex(salt);
                                                String hashHex = EnrollmentSubSystem.bitArrayToHex(hash);
                                                
                                                try{
                                                    Connection con0 = EnrollmentSubSystem.getSQLConnection();
                                                    String sql0 = "UPDATE logins SET password_Hash=?, password_Salt=? WHERE username=? AND studentID=? ; ";
                                                    PreparedStatement sta0 = con0.prepareStatement(sql0);
                                                    sta0.setString(1, hashHex);
                                                    sta0.setString(2, saltHex);
                                                    sta0.setString(3, recoveryUsername);
                                                    sta0.setString(4, studentID);
                                                    
                                                    int rows = sta0.executeUpdate();
                                                    if(rows > 0){
                                                        newPasswordDialog.dispose();
                                                        JOptionPane.showMessageDialog(getParent(), "Password change complete.", "Success", JOptionPane.PLAIN_MESSAGE);
                                                        this.recoveryUsernameJTextField.setText("");
                                                        this.studentIDJTextField.setText("");
                                                        this.layout.show(facePanel, "login");
                                                    }
                                                    con0.close();
                                                    
                                                }catch(SQLException ex){
                                                    System.err.println(ex.toString() + " LoginPanel_newPasswordAL ");
                                                }
                                                
                                            }
                                            
                                            
                                        };
                                        
                                        newpassSubmit.addActionListener(newPasswordAL);
                                        newPasswordDialog.add(newpassSubmit);
                                        
                                    newPasswordDialog.setVisible(true);
                                    
                                }
                            }
                            con.close();
                        } catch (SQLException wx){
                            
                        }
                    }else{ 
                        JOptionPane.showMessageDialog(getParent(), "A field has not been filled in", "Empty Field", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                
                case "Forgot Password" ->{
                    layout.show(facePanel, "password");
                }
                
                case "Create Account" -> {

                    layout.show(facePanel, "signUP");
                    
                }
                case "SignUp Back", "SignUp Submit" -> {
                    signUpPanel.emptyTextFields();
                    layout.show(facePanel, "login");
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
        
        recoveryUsernameJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        passwordRecoveryPanel.add(recoveryUsernameJTextField,c);
        
        JLabel studentIDJLabel = new JLabel();
        studentIDJLabel.setText("StudentID: ");
        c.gridx = 1;
        c.gridy = 6;
        passwordRecoveryPanel.add(studentIDJLabel, c);
        
        studentIDJTextField = new JTextField();
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
        signUpPanel = new SignUpPanel(size,loginListener);
        
        facePanel.add(signUpPanel, "signUP");

        add(facePanel);
        
        layout.show(facePanel, "login");
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
                new LoginPanel().setVisible(true);
        });
    }
    
}