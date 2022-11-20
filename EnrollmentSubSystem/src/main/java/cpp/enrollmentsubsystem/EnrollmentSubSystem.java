
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 */
public class EnrollmentSubSystem {
    
    private JFrame mainFrame;
    
    public EnrollmentSubSystem(){
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(600, 400));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setTitle("Enrollment Subsystem");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel face = new JPanel();
        CardLayout layout = new CardLayout(0, 0);
        face.setLayout(layout);
        
        JPanel firstScreen = new JPanel();
        face.add(firstScreen, firstScreen.getName());
        
        mainFrame.add(face);
        layout.show(face, firstScreen.getName());
        
        firstScreen.setLayout(new BoxLayout(firstScreen, BoxLayout.Y_AXIS));
        JLabel title = new JLabel();
        title.setText("Enrollment Subsystem Mock UI");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstScreen.add(title);
        JButton login = new JButton("log in screen");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close firstScreen window and open login window
                String[] args = {};
                LoginPanel.main(args);
                mainFrame.dispose();
            }
        });
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstScreen.add(login);
        JButton search = new JButton("search screen");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close firstScreen window and open search window
                String[] args = {};
                SearchPanel.main(args);
                mainFrame.dispose();
            }
        });
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstScreen.add(search);
        JButton createAcct = new JButton("create account screen");
        createAcct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close firstScreen window and open search window
                String[] args = {};
                SignUpPanel.main(args);
                mainFrame.dispose();
            }
        });
        createAcct.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstScreen.add(createAcct);
    }
    
    public void start(){
        mainFrame.setVisible(true);
    }
    
    /**
     * Entry Point
     * @param args 
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new EnrollmentSubSystem().start();
        });
    }
}
