/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import exceptions.WrongQueryFormulationException;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.parser.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author giuse
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Parte di acquisizione degli assiomi da DB
        Atom testedPositive = new Atom("tested_positive", true);
        Atom fever = new Atom("fever", true);
        Atom cough = new Atom("cough", true);
        Atom asthenia = new Atom("asthenia", true);
        Atom tasteOrSmellLoss = new Atom("taste_or_smell_loss", true);
        Atom chestPain = new Atom("chest_pain", true);
        Atom breathingDifficulty = new Atom("breathing_difficulty", true);
        Atom noSymptoms = new Atom("no_symptoms", false);
        Atom commonSymptoms = new Atom("common_symptoms", false);
        Atom seriousSymptoms = new Atom("serious_symptoms", false);
        Atom alreadyHadCovid = new Atom("already_had_covid", true); 
        Atom vaccinated = new Atom("vaccinated", true);
        Atom hangedOutNoProtection = new Atom("hanged_out_no_protection", true);
        Atom contactWithPositive = new Atom("contact_with_positive", true);
        Atom isProtected = new Atom("is_protected", false);
        Atom isAtRisk = new Atom("is_at_risk", false);
        Atom well = new Atom("well", false);
        Atom flu = new Atom("flu", false);
        Atom covid = new Atom("covid", false);
        Atom covidMild = new Atom("covid_mild", false);
        Atom covidSerious = new Atom("covid_serious", false);
        
        Set<Atom> atoms = new HashSet<>();
        
        atoms.add(testedPositive);
        atoms.add(fever);
        atoms.add(cough);
        atoms.add(asthenia);
        atoms.add(tasteOrSmellLoss);
        atoms.add(chestPain);
        atoms.add(breathingDifficulty);
        atoms.add(noSymptoms);
        atoms.add(commonSymptoms);
        atoms.add(seriousSymptoms);
        atoms.add(alreadyHadCovid);
        atoms.add(vaccinated);
        atoms.add(hangedOutNoProtection);
        atoms.add(contactWithPositive);
        atoms.add(isProtected);
        atoms.add(isAtRisk);
        atoms.add(well);
        atoms.add(flu);
        atoms.add(covid);
        atoms.add(covidMild);
        atoms.add(covidSerious);
        
        PropositionalDefiniteClause pdc1 = new PropositionalDefiniteClause(commonSymptoms, fever);
        PropositionalDefiniteClause pdc2 = new PropositionalDefiniteClause(commonSymptoms, cough);
        PropositionalDefiniteClause pdc3 = new PropositionalDefiniteClause(commonSymptoms, asthenia);
        PropositionalDefiniteClause pdc4 = new PropositionalDefiniteClause(commonSymptoms, tasteOrSmellLoss);
        PropositionalDefiniteClause pdc5 = new PropositionalDefiniteClause(seriousSymptoms, chestPain);
        PropositionalDefiniteClause pdc6 = new PropositionalDefiniteClause(seriousSymptoms, breathingDifficulty);
        PropositionalDefiniteClause pdc7 = new PropositionalDefiniteClause(isProtected, alreadyHadCovid);
        PropositionalDefiniteClause pdc8 = new PropositionalDefiniteClause(isProtected, vaccinated);
        PropositionalDefiniteClause pdc9 = new PropositionalDefiniteClause(isAtRisk, hangedOutNoProtection);
        PropositionalDefiniteClause pdc10 = new PropositionalDefiniteClause(isAtRisk, contactWithPositive);
        PropositionalDefiniteClause pdc11 = new PropositionalDefiniteClause(well, noSymptoms, isProtected);
        PropositionalDefiniteClause pdc12 = new PropositionalDefiniteClause(flu, commonSymptoms, isProtected);
        PropositionalDefiniteClause pdc13 = new PropositionalDefiniteClause(covidMild, commonSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc14 = new PropositionalDefiniteClause(covidSerious, seriousSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc15 = new PropositionalDefiniteClause(covid, covidMild);
        PropositionalDefiniteClause pdc16 = new PropositionalDefiniteClause(covid, covidSerious);
        PropositionalDefiniteClause pdc17 = new PropositionalDefiniteClause(covid, testedPositive);
        
        List<PropositionalDefiniteClause> covidKb = new ArrayList<>();
        
        covidKb.add(pdc1);
        covidKb.add(pdc2);
        covidKb.add(pdc3);
        covidKb.add(pdc4);
        covidKb.add(pdc5);
        covidKb.add(pdc6);
        covidKb.add(pdc7);
        covidKb.add(pdc8);
        covidKb.add(pdc9);
        covidKb.add(pdc10);
        covidKb.add(pdc11);
        covidKb.add(pdc12);
        covidKb.add(pdc13);
        covidKb.add(pdc14);
        covidKb.add(pdc15);
        covidKb.add(pdc16);
        covidKb.add(pdc17);
        
        for(PropositionalDefiniteClause axiom : covidKb){
            StringBuilder currentAxiomBuilder = new StringBuilder();
            
            currentAxiomBuilder.append(axiom.getHead().getName()).append(" <-");
            
            for(Atom bodyAtom : axiom.getBody()){
                currentAxiomBuilder.append(" ").append(bodyAtom.getName());
            }
            
            currentAxiomBuilder.append(System.lineSeparator());
            
            System.out.println(currentAxiomBuilder.toString());
        }
        
        //Creazione di un TopDownResolver sugli assiomi caricati
        TopDownResolver tdr = new TopDownResolver(atoms, covidKb);
        
        Parser parser = new Parser();
        
        while (true) {
            Scanner scanner = new Scanner(System.in);
            
            List<String> askedAtoms;
            
            try {
                askedAtoms = parser.decodeCommand(scanner.nextLine());
            } catch (WrongQueryFormulationException exc) {
                System.err.println(exc.getMessage());
                continue;
            }
            
            try {
                tdr.proveQuery(askedAtoms);
            } catch (WrongQueryFormulationException exc) {
                System.err.println(exc.getMessage());
                continue;
            }
        }

        
        //Finchè l'utente vuole uscire può somministrare query al risolutore
    }
    
}
