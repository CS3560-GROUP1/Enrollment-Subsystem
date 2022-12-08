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
import javax.swing.JPasswordField;
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
    
    /**
     * 
     */
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
        setPreferredSize(size);
        setLocationRelativeTo(null);
        layout = new CardLayout(0, 0);
        facePanel = new JPanel(layout);

        //Handles most events that occur on the LoginPanel
        ActionListener loginListener = evt -> {
            
            switch (evt.getActionCommand()) {
                
                case "Sign in" -> {
                    signInAttempt();
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
        
        JPanel mockLogin = setUpMockLoginPanel(size, loginListener);
        facePanel.add(mockLogin, "login");
        
        JPanel passwordRecoveryPanel = setUpPasswordRecoveryPanel(loginListener);
        facePanel.add(passwordRecoveryPanel, "password");
        
        signUpPanel = new SignUpPanel(size,loginListener);
        facePanel.add(signUpPanel, "signUP");

        add(facePanel);
        layout.show(facePanel, "login");
    }
    
    /**
     * 
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
                new LoginPanel().setVisible(true);
        });
    }
    /**
     * 
     * @param AL
     * @return
     */
    private JPanel setUpPasswordRecoveryPanel(ActionListener AL){
        // Password Recovery Panel
        JPanel passwordRecoveryPanel = new JPanel();
        passwordRecoveryPanel.setPreferredSize(this.getSize());
        passwordRecoveryPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        
        JLabel informationJLabel = new JLabel();
        informationJLabel.setText("Password Recovery");
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 2;
        passwordRecoveryPanel.add(informationJLabel, c);
        
        
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        passwordRecoveryPanel.add(usernameLabel,c);
        
        recoveryUsernameJTextField = new JTextField();
        c.gridwidth = 3;
        c.gridheight = 1;

        c.ipadx = (int)(getSize().width * 0.5);

        c.gridx = 1;
        c.gridy = 4;
        passwordRecoveryPanel.add(recoveryUsernameJTextField,c);

        c.ipadx = 0;
        
        JLabel studentIDJLabel = new JLabel();
        studentIDJLabel.setText("StudentID: ");
        c.gridx = 1;
        c.gridy = 6;

        c.gridwidth = 3;
        c.gridheight = 1;

        passwordRecoveryPanel.add(studentIDJLabel, c);
        
        studentIDJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 7;

        c.ipadx = (int)(getSize().width * 0.5);


        c.gridwidth = 3;
        c.gridheight = 1;

        passwordRecoveryPanel.add(studentIDJTextField,c);
        
        c.ipadx = 0;


        JButton passwordRecoveryJButton = new JButton();
        passwordRecoveryJButton.setText("Submit");
        passwordRecoveryJButton.setActionCommand("Recovery Submit");
        passwordRecoveryJButton.addActionListener(AL);
        
        c.gridx = 1;
        c.gridy = 9;

        c.gridwidth = 1;
        c.gridheight = 1;
        passwordRecoveryPanel.add(passwordRecoveryJButton,c);
        
        JButton passRecBackButton = new JButton();
        passRecBackButton.setText("Back");
        passRecBackButton.setActionCommand("Recovery Back");
        passRecBackButton.addActionListener(AL);
        c.gridx = 3;
        c.gridy = 9;

        c.gridwidth = 1;
        c.gridheight = 1;

        passwordRecoveryPanel.add(passRecBackButton,c);

        return passwordRecoveryPanel;
    }

    /**
     * 
     * @param size
     * @param AL
     * @return
     */
    private JPanel setUpMockLoginPanel(Dimension size, ActionListener AL){

        JPanel mockLogin = new JPanel();
        mockLogin.setPreferredSize(this.getSize());
        mockLogin.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        
        JLabel mainLogo = new JLabel("\n Login Page \n");
        final float fontConstant = 11.675f;
        float fontSize = (float)(this.getSize().getWidth() / fontConstant);
        mainLogo.setFont(mainLogo.getFont().deriveFont(fontSize));
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        //c.ipady = (int)(size.getHeight()/10);
        mockLogin.add(mainLogo, c);
        
        //c.ipady = 0;
        c.gridwidth = 1;

        JLabel usernameJLabel = new JLabel("Username: ");
        c.gridx = 2;
        c.gridy = 4;
        mockLogin.add(usernameJLabel, c);
        
        usernameJTextField = new JTextField();
        usernameJTextField.setHorizontalAlignment(JTextField.CENTER);
        c.gridx = 1;
        c.gridy = 5;
        c.ipadx = (int) (getSize().width * 0.35);
        c.gridwidth = 3;
        mockLogin.add(usernameJTextField, c);

        c.ipadx = 0; c.gridwidth = 1;

        JLabel passwordJLabel = new JLabel("Password: ");
        c.gridx = 2;
        c.gridy = 7;
        mockLogin.add(passwordJLabel, c);

        passwordJTextField = new JPasswordField();
        passwordJTextField.setHorizontalAlignment(JTextField.CENTER);
        c.gridx = 1;
        c.gridy = 8;
        c.ipadx = (int) (getSize().width * 0.35);
        c.gridwidth = 3;
        passwordJTextField.addActionListener(AL);
        passwordJTextField.setActionCommand("Sign in");
        mockLogin.add(passwordJTextField, c);

        c.ipadx = 0; c.gridwidth = 1;
        
        JButton signJButton = new JButton("Sign in");
        c.gridx = 2;
        c.gridy = 10;
        signJButton.addActionListener(AL);
        signJButton.setActionCommand("Sign in");
        mockLogin.add(signJButton, c);

        JButton forgotJButton = new JButton("Forgot Password");
        c.gridx = 1;
        c.gridy = 12;
        forgotJButton.addActionListener(AL);
        forgotJButton.setActionCommand("Forgot Password");
        mockLogin.add(forgotJButton, c);

        JButton createAccJButton = new JButton("Create Account");
        c.gridx = 3;
        c.gridy = 12;
        createAccJButton.addActionListener(AL);
        createAccJButton.setActionCommand("Create Account");
        mockLogin.add(createAccJButton, c);

        return mockLogin;
    }

    /**
     * Collects the information in the textfields to process a sign in attempt.
     */
    private void signInAttempt(){
        
        if(usernameJTextField.getText().length() > 0)
            username = usernameJTextField.getText();
        
        if(passwordJTextField.getText().length() > 0)
            password = passwordJTextField.getText();
        
        if(username == null){
            JOptionPane.showMessageDialog(getParent(), "No username given");
            return;
        }
        if(username.length() > 40){
            JOptionPane.showMessageDialog(getParent(), "Username is not valid. Too many characters");
            return;
        }
        if(password == null){
            JOptionPane.showMessageDialog(getParent(), "Password is not valid. No Entry Given");
            return;
        }

        try ( Connection con = EnrollmentSubSystem.getSQLConnection() ) {

            PreparedStatement statement= con.prepareStatement("select * from logins "
                    + " where username= ? ;" );
            statement.setString(1, username);
            ResultSet RS = statement.executeQuery();

            if(!RS.isBeforeFirst()){
                JOptionPane.showMessageDialog(getParent(), "Cant Find Account", "No Accounts", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(!RS.next())
                return;

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
                JOptionPane.showMessageDialog(getParent(), "Password is incorrect", "Incorrect Password", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            System.err.println("State: " + ex.getSQLState() + "Error Code: " + ex.getErrorCode() + "\n Exception: " + ex.toString());
        }

        username = null;
        password = null;
        passwordJTextField.setText("");

    }

    
}