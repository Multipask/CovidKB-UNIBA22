/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

/**
 *
 * @author giuse
 */
public class AtomAskingWindow extends javax.swing.JDialog {
    
    private JLabel backgroundLabel;
    private Boolean answer;
    
    /**
     * Creates new form AtomAskingWindow
     * @param parent
     * @param modal
     */
    public AtomAskingWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        super.setUndecorated(true);
        super.setLocationRelativeTo(parent);
        initComponents();
        customInit();
    }
    
    private void customInit(){        
        // <editor-fold defaultstate="collapsed" desc="Creazione e aggiunta labels di sfondo">
        
        String glassPanelPath = "src" + System.getProperty("file.separator") + 
                                "main" + System.getProperty("file.separator") +
                                "java" + System.getProperty("file.separator") + 
                                "com" + System.getProperty("file.separator") + 
                                "mycompany" + System.getProperty("file.separator") + 
                                "gui" + System.getProperty("file.separator") + "GlassPanel.png";
        
        this.backgroundLabel = new BackgroundLabel(glassPanelPath,
                this.backgroundPanel.getWidth(), this.backgroundPanel.getHeight());
        this.backgroundPanel.add(backgroundLabel, 
                new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, this.backgroundPanel.getWidth(), this.backgroundPanel.getHeight()));
        
        this.backgroundLabel.setOpaque(false);
        this.backgroundLabel.setBackground(new Color(0, 0, 0, 0));
        this.backgroundLabel.revalidate();
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Definizione dei focus">
        this.setFocusable(true);
        this.yesButton.setFocusable(true);
        this.noButton.setFocusable(true);
        this.questionArea.setFocusable(true);
        this.questionAreaScroll.setFocusable(true);
        this.backgroundPanel.setFocusable(false);
        this.backgroundLabel.setFocusable(false);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Impostazione questionArea">
        this.questionArea.setLineWrap(true);
        this.questionArea.setWrapStyleWord(true);
        this.questionArea.setEditable(false);
        this.questionArea.setForeground(Color.white);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Impostazione trasparenza componenti della question area">
        Color empty = new Color(0, 0, 0, 0);
        this.setBackground(empty);
        this.backgroundPanel.setBackground(empty);
        this.backgroundPanel.setOpaque(false);
        this.questionArea.setBackground(empty);
        this.questionArea.setOpaque(false);
        this.questionAreaScroll.getViewport().setBackground(empty);
        this.questionAreaScroll.getViewport().setOpaque(false);
        this.questionAreaScroll.setBackground(empty); //opzionale
        this.questionAreaScroll.setOpaque(false); //opzionale
        this.questionArea.setBorder(null);
        this.questionAreaScroll.setViewportBorder(null);
        this.questionAreaScroll.setBorder(null); //opzionale
        // </editor-fold>    
    }
    
    public void setQuestion(String question){
        this.questionArea.setText(question);
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
        this.dispose();
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
        yesButton = new javax.swing.JButton();
        noButton = new javax.swing.JButton();
        questionAreaScroll = new javax.swing.JScrollPane();
        questionArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yesButton.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        yesButton.setText("Yes");
        yesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesButtonActionPerformed(evt);
            }
        });
        yesButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                yesButtonKeyPressed(evt);
            }
        });
        backgroundPanel.add(yesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 120, 70));

        noButton.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        noButton.setText("No");
        noButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noButtonActionPerformed(evt);
            }
        });
        noButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noButtonKeyPressed(evt);
            }
        });
        backgroundPanel.add(noButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 120, 70));

        questionArea.setColumns(20);
        questionArea.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        questionArea.setRows(5);
        questionAreaScroll.setViewportView(questionArea);

        backgroundPanel.add(questionAreaScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 360, 190));

        getContentPane().add(backgroundPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void yesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesButtonActionPerformed
        this.setAnswer(true);
    }//GEN-LAST:event_yesButtonActionPerformed

    private void noButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noButtonActionPerformed
        this.setAnswer(false);
    }//GEN-LAST:event_noButtonActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT:
                this.yesButton.grabFocus();
                break;
            case KeyEvent.VK_RIGHT:
                this.noButton.grabFocus();
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void yesButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yesButtonKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_ENTER:
                this.setAnswer(true);
                break;
            case KeyEvent.VK_LEFT:
                this.yesButton.grabFocus();
                break;
            case KeyEvent.VK_RIGHT:
                this.noButton.grabFocus();
                break;
        }
    }//GEN-LAST:event_yesButtonKeyPressed

    private void noButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noButtonKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_ENTER:
                this.setAnswer(false);
                break;
            case KeyEvent.VK_LEFT:
                this.yesButton.grabFocus();
                break;
            case KeyEvent.VK_RIGHT:
                this.noButton.grabFocus();
                break;
        }
    }//GEN-LAST:event_noButtonKeyPressed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton noButton;
    private javax.swing.JTextArea questionArea;
    private javax.swing.JScrollPane questionAreaScroll;
    private javax.swing.JButton yesButton;
    // End of variables declaration//GEN-END:variables
}
