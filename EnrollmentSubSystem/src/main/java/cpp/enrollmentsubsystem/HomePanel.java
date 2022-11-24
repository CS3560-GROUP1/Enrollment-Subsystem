/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author LeothEcRz
 */
public class HomePanel extends JFrame{
    
    private JPanel facePanel;
    private CardLayout layout;
    
    public HomePanel(){
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Home");
        
        Dimension ScreenInformation = Toolkit.getDefaultToolkit().getScreenSize(); // Get ScreenSize
        Dimension size = new Dimension( 
                (int)(ScreenInformation.getWidth() * 0.6),
                (int)(ScreenInformation.getHeight() * 0.5) 
        );
       
        
        facePanel = new JPanel();
        layout = new CardLayout(0, 0);
        facePanel.setLayout(layout);
        
        JPanel homePanel = new JPanel(null);
        
        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.red);
        topPanel.setBounds(0, 0, size.width, (int)(size.getHeight() * 0.15));
        homePanel.add(topPanel);

        
        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        
        GridLayout gLayout = new GridLayout(20, 1, 2, 2);
        bottomPanel.setLayout(gLayout);
        bottomPanel.setBackground(Color.blue);
        
        JPanel usePanel;
        for(int i=0; i< 20; i++){
            usePanel = new JPanel();
            usePanel.setSize((int)(size.getWidth() * 0.8), (int)(size.getHeight() * 0.15) );
            usePanel.setBackground(new Color(i*5, i*10, i*12));
            
            bottomPanel.add(usePanel);
        }
                            
        JScrollPane bottomScrollPane = new JScrollPane(bottomPanel);
            int paddingInPixels = 20;
            bottomScrollPane.setBounds(0, topPanel.getHeight(), (size.width - paddingInPixels), ((int)(size.height - topPanel.getHeight())) - paddingInPixels );
            bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            
        
        //
        homePanel.add(bottomScrollPane);
        
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
      
        facePanel.add(homePanel, "home");
        
        add(facePanel);
        
    }
    
    public void startUp(){
        
        layout.show(facePanel, "home");
        setVisible(true);
        
    }

    
    
}
