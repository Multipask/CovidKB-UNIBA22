/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import com.mycompany.exceptions.WrongQueryFormulationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuse
 */
public class Parser {
    
    public final static String CONJUNCTION_STRING = "and";
    public final static String ASKING_QUERY_FORMAT = "ask[\\s]+[\\w]+([\\s]+" + Parser.CONJUNCTION_STRING + "[\\s]+[\\w]+)*";
    public final static String HELP_QUERY_FORMAT = "help";
    
    public Command decodeQuery(String query) throws WrongQueryFormulationException{
        
        Command command;
        
        query = query.toLowerCase();
        query = query.trim();
        
        if (query.matches(Parser.ASKING_QUERY_FORMAT)) {
            List<String> askedAtoms = new ArrayList<>();
            
            query = query.replace("ask", "");
            query = query.trim();
            String[] tokens = query.split("[\\s]+");

            for (int i = 0; i < tokens.length; i += 2) {
                askedAtoms.add(tokens[i]);
            }
            
            command = new AskingCommand(askedAtoms);
        } else if (query.matches(Parser.HELP_QUERY_FORMAT)){
            command = new HelpCommand();
        } else {
            throw new WrongQueryFormulationException();
        }
        
        return command;
    }
}
