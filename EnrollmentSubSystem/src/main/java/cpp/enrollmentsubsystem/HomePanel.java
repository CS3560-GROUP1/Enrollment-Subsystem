/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpp.enrollmentsubsystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

    private JPanel bottomPanel;
    private JLabel termLabel;
    private JLabel usernameLabel;
    
    private UserSchedulesPanel uSchedulesPanel;
    private JScrollPane bottomScrollPane;
    
    int bottomScrollPaneOffset;
    
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
       
        bottomScrollPaneOffset = (int) (size.getWidth() / 50);
        
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
            leftTopPanel.setLayout(new GridBagLayout());
            
            JPanel rightTopPanel = new JPanel();
            rightTopPanel.setBounds(size.width/2, 0, size.width/2, topPanel.getHeight());
            rightTopPanel.setBackground(Color.CYAN);

            {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = (int) (size.getHeight() * 0.05);
            JLabel myScheduleLabel = new JLabel("My Schedule - Term: ");
            c.gridx = 1;
            c.gridy = 1;
            leftTopPanel.add(myScheduleLabel, c);
            termLabel = new JLabel("Mock Term 2022");
        
            c.gridx = 3;
            c.gridy = 1;
            leftTopPanel.add(termLabel, c);

            usernameLabel = new JLabel("Mock Name");
            float fontSize = size.width / 50;
            usernameLabel.setFont(usernameLabel.getFont().deriveFont(fontSize));
            c.gridx = 2;
            c.gridy = 3;
            leftTopPanel.add(usernameLabel, c);
            }

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
                    case "Sign Out" -> {
                        new LoginPanel().setVisible(true);
                        dispose();
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
            mi2.setText("Cart");
            mi2.addActionListener(menuListner);
            mi2.setActionCommand("Cart");
            
            JMenuItem mi3 = new JMenuItem();
            mi3.setText("Enroll");
            mi3.addActionListener(menuListner);
            mi3.setActionCommand("Enroll");
            
            JMenuItem mi4 = new JMenuItem();
            mi4.setText("Sign Out");
            mi4.addActionListener(menuListner);
            mi4.setActionCommand("Sign Out");
            
            menu.add(mi1);
            menu.add(mi2);
            menu.add(mi3);
            menu.add(mi4);


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
        bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setBounds(0, topPanel.getHeight(), (size.width), (int)(size.height - topPanel.getHeight() ));     
            
            uSchedulesPanel = new UserSchedulesPanel(size);
            bottomScrollPane = new JScrollPane(uSchedulesPanel);
            bottomScrollPane.setBounds(bottomScrollPaneOffset, bottomScrollPaneOffset, uSchedulesPanel.getWidth() + 25, (int)(bottomPanel.getHeight() * 0.8) );
            
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
            
            //System.out.println(studentID);
            
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
            
            bottomScrollPane.remove(uSchedulesPanel);
            bottomPanel.remove(bottomScrollPane);
            uSchedulesPanel = new UserSchedulesPanel(this.getSize(), con, studentID);
            bottomScrollPane = new JScrollPane(uSchedulesPanel);
            bottomScrollPane.setBounds(bottomScrollPaneOffset, bottomScrollPaneOffset, uSchedulesPanel.getWidth() + 25, (int)(bottomPanel.getHeight() * 0.8) );
            bottomPanel.add(bottomScrollPane);
            
            
        } catch (SQLException e) { System.err.println( "Connection Failed (Home Panel Population): " + e.toString() ); }
        
         
        layout.show(facePanel, "home");
        setVisible(true);
        
    }
    
    
}
