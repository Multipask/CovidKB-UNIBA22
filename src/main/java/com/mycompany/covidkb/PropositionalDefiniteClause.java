/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author giuse
 */
public class PropositionalDefiniteClause implements Serializable {

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.head);
        hash = 47 * hash + Objects.hashCode(this.body);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropositionalDefiniteClause other = (PropositionalDefiniteClause) obj;
        if (!Objects.equals(this.head, other.head)) {
            return false;
        }
        return Objects.equals(this.body, other.body);
    } 

    @Override
    public String toString() {
        String finalString = this.head.getName() + " <- ";
        for(int i =0; i<body.size()-1;i++){
           finalString = finalString.concat(body.get(i).getName() + " AND ");
        }
        finalString = finalString.concat(body.get(body.size()-1).getName());
        return finalString;
    }
    
    
}
