
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 */
public class EnrollmentSubSystem {
    
    private JFrame mainFrame;
    
    public EnrollmentSubSystem(){
        mainFrame = new JFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(new Dimension(600, 400));
        mainFrame.setTitle("Enrollment Subsystem");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel face = new JPanel();
        CardLayout layout = new CardLayout(0, 0);
        face.setLayout(layout);
        
        JPanel firstScreen = new JPanel();
        face.add(firstScreen, firstScreen.getName());
        
        mainFrame.add(face);
        layout.show(face, firstScreen.getName());
        mainFrame.setVisible(true);
    }
    
    /**
     * Entry Point
     * @param args 
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Enrollment();
            }
        });
    }
}
