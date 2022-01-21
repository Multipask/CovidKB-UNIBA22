/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author giuse
 */
public class BackgroundLabel extends JLabel{

    public BackgroundLabel(String imagePath, int width, int height) {
        super();
        super.setSize(width, height);
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        Image img = backgroundImage.getImage()
                .getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(img);
        super.setIcon(backgroundImage);
    }
}
