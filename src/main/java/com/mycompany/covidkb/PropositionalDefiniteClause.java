/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author giuse
 */
public class PropositionalDefiniteClause {

    private Atom head;
    private List<Atom> body;

    public PropositionalDefiniteClause(Atom head) {
        this.head = head;
        this.body = new ArrayList<>();
    }

    public PropositionalDefiniteClause(Atom head, List<Atom> bodyAtoms) {
        this.head = head;
        this.body = new ArrayList<>();
        this.body.addAll(bodyAtoms);
    }
    
    public PropositionalDefiniteClause(Atom head, Atom... bodyAtoms) {
        this.head = head;
        this.body = new ArrayList<>();
        this.body.addAll(Arrays.asList(bodyAtoms));
    }
    
    public Atom getHead() {
        return head;
    }

    public List<Atom> getBody() {
        return body;
    }

    public void setBody(List<Atom> body) {
        this.body = new ArrayList<>();
        this.body.addAll(body);
    }
    
    public boolean isFact() {
        return (this.body.isEmpty());
    }
}
