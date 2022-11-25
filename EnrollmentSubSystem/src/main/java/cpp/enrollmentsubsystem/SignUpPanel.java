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

/**
 * 
 */
public class SignUpPanel extends JPanel{
    
    String username;    
    String password;
    String name;
    String email;
    String major;

    public SignUpPanel(Dimension size , ActionListener AL){
        
        setSize(size);
        int BorderPadding = 20;
        setLayout(new BorderLayout(BorderPadding, BorderPadding));
        JPanel container = new JPanel(null); // TOP LEVEL container
        container.setSize(new Dimension(getWidth() - BorderPadding, getHeight() - BorderPadding));
        container.setVisible(true);

        int containerHalfPoint = (int)(container.getWidth()/2);
        
        //back button
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
        title.setSize(100, 20);
        title.setBounds( (containerHalfPoint - (int)(title.getWidth()/2)),
                (int)(container.getHeight() * 0.10),
                title.getWidth(),
                title.getHeight());
        container.add(title);
        
        
        //username
        JLabel unLabel = new JLabel();
        unLabel.setText("Username:");
        unLabel.setSize(100, 20);
        unLabel.setBounds( (containerHalfPoint - (int)(unLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.20),
                unLabel.getWidth(),
                unLabel.getHeight());
        container.add(unLabel);

        JTextField unInput = new JTextField();
        unInput.setSize(100, 20);
        unInput.setBounds( (containerHalfPoint - (int)(unInput.getWidth()/2)),
                (int)(container.getHeight() * 0.25),
                unInput.getWidth(),
                unInput.getHeight());
        container.add(unInput);
        
        //password
        JLabel pwLabel = new JLabel();
        pwLabel.setText("Password:");
        pwLabel.setSize(100,20);
        pwLabel.setBounds( (containerHalfPoint - (int)(pwLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.3),
                pwLabel.getWidth(),
                pwLabel.getHeight());
        container.add(pwLabel);

        JTextField pwInput = new JTextField();
        pwInput.setSize(100,20);
        pwInput.setBounds( (containerHalfPoint - (int)(pwInput.getWidth()/2)),
                (int)(container.getHeight() * 0.35),
                pwInput.getWidth(),
                pwInput.getHeight());
        container.add(pwInput);
        
        //confirm password
        JLabel pwconLabel = new JLabel();
        pwconLabel.setText("Password:");
        pwconLabel.setSize(100,20);
        pwconLabel.setBounds( (containerHalfPoint - (int)(pwconLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.4),
                pwconLabel.getWidth(),
                pwconLabel.getHeight());
        container.add(pwconLabel);

        JTextField pwconInput = new JTextField();
        pwconInput.setSize(100,20);
        pwconInput.setBounds( (containerHalfPoint - (int)(pwconInput.getWidth()/2)),
                (int)(container.getHeight() * 0.45),
                pwconInput.getWidth(),
                pwconInput.getHeight());
        container.add(pwconInput);
        
        //name
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name:");
        nameLabel.setSize(100,20);
        nameLabel.setBounds( (containerHalfPoint - (int)(nameLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.5),
                nameLabel.getWidth(),
                nameLabel.getHeight());
        container.add(nameLabel);

        JTextField nameInput = new JTextField();
        nameInput.setSize(100,20);
        nameInput.setBounds( (containerHalfPoint - (int)(nameInput.getWidth()/2)),
                (int)(container.getHeight() * 0.55),
                nameInput.getWidth(),
                nameInput.getHeight());
        container.add(nameInput);
        
        //email
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email:");
        emailLabel.setSize(100,20);
        emailLabel.setBounds( (containerHalfPoint - (int)(emailLabel.getWidth()/2)),
                (int)(container.getHeight() * 0.6),
                emailLabel.getWidth(),
                emailLabel.getHeight());
        container.add(emailLabel);
        
        JTextField emailInput = new JTextField();
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
        
        JTextField majorInput = new JTextField();
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
            
            username = unInput.getText();
            password = pwInput.getText();
            name = nameInput.getText();
            email = emailInput.getText();
            major = majorInput.getText();
            
            if(username.isBlank() || password.isBlank() || name.isBlank() || email.isBlank()){
                    
                JOptionPane.showMessageDialog( getParent(), 
                    "1 or more required fields empty", 
                    "Sign Up Failed", 
                    JOptionPane.ERROR_MESSAGE);
                
            }else{
                //create new row in student table in database and populate it with the entered information
                //INSERT INTO student (student_ID, student_name, student_email, student_username, student_password, student_major)
                //VALUES ((generated ID, not sure how this would work yet), 
                //'"+name+"', '"+email+"', '"+username+"', '"+password+"', '"+major+"');
                
                
                
                
                JOptionPane.showMessageDialog( getParent(), 
                    "Account Created!\n"
                    + "Click OK to return to menu screen",
                    "Sign Up Success", 
                    JOptionPane.PLAIN_MESSAGE);
                
            }
        
        };
        
        submit.addActionListener(sumbimissionListener);
        container.add(BorderLayout.CENTER,submit);

        add(container);
        setVisible(true);
    }
    
    public void emptyTextFields(){
        
    }
    
}