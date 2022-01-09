/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import com.mycompany.covidkb.Atom;
import exceptions.WrongQueryFormulationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuse
 */
public class Parser {
    
    public final static String CONJUNCTION_SYMBOL = "and";
    public final static String ASK_COMMAND_FORMAT = "ask[\\s]+[\\w]+([\\s]+" + Parser.CONJUNCTION_SYMBOL + "[\\s]+[\\w]+)*";
    
    public List<String> decodeCommand(String command) throws WrongQueryFormulationException{
        
        List<String> askedAtoms = new ArrayList<>();
        
        command = command.toLowerCase();
        command = command.trim();
        
        if(command.matches(Parser.ASK_COMMAND_FORMAT)){
            command = command.replace("ask", "");
            command = command.trim();
            String[] tokens = command.split("[\\s]+");
            
            for(int i = 0; i < tokens.length; i+=2){
                askedAtoms.add(tokens[i]);
            }
        } else {
            throw new WrongQueryFormulationException();
        }
        
        return askedAtoms;
    }
    
}
