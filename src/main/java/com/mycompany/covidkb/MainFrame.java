/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import com.mycompany.gui.BackgroundLabel;
import com.mycompany.parser.Parser;
import exceptions.WrongQueryFormulationException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author giuse
 */
public class MainFrame extends javax.swing.JFrame {
    
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
    
    public final TopDownResolver getResolver(){
        
        //Parte di acquisizione di atomi e assiomi da DB
        
        Atom testedPositive = new Atom("tested_positive", true);
        Atom fever = new Atom("fever", true);
        Atom cough = new Atom("cough", true);
        Atom asthenia = new Atom("asthenia", true);
        Atom tasteOrSmellLoss = new Atom("taste_or_smell_loss", true);
        Atom chestPain = new Atom("chest_pain", true);
        Atom breathingDifficulty = new Atom("breathing_difficulty", true);
        Atom noSymptoms = new Atom("no_symptoms", true);
        Atom commonSymptoms = new Atom("common_symptoms", false);
        Atom seriousSymptoms = new Atom("serious_symptoms", false);
        Atom alreadyHadCovid = new Atom("already_had_covid", true); 
        Atom vaccinated = new Atom("vaccinated", true);
        Atom hangedOutNoProtection = new Atom("hanged_out_no_protection", true);
        Atom contactWithPositive = new Atom("contact_with_positive", true);
        Atom isProtected = new Atom("is_protected", false);
        Atom isAtRisk = new Atom("is_at_risk", false);
        Atom well = new Atom("well", false);
        Atom flu = new Atom("flu", false);
        Atom covid = new Atom("covid", false);
        Atom covidMild = new Atom("covid_mild", false);
        Atom covidSerious = new Atom("covid_serious", false);
        
        Set<Atom> atoms = new HashSet<>();
        
        atoms.add(testedPositive);
        atoms.add(fever);
        atoms.add(cough);
        atoms.add(asthenia);
        atoms.add(tasteOrSmellLoss);
        atoms.add(chestPain);
        atoms.add(breathingDifficulty);
        atoms.add(noSymptoms);
        atoms.add(commonSymptoms);
        atoms.add(seriousSymptoms);
        atoms.add(alreadyHadCovid);
        atoms.add(vaccinated);
        atoms.add(hangedOutNoProtection);
        atoms.add(contactWithPositive);
        atoms.add(isProtected);
        atoms.add(isAtRisk);
        atoms.add(well);
        atoms.add(flu);
        atoms.add(covid);
        atoms.add(covidMild);
        atoms.add(covidSerious);
        
        PropositionalDefiniteClause pdc1 = new PropositionalDefiniteClause(commonSymptoms, fever);
        PropositionalDefiniteClause pdc2 = new PropositionalDefiniteClause(commonSymptoms, cough);
        PropositionalDefiniteClause pdc3 = new PropositionalDefiniteClause(commonSymptoms, asthenia);
        PropositionalDefiniteClause pdc4 = new PropositionalDefiniteClause(commonSymptoms, tasteOrSmellLoss);
        PropositionalDefiniteClause pdc5 = new PropositionalDefiniteClause(seriousSymptoms, chestPain);
        PropositionalDefiniteClause pdc6 = new PropositionalDefiniteClause(seriousSymptoms, breathingDifficulty);
        PropositionalDefiniteClause pdc7 = new PropositionalDefiniteClause(isProtected, alreadyHadCovid);
        PropositionalDefiniteClause pdc8 = new PropositionalDefiniteClause(isProtected, vaccinated);
        PropositionalDefiniteClause pdc9 = new PropositionalDefiniteClause(isAtRisk, hangedOutNoProtection);
        PropositionalDefiniteClause pdc10 = new PropositionalDefiniteClause(isAtRisk, contactWithPositive);
        PropositionalDefiniteClause pdc11 = new PropositionalDefiniteClause(well, noSymptoms, isProtected);
        PropositionalDefiniteClause pdc12 = new PropositionalDefiniteClause(flu, commonSymptoms, isProtected);
        PropositionalDefiniteClause pdc13 = new PropositionalDefiniteClause(covidMild, commonSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc14 = new PropositionalDefiniteClause(covidSerious, seriousSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc15 = new PropositionalDefiniteClause(covid, covidMild);
        PropositionalDefiniteClause pdc16 = new PropositionalDefiniteClause(covid, covidSerious);
        PropositionalDefiniteClause pdc17 = new PropositionalDefiniteClause(covid, testedPositive);
        
        List<PropositionalDefiniteClause> covidKb = new ArrayList<>();
        
        covidKb.add(pdc1);
        covidKb.add(pdc2);
        covidKb.add(pdc3);
        covidKb.add(pdc4);
        covidKb.add(pdc5);
        covidKb.add(pdc6);
        covidKb.add(pdc7);
        covidKb.add(pdc8);
        covidKb.add(pdc9);
        covidKb.add(pdc10);
        covidKb.add(pdc11);
        covidKb.add(pdc12);
        covidKb.add(pdc13);
        covidKb.add(pdc14);
        covidKb.add(pdc15);
        covidKb.add(pdc16);
        covidKb.add(pdc17);
        
        for(PropositionalDefiniteClause axiom : covidKb){
            StringBuilder currentAxiomBuilder = new StringBuilder();
            
            currentAxiomBuilder.append(axiom.getHead().getName()).append(" <-");
            
            for(Atom bodyAtom : axiom.getBody()){
                currentAxiomBuilder.append(" ").append(bodyAtom.getName());
            }
            
            currentAxiomBuilder.append(System.lineSeparator());
            
            System.out.println(currentAxiomBuilder.toString());
        }
        
        //Creazione di un TopDownResolver sugli assiomi caricati
        TopDownResolver tdr = new TopDownResolver(this, atoms, covidKb);
        
        return tdr;
    }

    private void customInit(){
        
        // <editor-fold defaultstate="collapsed" desc="Creazione e aggiunta labels di sfondo">
        this.backgroundLabel = new JLabel();
        this.backgroundPanel.add(backgroundLabel, 
                new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, this.backgroundPanel.getWidth(), this.backgroundPanel.getHeight()));
        
        this.backgroundLabel.setOpaque(false);
        this.backgroundLabel.setBackground(new Color(0, 0, 0, 0));
        this.backgroundLabel.revalidate();
        
        String glassPanelPath = "src" + System.getProperty("file.separator") + 
                                "main" + System.getProperty("file.separator") +
                                "java" + System.getProperty("file.separator") + 
                                "com" + System.getProperty("file.separator") + 
                                "mycompany" + System.getProperty("file.separator") + "gui";
        
        this.outputAreaBackgroundLabel = new BackgroundLabel(glassPanelPath,
                this.outputAreaBackgroundPanel.getWidth(), this.outputAreaBackgroundPanel.getHeight());
        this.outputAreaBackgroundPanel.add(outputAreaBackgroundLabel, 
                new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, this.outputAreaBackgroundPanel.getWidth(), this.outputAreaBackgroundPanel.getHeight()));
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Definizione dei focus">
        this.setFocusable(true);
        this.inputField.setFocusable(true);
        this.outputAreaScroll.setFocusable(true);
        this.backgroundPanel.setFocusable(false);
        this.backgroundLabel.setFocusable(false);
        this.outputAreaBackgroundPanel.setFocusable(false);
        this.outputAreaBackgroundLabel.setFocusable(false);
        this.outputArea.setFocusable(false);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Impostazione outputArea">
        this.outputArea.setLineWrap(true);
        this.outputArea.setWrapStyleWord(true);
        this.outputArea.setEditable(false);
        this.outputArea.setForeground(Color.black);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Impostazione trasparenza componenti dell'area di output">
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
        String command = this.inputField.getText();
        this.inputField.setText("");
        
        Parser parser = new Parser();
        
        List<String> askedAtoms = new ArrayList<>();
        
        try {
            askedAtoms.addAll(parser.decodeCommand(command));
        } catch (WrongQueryFormulationException ex) {
            outputArea.setText(ex.getMessage());
        }
        
        try {
            resolver.proveQuery(askedAtoms);
        } catch (WrongQueryFormulationException ex) {
            outputArea.setText(ex.getMessage());
        }
    }
    
    public void appendOutput(String content){
        this.outputArea.append(System.lineSeparator() + content);
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
        inputField = new javax.swing.JTextField();
        outputAreaBackgroundPanel = new javax.swing.JPanel();
        outputAreaScroll = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setPreferredSize(new java.awt.Dimension(1000, 600));
        setSize(new java.awt.Dimension(1000, 600));

        backgroundPanel.setBackground(new java.awt.Color(153, 255, 255));
        backgroundPanel.setMaximumSize(new java.awt.Dimension(1000, 600));
        backgroundPanel.setMinimumSize(new java.awt.Dimension(1000, 600));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(1000, 600));

        inputField.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        inputField.setMaximumSize(new java.awt.Dimension(800, 30));
        inputField.setMinimumSize(new java.awt.Dimension(800, 30));
        inputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputFieldKeyPressed(evt);
            }
        });

        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        outputArea.setRows(5);
        outputAreaScroll.setViewportView(outputArea);

        javax.swing.GroupLayout outputAreaBackgroundPanelLayout = new javax.swing.GroupLayout(outputAreaBackgroundPanel);
        outputAreaBackgroundPanel.setLayout(outputAreaBackgroundPanelLayout);
        outputAreaBackgroundPanelLayout.setHorizontalGroup(
            outputAreaBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outputAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        outputAreaBackgroundPanelLayout.setVerticalGroup(
            outputAreaBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outputAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(outputAreaBackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(outputAreaBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        getContentPane().add(backgroundPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputFieldKeyPressed
        switch (evt.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_ENTER:
                this.executeCommand();
            default:
                this.outputArea.grabFocus();
                if(Character.isLetterOrDigit(evt.getKeyChar())){
                    
                    String charInserted = Character.toString(evt.getKeyChar());
                    
                    if (this.outputArea.getText().isEmpty()) {
                        this.outputArea.setText(charInserted);
                    }
                }
                break;
        }
    }//GEN-LAST:event_inputFieldKeyPressed

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
    private javax.swing.JTextField inputField;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JPanel outputAreaBackgroundPanel;
    private javax.swing.JScrollPane outputAreaScroll;
    // End of variables declaration//GEN-END:variables
}