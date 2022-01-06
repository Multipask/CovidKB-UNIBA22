/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

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
}
