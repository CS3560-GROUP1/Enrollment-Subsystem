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
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.util.Vector;

/**
 *  The HomePanel JFrmae
 * 
 */
public final class HomePanel extends JFrame{
    
    private JPanel facePanel;
    private CardLayout layout;

    private JPanel bottomPanel;
    private JLabel termLabel;
    private JLabel usernameLabel;
    
    private UserSchedulesPanel uSchedulesPanel;
    private JScrollPane bottomScrollPane;

    public ArrayList<String> dropList;
    public String studentID;
    
    int bottomScrollPaneOffset;
    
    public HomePanel(String stuID){
        super();
        this.studentID = stuID;
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
            leftTopPanel.setBackground(Color.lightGray);
            leftTopPanel.setLayout(new GridBagLayout());
            
            JPanel rightTopPanel = new JPanel();
            rightTopPanel.setBounds(size.width/2, 0, size.width/2, topPanel.getHeight());
            rightTopPanel.setBackground(Color.gray);

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
                        SearchPanel searchPanel =
                        new SearchPanel(false);
                        searchPanel.setVisible(true);

                    }
                    case "Cart" -> {
                        
                        ActionListener innerAL = ev -> {
                            dispose();
                            String id = ev.getActionCommand();
                            
                            System.out.println(id);
                                    
                            HomePanel hPanel = new HomePanel(id);
                            hPanel.populateHomePanel();
                            hPanel.setVisible(true);
                        };
                        
                        
                        CartPanel cartPanel =
                        new CartPanel(studentID, innerAL);
                        cartPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        cartPanel.setVisible(true);

                    }
                    case "Enroll" ->{
                        System.out.println(" Enroll ");

                    }
                    case "Drop selected" -> {
                        methods m = new methods();
                        dropList.forEach((n) -> m.dropSection(studentID, n));
                        populateHomePanel();
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

            JPopupMenu menu1 = new JPopupMenu();
            
            JMenuItem mi01 = new JMenuItem();
            mi01.setText("Winter");
            mi01.addActionListener(menuListner);
            mi01.setActionCommand("Winter");
            
            JMenuItem mi02 = new JMenuItem();
            mi02.setText("Spring");
            mi02.addActionListener(menuListner);
            mi02.setActionCommand("Spring");
            
            JMenuItem mi03 = new JMenuItem();
            mi03.setText("Summer");
            mi03.addActionListener(menuListner);
            mi03.setActionCommand("Summer");
            
            JMenuItem mi04 = new JMenuItem();
            mi04.setText("Fall");
            mi04.addActionListener(menuListner);
            mi04.setActionCommand("Fall");
            
            menu1.add(mi01);
            menu1.add(mi02);
            menu1.add(mi03);
            menu1.add(mi04);
            
            JButton changeTerm = new JButton("Change Term");
            ActionListener termListener = evt -> {
                menu1.show(rightTopPanel, changeTerm.getX(), changeTerm.getY() + changeTerm.getHeight()); // menu under button
            };
            changeTerm.addActionListener(termListener);
            rightTopPanel.add(changeTerm);
            
            JPopupMenu menu = new JPopupMenu();

            JButton dropClassesButton = new JButton("Drop selected");
            dropClassesButton.setActionCommand("Drop selected");
            dropClassesButton.addActionListener(menuListner);
            rightTopPanel.add(dropClassesButton);
            
           

            JMenuItem mi1 = new JMenuItem();
            mi1.setText("Search");
            mi1.addActionListener(menuListner);
            mi1.setActionCommand("Search");

            JMenuItem mi2 = new JMenuItem();
            mi2.setText("Cart");
            mi2.addActionListener(menuListner);
            mi2.setActionCommand("Cart");

            JMenuItem mi4 = new JMenuItem();
            mi4.setText("Sign Out");
            mi4.addActionListener(menuListner);
            mi4.setActionCommand("Sign Out");
            
            menu.add(mi1);
            menu.add(mi2);
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
        bottomPanel.setBackground(Color.darkGray);
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

    public void populateHomePanel(){
        
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
            dropList = new ArrayList<String>();
            uSchedulesPanel = new UserSchedulesPanel(this.getSize(), con, studentID, dropList);
            bottomScrollPane = new JScrollPane(uSchedulesPanel);
            bottomScrollPane.setBounds(bottomScrollPaneOffset, bottomScrollPaneOffset, uSchedulesPanel.getWidth() + 25, (int)(bottomPanel.getHeight() * 0.8) );
            bottomPanel.add(bottomScrollPane);
             
            
        } catch (SQLException e) { System.err.println( "Connection Failed (Home Panel Population): " + e.toString() ); }
        
         
        layout.show(facePanel, "home");
        setVisible(true);
        
    }
    
    
}
