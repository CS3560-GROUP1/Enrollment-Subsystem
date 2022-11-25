/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 */
public class SignUpPanel extends JPanel{
    
    private String username;    
    private String password;
    private String passwordCon;
    private String name;
    private String email;
    private String major;
    
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField passwordInput;
    private JTextField confirmPasswordInput;
    private JTextField emailInput;
    private JTextField majorInput;
    

    public SignUpPanel(Dimension size , ActionListener AL){
        
        setSize(size);
        int BorderPadding = 20;
        setLayout(new BorderLayout(BorderPadding, BorderPadding));
        JPanel container = new JPanel(null); // TOP LEVEL container
        container.setSize(new Dimension(getWidth() - BorderPadding, getHeight() - BorderPadding));
        container.setVisible(true);

        int containerHalfPoint = (int)(container.getWidth()/2);
        
        //Sign Up Panel BACK button
        JButton back = new JButton("Back");
        back.setBounds( 10,
            10,
            (int)(container.getWidth() * 0.25),
            (int)(container.getHeight() * 0.075));
        back.addActionListener(AL);
        back.setActionCommand("SignUp Back");
        container.add(back);
        
        //title
        JLabel title = new JLabel();
        title.setText("Create an Account");
        title.setSize(120, 20);
        title.setBounds( (containerHalfPoint - (int)(title.getWidth()/2)),
                (int)(container.getHeight() * 0.10),
                title.getWidth(),
                title.getHeight());
        container.add(title);
        
        
        //username
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name: ");
        firstNameLabel.setSize(100, 20);
        firstNameLabel.setBounds((containerHalfPoint - (int)(firstNameLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.20),
                firstNameLabel.getWidth(),
                firstNameLabel.getHeight());
        container.add(firstNameLabel);

        firstNameInput = new JTextField();
        firstNameInput.setSize(100, 20);
        firstNameInput.setBounds((containerHalfPoint - (int)(firstNameInput.getWidth()/2)),
                (int)(container.getHeight() * 0.25),
                firstNameInput.getWidth(),
                firstNameInput.getHeight());
        container.add(firstNameInput);
        
        //password
        JLabel lastNameLabel = new JLabel();
        
        lastNameLabel.setText("Last Name: ");
        lastNameLabel.setSize(100,20);
        lastNameLabel.setBounds((containerHalfPoint - (int)(lastNameLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.3),
                lastNameLabel.getWidth(),
                lastNameLabel.getHeight());
        container.add(lastNameLabel);

        lastNameInput = new JTextField();
        lastNameInput.setSize(100,20);
        lastNameInput.setBounds((containerHalfPoint - (int)(lastNameInput.getWidth()/2)),
                (int)(container.getHeight() * 0.35),
                lastNameInput.getWidth(),
                lastNameInput.getHeight());
        container.add(lastNameInput);
        
        //confirm password
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setSize(100,20);
        passwordLabel.setBounds((containerHalfPoint - (int)(passwordLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.4),
                passwordLabel.getWidth(),
                passwordLabel.getHeight());
        container.add(passwordLabel);

        passwordInput = new JTextField();
        passwordInput.setSize(100,20);
        passwordInput.setBounds((containerHalfPoint - (int)(passwordInput.getWidth()/2)),
                (int)(container.getHeight() * 0.45),
                passwordInput.getWidth(),
                passwordInput.getHeight());
        container.add(passwordInput);
        
        //name
        JLabel confirmPasswordLabel = new JLabel();
        confirmPasswordLabel.setText("Confirm Password:");
        confirmPasswordLabel.setSize(120,20);
        confirmPasswordLabel.setBounds((containerHalfPoint - (int)(confirmPasswordLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.5),
                confirmPasswordLabel.getWidth(),
                confirmPasswordLabel.getHeight());
        container.add(confirmPasswordLabel);

        confirmPasswordInput = new JTextField();
        confirmPasswordInput.setSize(100,20);
        confirmPasswordInput.setBounds((containerHalfPoint - (int)(confirmPasswordInput.getWidth()/2)),
                (int)(container.getHeight() * 0.55),
                confirmPasswordInput.getWidth(),
                confirmPasswordInput.getHeight());
        container.add(confirmPasswordInput);
        
        //email
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email:");
        emailLabel.setSize(100,20);
        emailLabel.setBounds( (containerHalfPoint - (int)(emailLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.6),
                emailLabel.getWidth(),
                emailLabel.getHeight());
        container.add(emailLabel);
        
        emailInput = new JTextField();
        emailInput.setSize(100,20);
        emailInput.setBounds( (containerHalfPoint - (int)(emailInput.getWidth()/2)),
                (int)(container.getHeight() * 0.65),
                emailInput.getWidth(),
                emailInput.getHeight());
        container.add(emailInput);
        
        //major
        JLabel majorLabel = new JLabel();
        majorLabel.setText("Major:");
        majorLabel.setSize(100,20);
        majorLabel.setBounds( (containerHalfPoint - (int)(majorLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.7),
                majorLabel.getWidth(),
                majorLabel.getHeight());
        container.add(majorLabel);
        
        majorInput = new JTextField();
        majorInput.setSize(100, 20);
        majorInput.setBounds( (containerHalfPoint - (int)(majorInput.getWidth()/2)),
                (int)(container.getHeight() * 0.75),
                majorInput.getWidth(),
                majorInput.getHeight());
        container.add(majorInput);
        //submit button
        
        JButton submit = new JButton("Submit");
        submit.setSize(back.getSize());
        submit.setBounds( ((container.getWidth()/2) - (submit.getWidth()/2)),
                (int)(container.getHeight() * 0.85),
                submit.getWidth(),
                submit.getHeight());
        
        ActionListener sumbimissionListener = evt ->{
            
            username = firstNameInput.getText();
            password = lastNameInput.getText();
            passwordCon = passwordInput.getText();
            name = confirmPasswordInput.getText();
            email = emailInput.getText();
            major = majorInput.getText();
            
            if(username.isBlank() || password.isBlank() || name.isBlank() || email.isBlank() || passwordCon.isBlank() ){
                    
                JOptionPane.showMessageDialog( getParent(), 
                    "1 or more required fields empty", 
                    "Sign Up Failed", 
                    JOptionPane.ERROR_MESSAGE);
                
            }else{
                //create new row in student table in database and populate it with the entered information
                //INSERT INTO student (student_ID, student_name, student_email, student_username, student_password, student_major)
                //VALUES ((generated ID, not sure how this would work yet), 
                //'"+name+"', '"+email+"', '"+username+"', '"+password+"', '"+major+"');
                
                if(password.equals(passwordCon)){
                    
                    byte[] salt = EnrollmentSubSystem.createSalt();
                    byte[] hash = EnrollmentSubSystem.passwordHash(password, salt);
                    
                    String HashHex = EnrollmentSubSystem.bitArrayToHex(hash);
                    String SaltHex = EnrollmentSubSystem.bitArrayToHex(salt);
                    
                    try{
                    Connection con = EnrollmentSubSystem.getSQLConnection();
                    Statement statement = con.createStatement();
                    //String sql = "INSERT INTO students"
                    //statement.executeUpdate(sql);
                    
                    } catch(SQLException ex) {
                        
                    }
                    
                    
                    JOptionPane.showMessageDialog( getParent(), 
                        "Account Created! \n " + 
                        "Click OK to return to Login Screen",
                        "Sign Up Success", 
                        JOptionPane.PLAIN_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog( getParent(), 
                    "Password Confirmation does not match", 
                    "Sign Up Failed", 
                    JOptionPane.ERROR_MESSAGE);
                }
                
            }
        
        };
        
        submit.addActionListener(sumbimissionListener);
        container.add(BorderLayout.CENTER,submit);

        add(container);
        setVisible(true);
    }
    
    public void emptyTextFields(){
        this.emailInput.setText("");
        this.majorInput.setText("");
        this.confirmPasswordInput.setText("");
        this.lastNameInput.setText("");
        this.passwordInput.setText("");
        this.firstNameInput.setText("");
    }
    
}