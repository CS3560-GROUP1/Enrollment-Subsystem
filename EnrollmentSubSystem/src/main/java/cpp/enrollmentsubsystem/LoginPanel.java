/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author LeothEcRz
 */
public class LoginPanel extends JFrame {
    
    public LoginPanel() {
        super();
        setTitle("Mock LoginPanel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.height = (int) (screen.getHeight() / 2);
        screen.width = (int) (screen.getWidth() / 5);
        setSize(screen);
        setLocationRelativeTo(null);
        
        JPanel mockLogin = new JPanel();
        mockLogin.setPreferredSize(getPreferredSize());
        mockLogin.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JLabel mainLogo = new JLabel("\n Login Page \n");
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 2;
        c.ipady = (int)(screen.getHeight()/10);
        mockLogin.add(mainLogo, c);
        
        c.ipady = 0;
        
        JLabel usernameJLabel = new JLabel("Username: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        mockLogin.add(usernameJLabel, c);
        
        JTextField usernameJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 5;
        mockLogin.add(usernameJTextField, c);

        JLabel passwordJLabel = new JLabel("Password: ");
        c.gridx = 1;
        c.gridy = 7;
        mockLogin.add(passwordJLabel, c);

        JTextField passwordJTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 8;
        
        mockLogin.add(passwordJTextField, c);
        add(mockLogin);
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {
                new LoginPanel().setVisible(true);
        });
    }
    
}
