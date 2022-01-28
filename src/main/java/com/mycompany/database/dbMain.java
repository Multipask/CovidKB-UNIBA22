package com.mycompany.database;

import com.mycompany.covidkb.Atom;
import com.mycompany.covidkb.PropositionalDefiniteClause;
import java.sql.SQLException;

/**
 *
 * @author forst
 */
public class dbMain {

    public static void buildDB(){
        DatabaseHandler db = DatabaseHandler.getDBHandler();
        
        db.ResetDB();
        
        Atom testedPositive = new Atom("tested_positive", true, "The Covid Test for the subject resulted positive");
        Atom fever = new Atom("fever", true, "The subject has fever");
        Atom cough = new Atom("cough", true, "The subject has cough");
        Atom asthenia = new Atom("asthenia", true, "The subject has asthenia");
        Atom tasteOrSmellLoss = new Atom("taste_or_smell_loss", true, "The subject presents a loss of smell or taste");
        Atom chestPain = new Atom("chest_pain", true, "The subject presents chest pain");
        Atom breathingDifficulty = new Atom("breathing_difficulty", true, "The subject has difficulties breathing normally");
        Atom noSymptoms = new Atom("no_symptoms", true,"The subject presents no symptoms");
        Atom commonSymptoms = new Atom("common_symptoms", false, "The subject presents common symptoms of covid");
        Atom seriousSymptoms = new Atom("serious_symptoms", false, "The subject presents severe smyptoms of covid");
        Atom alreadyHadCovid = new Atom("already_had_covid", true, "The subject already had covid and healed"); 
        Atom vaccinated = new Atom("vaccinated", true, "The subject received a full round of vaccination");
        Atom hangedOutNoProtection = new Atom("hanged_out_no_protection", true, "The subject has not used protection when outside");
        Atom contactWithPositive = new Atom("contact_with_positive", true, "The subject had a recent contact with an individual positive to covid");
        Atom isProtected = new Atom("is_protected", false, "The subject is protected against covid");
        Atom isAtRisk = new Atom("is_at_risk", false, "The subject is at risk of contracting covid");
        Atom flu = new Atom("flu", false, "The subject has flu");
        Atom covid = new Atom("covid", false, "The subject has covid");
        Atom covidMild = new Atom("covid_mild", false, "The subject shows mild symptoms of covid");
        Atom covidSerious = new Atom("covid_serious", false, "The subject shows severe symptoms of covid");
        
        System.out.println("Uploading Atoms..."+System.lineSeparator());
        
        try{
            db.uploadAtom(testedPositive);
            db.uploadAtom(flu);
            db.uploadAtom(fever);
            db.uploadAtom(cough);
            db.uploadAtom(asthenia);
            db.uploadAtom(tasteOrSmellLoss);
            db.uploadAtom(chestPain);
            db.uploadAtom(breathingDifficulty);
            db.uploadAtom(noSymptoms);
            db.uploadAtom(commonSymptoms);
            db.uploadAtom(seriousSymptoms);
            db.uploadAtom(alreadyHadCovid);
            db.uploadAtom(vaccinated);
            db.uploadAtom(hangedOutNoProtection);
            db.uploadAtom(contactWithPositive);
            db.uploadAtom(isAtRisk);
            db.uploadAtom(isProtected);
            db.uploadAtom(covid);
            db.uploadAtom(covidMild);
            db.uploadAtom(covidSerious);
            
            System.out.println("Uploaded the following Atoms:"+System.lineSeparator());
            
            for(Atom a : db.downloadAllAtoms()){
                System.out.println(a.toString() + System.lineSeparator());
            }
            
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        System.out.println(System.lineSeparator()+System.lineSeparator());
        
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
        PropositionalDefiniteClause pdc12 = new PropositionalDefiniteClause(flu, commonSymptoms, isProtected);
        PropositionalDefiniteClause pdc13 = new PropositionalDefiniteClause(covidMild, commonSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc14 = new PropositionalDefiniteClause(covidSerious, seriousSymptoms, isAtRisk);
        PropositionalDefiniteClause pdc15 = new PropositionalDefiniteClause(covid, covidMild);
        PropositionalDefiniteClause pdc16 = new PropositionalDefiniteClause(covid, covidSerious);
        PropositionalDefiniteClause pdc17 = new PropositionalDefiniteClause(covid, testedPositive);
        
        System.out.println("Uploading Propositions..."+System.lineSeparator());
        
        try{
            db.uploadProposition(pdc1);
            db.uploadProposition(pdc2);
            db.uploadProposition(pdc3);
            db.uploadProposition(pdc4);
            db.uploadProposition(pdc5);
            db.uploadProposition(pdc6);
            db.uploadProposition(pdc7);
            db.uploadProposition(pdc8);
            db.uploadProposition(pdc9);
            db.uploadProposition(pdc10);
            db.uploadProposition(pdc12);
            db.uploadProposition(pdc13);
            db.uploadProposition(pdc14);
            db.uploadProposition(pdc15);
            db.uploadProposition(pdc16);
            db.uploadProposition(pdc17);          
            
            System.out.println("Uploaded the following Propositions"+System.lineSeparator());
            
            for(PropositionalDefiniteClause p : db.downloadAllPropositions()){
                System.out.println(p.toString() + System.lineSeparator());
            }
            
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        buildDB();
    } 
  
}
