/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import java.util.ArrayList;
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

    public void proveQuery(Atom... atomsToProve) {
        Atom yesAtom = new Atom("yes", false);
        PropositionalDefiniteClause answerClause = new PropositionalDefiniteClause(yesAtom, atomsToProve);

        this.proveAnswerClause(answerClause);
        
        if(answerClause.isFact()){
            System.out.println("Dimostrazione riuscita!");
        } else {
            System.out.println("Dimostrazione fallita...");
        }
    }

    public void proveAnswerClause(PropositionalDefiniteClause answerClause) {
        
        Atom currentAtom = answerClause.getBody().get(0);
        
        List<PropositionalDefiniteClause> neededClauses = new ArrayList<>();

        for (PropositionalDefiniteClause axiom : kbAxioms) {
            if (axiom.getHead().equals(currentAtom)) {
                neededClauses.add(axiom);
            }
        }
        
        List<Atom> currentBody = new ArrayList<>();
        currentBody.addAll(answerClause.getBody());
        
        for (PropositionalDefiniteClause currentClause : neededClauses){
            
            if(currentClause.isFact()){
                answerClause.getBody().remove(currentAtom);
                break;
            }
            
            answerClause.getBody().clear();
            answerClause.getBody().addAll(currentBody);
            answerClause.getBody().remove(currentAtom);
            answerClause.getBody().addAll(currentClause.getBody());
        }
    }
}
