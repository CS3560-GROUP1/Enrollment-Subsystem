/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author lpera00
 */
public class SignUpPanel extends JPanel{
    
    String username;    
    String password;
    String name;
    String email;
    String major;

    public SignUpPanel(ActionListener AL){
        username = "";        
        password = "";
        name = "";
        email = "";
        major = "";

        JPanel container = new JPanel(null);
        container.setSize(4000, 500);
        //back button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 20);
        back.addActionListener(AL);
        back.setActionCommand("SignUp Back");
        container.add(back);
        //title
        JLabel title = new JLabel();
        title.setText("Create an Account");
        title.setBounds(250, 10, 150, 20);
        container.add(title);
        //username
        JLabel unLabel = new JLabel();
        unLabel.setText("Username:");
        unLabel.setBounds(165, 40, 70, 20);
        container.add(unLabel);
        JTextField unInput = new JTextField();
        unInput.setBounds(230, 40, 200, 20);
        container.add(unInput);
        //password
        JLabel pwLabel = new JLabel();
        pwLabel.setText("Password:");
        pwLabel.setBounds(165, 70, 70, 20);
        container.add(pwLabel);

        JTextField pwInput = new JTextField();
        pwInput.setBounds(230, 70, 200, 20);
        container.add(pwInput);
        //name
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name:");
        nameLabel.setBounds(190, 100, 70, 20);
        container.add(nameLabel);
        JTextField nameInput = new JTextField();
        nameInput.setBounds(230, 100, 200, 20);
        container.add(nameInput);
        //email
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email:");
        emailLabel.setBounds(190, 130, 70, 20);
        container.add(emailLabel);
        JTextField emailInput = new JTextField();
        emailInput.setBounds(230, 130, 200, 20);
        container.add(emailInput);
        //major
        JLabel majorLabel = new JLabel();
        majorLabel.setText("Major:");
        majorLabel.setBounds(190, 160, 70, 20);
        container.add(majorLabel);
        JTextField majorInput = new JTextField();
        majorInput.setBounds(230, 160, 200, 20);
        container.add(majorInput);
        //submit button
        JButton submit = new JButton("Submit");
        submit.setBounds(275, 190, 80, 20);
        submit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                //assign entered values to variables
                username = unInput.getText();
                password = pwInput.getText();
                name = nameInput.getText();
                email = emailInput.getText();
                major = majorInput.getText();
                
                //check if 1 or more required fields is empty
                if(username.isBlank() || password.isBlank() || name.isBlank() || email.isBlank()){
                    JFrame alertFrame = new JFrame();
                    alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    alertFrame.setVisible(false);
                    JOptionPane.showMessageDialog(alertFrame, 
                        "1 or more required fields empty", 
                        "Sign Up Failed", 
                        JOptionPane.ERROR_MESSAGE);
                    alertFrame.dispose();
                }
                else{
                    //create new row in student table in database and populate it with the entered information
                    //INSERT INTO student (student_ID, student_name, student_email, student_username, student_password, student_major)
                    //VALUES ((generated ID, not sure how this would work yet), 
                    //'"+name+"', '"+email+"', '"+username+"', '"+password+"', '"+major+"');
                    JFrame alertFrame = new JFrame();
                    alertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    alertFrame.setVisible(false);
                    JOptionPane.showMessageDialog(alertFrame, 
                        "Account Created!\n"
                        + "Click OK to return to menu screen",
                        "Sign Up Success", 
                        JOptionPane.PLAIN_MESSAGE);
                    alertFrame.dispose();
                    
            }
        }});
        container.add(submit);
        add(container);
    }
    
   
}