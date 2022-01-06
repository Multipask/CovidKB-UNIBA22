/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import java.util.List;

/**
 *
 * @author giuse
 */
public class TopDownResolver {
    
    private List<PropositionalDefiniteClause> kbAxioms;

    public TopDownResolver(List<PropositionalDefiniteClause> kbAxioms) {
        this.kbAxioms = kbAxioms;
    }
    
    public void prove(Atom... atomsToProve){
        Atom yesAtom = new Atom("yes", false);
        PropositionalDefiniteClause answerClause = new PropositionalDefiniteClause(yesAtom, atomsToProve);
    }
}
