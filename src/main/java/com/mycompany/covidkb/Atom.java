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
    private TruthValue truthVal;
    private boolean askable;
    private boolean alreadyAsked;

    public Atom(String name, boolean askable) {
        this.name = name;
        this.askable = askable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TruthValue getTruthVal() {
        return truthVal;
    }

    public void setTruthVal(TruthValue truthVal) {
        this.truthVal = truthVal;
    }

    public boolean isAskable() {
        return askable;
    }

    public void setAskable(boolean askable) {
        this.askable = askable;
    }

    public boolean isAlreadyAsked() {
        return alreadyAsked;
    }

    public void setAlreadyAsked(boolean alreadyAsked) {
        this.alreadyAsked = alreadyAsked;
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
}
