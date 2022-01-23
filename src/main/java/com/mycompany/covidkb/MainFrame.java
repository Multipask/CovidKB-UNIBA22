/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import com.mycompany.database.DatabaseHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mycompany.parser.*;
import com.mycompany.exceptions.WrongQueryFormulationException;

import java.awt.Color;
import javax.swing.JLabel;

import com.mycompany.gui.BackgroundLabel;
import java.sql.SQLException;

/**
 *
 * @author giuse
 */
public class MainFrame extends javax.swing.JFrame {
    
    private final static String TITLE = "COVID KB";
    private final static String INFO = "Ask a Query. (Digit \"help\" if needed)";
        
    private TopDownResolver resolver;
    
    private JLabel backgroundLabel;
    private JLabel outputAreaBackgroundLabel;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        this.resolver = this.getResolver();
        initComponents();
        customInit();
    }
    
    private TopDownResolver getResolver(){
        
        //Parte di acquisizione di atomi e assiomi da DB
        Set<Atom> atoms = new HashSet<>();
        List<PropositionalDefiniteClause> covidKb = new ArrayList<>();
        
        try {
            atoms = DatabaseHandler.getDBHandler().downloadAllAtoms();
            covidKb = DatabaseHandler.getDBHandler().downloadAllPropositions();
        } catch (SQLException ex){
            this.setOutput(ex.getMessage());
        }
        
        // MESSAGGIO DI ERRORE E CHIUSURA PROGRAMMA SE ATOMS O COVIDKB SONO VUOTI
        
        //Creazione di un TopDownResolver sugli assiomi caricati
        TopDownResolver tdr = new TopDownResolver(this, atoms, covidKb);
        
        return tdr;
    }

    private void customInit(){
        this.setTitle(TITLE);
                
        // <editor-fold defaultstate="collapsed" desc="Setting up title and info labels">
        this.titleLabel.setText(MainFrame.TITLE);
        this.titleLabel.setForeground(Color.white);
        this.infoLabel.setText(MainFrame.INFO);
        this.infoLabel.setForeground(Color.white);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Creating and adding background labels">
        
        String backgroundPath = "resources" + System.getProperty("file.separator")+
                                "gui" + System.getProperty("file.separator") + "Background.jpg";
        
        this.backgroundLabel = new BackgroundLabel(backgroundPath,
                this.backgroundPanel.getWidth(), this.backgroundPanel.getHeight());
        this.backgroundPanel.add(backgroundLabel, 
                new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, this.backgroundPanel.getWidth(), this.backgroundPanel.getHeight()));
        
        this.backgroundLabel.setOpaque(false);
        this.backgroundLabel.setBackground(new Color(0, 0, 0, 0));
        this.backgroundLabel.revalidate();
        
        String glassPanelPath = "resources" + System.getProperty("file.separator") +                                
                                "gui" + System.getProperty("file.separator") + "GlassPanel.png";
        
        this.outputAreaBackgroundLabel = new BackgroundLabel(glassPanelPath,
                this.outputAreaBackgroundPanel.getWidth(), this.outputAreaBackgroundPanel.getHeight());
        this.outputAreaBackgroundPanel.add(outputAreaBackgroundLabel, 
                new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, this.outputAreaBackgroundPanel.getWidth(), this.outputAreaBackgroundPanel.getHeight()));
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Defining focus">
        this.setFocusable(true);
        this.inputField.setFocusable(true);
        this.outputAreaScroll.setFocusable(true);
        this.backgroundPanel.setFocusable(false);
        this.backgroundLabel.setFocusable(false);
        this.outputAreaBackgroundPanel.setFocusable(false);
        this.outputAreaBackgroundLabel.setFocusable(false);
        this.outputArea.setFocusable(false);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setting up outputArea">
        this.outputArea.setLineWrap(true);
        this.outputArea.setWrapStyleWord(true);
        this.outputArea.setEditable(false);
        this.outputArea.setForeground(Color.white);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setting up outputArea trasparency">
        Color empty = new Color(0, 0, 0, 0);
        this.outputAreaBackgroundPanel.setBackground(empty);
        this.outputAreaBackgroundPanel.setOpaque(false);
        this.outputArea.setBackground(empty);
        this.outputArea.setOpaque(false);
        this.outputAreaScroll.getViewport().setBackground(empty);
        this.outputAreaScroll.getViewport().setOpaque(false);
        this.outputAreaScroll.setBackground(empty); //opzionale
        this.outputAreaScroll.setOpaque(false); //opzionale
        this.outputArea.setBorder(null);
        this.outputAreaScroll.setViewportBorder(null);
        this.outputAreaScroll.setBorder(null); //opzionale
        // </editor-fold>
    }
    
    private void executeCommand(){
        String query = this.inputField.getText();
        this.inputField.setText("");
        this.cleanOutput();
        
        Parser parser = new Parser();
        
        Command resultingCommand;

        try {
            resultingCommand = parser.decodeQuery(query);
        } catch (WrongQueryFormulationException ex) {
            outputArea.setText(ex.getMessage());
            return;
        }
        
        if (resultingCommand.isHelpCommand()) {
            outputArea.setText("Digit a query in the format: ask [atom] (and [atom])*" + System.lineSeparator());
            outputArea.append("Digit \"axioms\" to show KB axioms" + System.lineSeparator());
            outputArea.append("Digit \"ontology\" to show symbol meanings" + System.lineSeparator());
            outputArea.setCaretPosition(0);
        }
        
        if (resultingCommand.isAskingCommand()) {
            try {
                List<String> askedAtoms = ((AskingCommand) resultingCommand).getAskedAtoms();
                resolver.proveQuery(askedAtoms);
            } catch (WrongQueryFormulationException ex) {
                outputArea.setText(ex.getMessage());
                return;
            }
        }
        
        if(resultingCommand.isShowOntologyCommand()){
            outputArea.setText("Ontology:" + System.lineSeparator());
            outputArea.append(resolver.getFormattedOntology());
            outputArea.setCaretPosition(0);
        }
        
        if(resultingCommand.isShowAxiomsCommand()){
            outputArea.setText("Axioms:" + System.lineSeparator());
            outputArea.append(resolver.getFormattedAxioms());
            outputArea.setCaretPosition(0);
        }
    }
    
    public void setOutput(String content){
        this.outputArea.setText(content);
    }
    
    public void appendOutput(String content){
        this.outputArea.append(System.lineSeparator() + content);
    }
    
    public void cleanOutput(){
        this.outputArea.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        outputAreaBackgroundPanel = new javax.swing.JPanel();
        outputAreaScroll = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 600));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        backgroundPanel.setBackground(new java.awt.Color(153, 255, 255));
        backgroundPanel.setMaximumSize(new java.awt.Dimension(1000, 600));
        backgroundPanel.setMinimumSize(new java.awt.Dimension(1000, 600));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundPanel.add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 13, 882, 34));

        infoLabel.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundPanel.add(infoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 54, 882, 38));

        inputField.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        inputField.setMaximumSize(new java.awt.Dimension(800, 30));
        inputField.setMinimumSize(new java.awt.Dimension(800, 30));
        inputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputFieldKeyPressed(evt);
            }
        });
        backgroundPanel.add(inputField, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 101, 882, -1));

        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        outputArea.setRows(5);
        outputAreaScroll.setViewportView(outputArea);

        javax.swing.GroupLayout outputAreaBackgroundPanelLayout = new javax.swing.GroupLayout(outputAreaBackgroundPanel);
        outputAreaBackgroundPanel.setLayout(outputAreaBackgroundPanelLayout);
        outputAreaBackgroundPanelLayout.setHorizontalGroup(
            outputAreaBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputAreaBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outputAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        outputAreaBackgroundPanelLayout.setVerticalGroup(
            outputAreaBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputAreaBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(outputAreaScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        backgroundPanel.add(outputAreaBackgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 158, -1, -1));

        getContentPane().add(backgroundPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void inputFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputFieldKeyPressed
        switch (evt.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_ENTER:
                this.executeCommand();
        }
    }//GEN-LAST:event_inputFieldKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_ENTER:
                this.executeCommand();
            default:
                this.inputField.grabFocus();
                if(Character.isLetterOrDigit(evt.getKeyChar())){
                    
                    String charInserted = Character.toString(evt.getKeyChar());
                    
                    if (this.inputField.getText().isEmpty()) {
                        this.inputField.setText(charInserted);
                    }
                }
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JTextField inputField;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JPanel outputAreaBackgroundPanel;
    private javax.swing.JScrollPane outputAreaScroll;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
