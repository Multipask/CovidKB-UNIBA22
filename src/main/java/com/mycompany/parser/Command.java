/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

/**
 *
 * @author giuse
 */
public abstract class Command {
    public boolean isAskingCommand(){
        return (this instanceof AskingCommand);
    }
    
    public boolean isHelpCommand(){
        return (this instanceof HelpCommand);
    }
}
