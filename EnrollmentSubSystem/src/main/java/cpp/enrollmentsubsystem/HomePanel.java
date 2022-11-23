/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author LeothEcRz
 */
public class HomePanel extends JFrame{
    
    public HomePanel(){
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
        Dimension size = new Dimension( 
                (int)(ScreenInformation.getWidth() * 0.6),
                (int)(ScreenInformation.getHeight() * 0.5) 
        );
        setSize(size);
        setLocationRelativeTo(null);
        
        
    }
    
}
