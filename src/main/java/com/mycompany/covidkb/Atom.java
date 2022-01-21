/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import java.util.Objects;

/**
 *
 * @author giuse
 */
public class Atom {
    
    private String name;
    private boolean askable;
    private boolean alreadyAsked;
    private Boolean providedAnswer;

    public Atom(String name, boolean askable) {
        this.name = name;
        this.askable = askable;
        this.alreadyAsked = false;
        this.providedAnswer = null;
    }

    public String getName() {
        return name;
    }
    
    public boolean isAskable() {
        return askable;
    }

    public boolean isAlreadyAsked() {
        return alreadyAsked;
    }

    public boolean getAnswerProvided() {
        return providedAnswer;
    }
    
    public void provideAnswer(Boolean providedAnswer){
        this.alreadyAsked = true;
        this.providedAnswer = providedAnswer;
    }
    
    public void resetAtom(){
        this.alreadyAsked = false;
        this.providedAnswer = null;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final Atom other = (Atom) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (this.name);
    }
}
