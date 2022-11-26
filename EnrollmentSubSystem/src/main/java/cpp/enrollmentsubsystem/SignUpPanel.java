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
    
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField passwordInput;
    private JTextField confirmPasswordInput;
    private JTextField studentIDInput;
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
        JLabel studentIDLabel = new JLabel();
        studentIDLabel.setText("StudentID: ");
        studentIDLabel.setSize(100,20);
        studentIDLabel.setBounds((containerHalfPoint - (int)(studentIDLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.6),
                studentIDLabel.getWidth(),
                studentIDLabel.getHeight());
        container.add(studentIDLabel);
        
        studentIDInput = new JTextField();
        studentIDInput.setSize(100,20);
        studentIDInput.setBounds((containerHalfPoint - (int)(studentIDInput.getWidth()/2)),
                (int)(container.getHeight() * 0.65),
                studentIDInput.getWidth(),
                studentIDInput.getHeight());
        container.add(studentIDInput);
        
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
        submit.setActionCommand("SignUp Submit");
        
        ActionListener sumbimissionListener = evt ->{
            
            int confirm = 1;
            
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String password = passwordInput.getText();
            String passwordConfirmation = confirmPasswordInput.getText();
            String studentID = studentIDInput.getText();
            String major = majorInput.getText();
            
            if(firstName.isBlank() || password.isBlank() || lastName.isBlank() || studentID.isBlank() || passwordConfirmation.isBlank() || major.isBlank() ){
                    
                JOptionPane.showMessageDialog( getParent(), 
                    "1 or more required fields empty", 
                    "Sign Up Failed", 
                    JOptionPane.ERROR_MESSAGE);
                
            }else{
                
                if(password.equals(passwordConfirmation)){
                    
                    byte[] salt = EnrollmentSubSystem.createSalt();
                    byte[] hash = EnrollmentSubSystem.passwordHash(password, salt);
                    
                    String HashHex = EnrollmentSubSystem.bitArrayToHex(hash);
                    String SaltHex = EnrollmentSubSystem.bitArrayToHex(salt);
                    
                    try{
                        Connection con = EnrollmentSubSystem.getSQLConnection();
                        
                        String sql = "INSERT INTO students (first_Name, last_Name, studentID, major) VALUES (?,?,?,?);";
                        PreparedStatement statement = con.prepareStatement(sql);
                        statement.setString(1, firstName);
                        statement.setString(2, lastName);
                        statement.setString(3, studentID);
                        statement.setString(4, major);
                        
                        int row = statement.executeUpdate();
                        if(row != 0){
                            
                            sql = "INSERT INTO logins (studentID, username, password_Hash, password_Salt) VALUES (?,?,?,?)";
                            statement = con.prepareStatement(sql);
                            statement.setString(1, studentID);
                            statement.setString(2, firstName);
                            statement.setString(3, HashHex);
                            statement.setString(4, SaltHex);
                            
                            row = statement.executeUpdate();
                            if(row != 0){
                                
                                confirm = JOptionPane.showConfirmDialog( getParent(), 
                                    "Account Created! \n " + 
                                    "Click OK to return to Login Screen",
                                    "Sign Up Success", 
                                    JOptionPane.PLAIN_MESSAGE);
                                System.out.println(confirm);
                                
                            } else{
                                System.err.println("Unknow Error Insert fail - no rows affected (logins) ");
                            }
                            
                        } else {
                            System.err.println("Unknow Error Insert fail - no rows affected");
                        } // IF
                        
                        con.close();
                    } catch(SQLException ex) {
                        System.err.println(ex.toString());
                    }
                } else {
                    
                    JOptionPane.showMessageDialog( getParent(), 
                    "Password Confirmation does not match", 
                    "Sign Up Failed", 
                    JOptionPane.ERROR_MESSAGE);
                    
                } // IF
                
                if(confirm != 1){
                    AL.actionPerformed(evt);
                }
                
            }
        
        };
        
        submit.addActionListener(sumbimissionListener);
        container.add(BorderLayout.CENTER,submit);

        add(container);
        setVisible(true);
    }
    
    public void emptyTextFields(){
        this.studentIDInput.setText("");
        this.majorInput.setText("");
        this.confirmPasswordInput.setText("");
        this.lastNameInput.setText("");
        this.passwordInput.setText("");
        this.firstNameInput.setText("");
    }
    
}