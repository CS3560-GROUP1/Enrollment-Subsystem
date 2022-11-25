/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.BorderLayout;
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
import javax.swing.ScrollPaneConstants;

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
        setSize(size);
       
        
        facePanel = new JPanel();
        layout = new CardLayout(0, 0);
        facePanel.setLayout(layout);
        
        //HomePanel Container
        JPanel homePanel = new JPanel(null);
        homePanel.setSize(size);
        
            //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.red);
        topPanel.setBounds(0, 0, size.width, (int)(size.getHeight() * 0.20));
        homePanel.add(topPanel);

        
            //Bottom Panel - Set up will be moved to after a user logins to receive from where to pull home screen data.
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setBounds(0, topPanel.getHeight(), (size.width), (int)(size.height - topPanel.getHeight()));
        
            int entries = 20;
            JPanel innerBottomPanel = new JPanel(null);
            innerBottomPanel.setSize( (int)(size.getWidth() * 0.8), (entries * ((int)(size.getHeight() * 0.25)) ) ); // inner panell size set
            innerBottomPanel.setPreferredSize(innerBottomPanel.getSize());

            JPanel usePanel; // Will be moved to its own panel class
            for(int i=0; i< entries; i++){
                usePanel = new JPanel();
                usePanel.setBounds( 0, i*(int)(size.getHeight() * 0.25), (innerBottomPanel.getWidth()), (int)(size.getHeight() * 0.25));
                usePanel.setBackground(new Color(i*5, i*10, i*12));

                innerBottomPanel.add(usePanel);
            }

            JScrollPane bottomScrollPane = new JScrollPane(innerBottomPanel);
            bottomScrollPane.setBackground(Color.blue);
            bottomScrollPane.setBounds(10, 10, innerBottomPanel.getWidth() + 25, bottomPanel.getHeight() - 60);
            
        bottomPanel.add(bottomScrollPane);
        
        
        homePanel.add(bottomPanel);
        
        //
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
