/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuse
 */
public class AskingCommand extends Command{
    
    private List<String> askedAtoms;

    public AskingCommand(List<String> askedAtoms) {
        this.askedAtoms = new ArrayList<>();
        this.askedAtoms.addAll(askedAtoms);
    }

    public List<String> getAskedAtoms() {
        return askedAtoms;
    }
}
