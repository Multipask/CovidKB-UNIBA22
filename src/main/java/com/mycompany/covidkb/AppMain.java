/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.covidkb;

import com.mycompany.database.dbMain;

/**
 *
 * @author forst
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(String s:args){
            if(s.equalsIgnoreCase("-buildKB")){
                dbMain.buildDB();
            }
        }
        MainFrame.main(null);
    }
    
}
