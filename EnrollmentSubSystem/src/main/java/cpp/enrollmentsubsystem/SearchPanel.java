/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package cpp.enrollmentsubsystem;

/**
 *
 * @author nickafic
 */
public class SearchPanel extends javax.swing.JFrame {
    
    private javax.swing.JButton searchButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> subjectCombo;
    private javax.swing.JComboBox<String> termCombo;
    private javax.swing.JComboBox<String> searchCombo;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel subjectLabel;
    private javax.swing.JLabel courseNumLabel;
    private javax.swing.JLabel termLabel;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JTextField numTextField;
    private javax.swing.JCheckBox openCheck;
    
    /**
     * Creates new form SearchPanel
     */
    public SearchPanel(boolean debugMode) {
        initComponents(debugMode);
    }
                        
    private void initComponents(boolean debugMode) {

        searchLabel = new javax.swing.JLabel();
        subjectLabel = new javax.swing.JLabel();
        subjectCombo = new javax.swing.JComboBox<>();
        termCombo = new javax.swing.JComboBox<>();
        searchCombo = new javax.swing.JComboBox<>();
        courseNumLabel = new javax.swing.JLabel();
        termLabel = new javax.swing.JLabel();
        optionsLabel = new javax.swing.JLabel();
        numTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        openCheck = new javax.swing.JCheckBox("Open classes only", true);
        
        if(debugMode){
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        searchLabel.setText("Course Search");

        termLabel.setText("Term");
        termCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FALL", "SPRING", "SUMMER", "WINTER" }));

        subjectLabel.setText("Subject");

        subjectCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Computer Science", "Philosophy", "Music", "Civil Engineering" }));

        courseNumLabel.setText("Course Number");

        numTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numTextFieldActionPerformed(evt);
            }
        });

        optionsLabel.setText("Search options");
        searchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "test 3", "Item 2", "Item 3", "Item 4" }));


        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(searchLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(courseNumLabel)
                                .addGap(18, 18, 18)
                                .addComponent(numTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(subjectLabel)
                                .addGap(64, 64, 64)
                                .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(termLabel)
                                .addGap(64, 64, 64)
                                .addComponent(termCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(optionsLabel)
                                .addGap(64, 64, 64)
                                .addComponent(searchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(openCheck)
                            .addComponent(searchButton)
                            .addComponent(clearButton))))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(termCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termLabel))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subjectLabel))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseNumLabel))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optionsLabel))
                .addComponent(openCheck)
                .addGap(27, 27, 27)
                .addComponent(searchButton)
                .addComponent(clearButton)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }                        

    private void numTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        
    }                                           

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.out.println(termCombo.getItemAt(termCombo.getSelectedIndex()));
        methods m = new methods();
        m.search(termCombo.getItemAt(termCombo.getSelectedIndex()), numTextField.getText(), subjectCombo.getItemAt(subjectCombo.getSelectedIndex()));
        dispose();
    }    
    
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        numTextField.setText(null);
    }


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPanel(true).setVisible(true);
            }
        });
    }
                 
}