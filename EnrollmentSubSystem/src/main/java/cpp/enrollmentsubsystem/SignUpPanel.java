/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author lpera00
 */
public class SignUpPanel extends JFrame{
    
    public SignUpPanel(){
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel container = new JPanel(null);
        //back button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close this window and open menu window
                String[] args = {};
                EnrollmentSubSystem.main(args);
                dispose();
            }
        });
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
        JPasswordField pwInput = new JPasswordField();
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
        container.add(submit);
        add(container);
    }
    
    
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
            new SignUpPanel().setVisible(true);
        });
    }
   
}