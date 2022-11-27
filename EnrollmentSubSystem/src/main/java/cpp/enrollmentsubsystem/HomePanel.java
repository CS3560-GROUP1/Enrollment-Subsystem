/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
/**
 *
 * @author LeothEcRz
 */
public class HomePanel extends JFrame{
    
    private JPanel facePanel;
    private CardLayout layout;

    private JLabel termLabel;
    private JLabel usernameLabel;
    
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
        JPanel topPanel = new JPanel(null);
            topPanel.setBackground(Color.red);
            topPanel.setBounds(0, 0, size.width, (int)(size.getHeight() * 0.20));

            //LEFT
            JPanel leftTopPanel = new JPanel();
            leftTopPanel.setBounds(0,0,size.width/2, topPanel.getHeight() );
            leftTopPanel.setBackground(Color.green);

            JPanel rightTopPanel = new JPanel();
            rightTopPanel.setBounds(size.width/2, 0, size.width/2, topPanel.getHeight());
            rightTopPanel.setBackground(Color.CYAN);

            JLabel myScheduleLabel = new JLabel("My Schedule - Term: ");
            leftTopPanel.add(myScheduleLabel);
            termLabel = new JLabel("Mock Term 2022");
            leftTopPanel.add(termLabel);

            usernameLabel = new JLabel("Mock Name");
            leftTopPanel.add(usernameLabel);

            //RIGHT
            ActionListener menuListner = evt -> {
                switch(evt.getActionCommand()){
                    case "Search" ->{
                        System.out.println(" Search" );
                        
                        new SearchPanel(false).setVisible(true);

                    }
                    case "Enroll" ->{
                        System.out.println(" Enroll ");

                    }
                    case "Change Term" -> {
                        System.out.println(" Change Term ");
                    }
                    default -> {
                        System.out.println(evt.toString());
                    }
                }
            };

            JPopupMenu menu = new JPopupMenu();

            JButton changeTermButton = new JButton("Change Term");
            changeTermButton.setActionCommand("Change Term");
            changeTermButton.addActionListener(menuListner);
            rightTopPanel.add(changeTermButton);

            JMenuItem mi1 = new JMenuItem();
            mi1.setText("Search");
            mi1.addActionListener(menuListner);
            mi1.setActionCommand("Search");

            JMenuItem mi2 = new JMenuItem();
            mi2.setText("Enroll");
            mi2.addActionListener(menuListner);
            mi2.setActionCommand("Enroll");
            
            menu.add(mi1);
            menu.add(mi2);

            JButton menuButton = new JButton("Menu");
            ActionListener menuActivator = evt -> {
                menu.show(rightTopPanel, menuButton.getX(), menuButton.getY() + menuButton.getHeight()); // menu under button
            };
            menuButton.addActionListener(menuActivator);

            rightTopPanel.add(menuButton);

            topPanel.add(leftTopPanel);
            topPanel.add(rightTopPanel);

        homePanel.add(topPanel);

            //Bottom Panel - Set up will be moved to after a user logins to receive from where to pull home screen data.
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setBounds(0, topPanel.getHeight(), (size.width), (int)(size.height - topPanel.getHeight() ));
        
            int entries = 20;
            JPanel innerBottomPanel = new JPanel(null); // TO BE Moved to method with sql query to reach for information
            innerBottomPanel.setSize( (int)(size.getWidth() * 0.8), (entries * ((int)(size.getHeight() * 0.25)) ) ); // inner panell size set
            innerBottomPanel.setPreferredSize(innerBottomPanel.getSize()); // scroll requires a preferred size

            JPanel usePanel; // Will be moved to its own panel class
            for(int i=0; i< entries; i++){
                usePanel = new JPanel();
                usePanel.setBounds( 0, i*(int)(size.getHeight() * 0.25), (innerBottomPanel.getWidth()), (int)(size.getHeight() * 0.25));
                usePanel.setBackground(new Color(i*5, i*10, i*12));

                innerBottomPanel.add(usePanel);
            }
            

            JScrollPane bottomScrollPane = new JScrollPane(innerBottomPanel);
            bottomScrollPane.setBackground(Color.blue);
            bottomScrollPane.setBounds(10, 10, innerBottomPanel.getWidth() + 25, (int)(bottomPanel.getHeight() * 0.8) );
            
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

    public void populateHomePanel(String studentID){
        
        try (Connection con = EnrollmentSubSystem.getSQLConnection() ){
            
            System.out.println(studentID);
            
            String sql0 = "SELECT * FROM students WHERE studentID=?; ";
            PreparedStatement pSta = con.prepareStatement(sql0);
            pSta.setString(1, studentID);
            ResultSet RS0 = pSta.executeQuery();
            
            if(!(RS0.isBeforeFirst())){
                System.err.println(" Empty Result Set - populate home panel");
            } else {
                
                if(RS0.next()){
                    String tempFName = RS0.getString("first_Name");
                    String tempLName = RS0.getString("last_Name");
                    usernameLabel.setText(tempFName.concat(" " + tempLName));
                }
                
            }
            
            
        } catch (SQLException e) { System.err.println( "Connection Failed (Home Panel Population): " + e.toString() ); }
        
         
        layout.show(facePanel, "home");
        setVisible(true);
        
    }
    
    
}
