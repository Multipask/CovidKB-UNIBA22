/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.database;

import com.mycompany.covidkb.Atom;
import com.mycompany.covidkb.PropositionalDefiniteClause;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pasquale
 */
public class DatabaseHandler {
    private static DatabaseHandler SINGLE_INSTANCE = null;
    
    private Connection conn;
    
    // Creates Database at C:\Users\[username]\KBdatabases named KBData
    private static final String URL = "jdbc:h2:~/KBdatabases/KBData;IGNORECASE=TRUE";
    
    private static final String CREATE_ATOM_TABLE =
            "CREATE TABLE IF NOT EXISTS atoms (name VARCHAR(32) PRIMARY KEY, askable BOOLEAN, ontology VARCHAR(128))";
    
    private static final String CREATE_PROPOSITIONS_TABLE = 
            "CREATE TABLE IF NOT EXISTS propositions (head VARCHAR(32), prop OTHER, PRIMARY KEY (head,prop), FOREIGN KEY(head) REFERENCES atoms(name) ON DELETE CASCADE)";
    
    private DatabaseHandler(){
        initializeDatabase();
    }   
    
    public static DatabaseHandler getDBHandler() {
        if(SINGLE_INSTANCE == null){
            SINGLE_INSTANCE = new DatabaseHandler();
        }
        return SINGLE_INSTANCE;
    }
    
    private void initializeDatabase(){
        try{
            conn = DriverManager.getConnection(URL);
            Statement stm = conn.createStatement();
            stm.executeUpdate(CREATE_ATOM_TABLE);
            stm.executeUpdate(CREATE_PROPOSITIONS_TABLE);
            stm.close();
            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void ResetDB(){
        try{
            conn = DriverManager.getConnection(URL);
            Statement stm = conn.createStatement();
            stm.executeUpdate("DROP TABLE propositions");
            stm.executeUpdate("DROP TABLE atoms");
            stm.close();
            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        initializeDatabase();
    }
    
    public void uploadAtom(Atom a) throws SQLException {
        conn = DriverManager.getConnection(URL);
        PreparedStatement atomStm = 
                conn.prepareStatement("INSERT INTO atoms(name,askable,ontology) VALUES(?,?,?)");
        atomStm.setString(1, a.getName());
        atomStm.setBoolean(2, a.isAskable());
        atomStm.setString(3, a.getOntology());
        atomStm.executeUpdate();
        atomStm.close();
        conn.close();
    }
    
    public void uploadAtom(String name, boolean askable, String ontology) throws SQLException {
        conn = DriverManager.getConnection(URL);
        PreparedStatement atomStm = 
                conn.prepareStatement("INSERT INTO atoms(name,askable,ontology) VALUES(?,?,?)");
        atomStm.setString(1, name);
        atomStm.setBoolean(2, askable);
        atomStm.setString(3, ontology);
        atomStm.executeUpdate();
        atomStm.close();
        conn.close();
    }

    public Set<Atom> downloadAllAtoms() throws SQLException {
        conn = DriverManager.getConnection(URL);
        Set<Atom> atomSet = new HashSet<Atom>();
        String STATEMENT = "SELECT * FROM atoms";
        
        Statement query = conn.createStatement();
        ResultSet rs = query.executeQuery(STATEMENT);
        
        while(rs.next()){
            Atom a = new Atom(rs.getString(1), rs.getBoolean(2), rs.getString(3));
            atomSet.add(a);
        }

        rs.close();
        query.close();
        conn.close();
        
        if(atomSet.isEmpty()){
            throw new SQLException("NO ATOMS FOUND IN THE DATABASE");
        }
        
        return atomSet;
    }
    
    public List<Atom> downloadListAllAtoms() throws SQLException {
                conn = DriverManager.getConnection(URL);
        List<Atom> atomSet = new LinkedList<Atom>();
        String STATEMENT = "SELECT * FROM atoms";  
        Statement query = conn.createStatement();
        ResultSet rs = query.executeQuery(STATEMENT);
        
        while(rs.next()){
            Atom a = new Atom(rs.getString(1), rs.getBoolean(2), rs.getString(3));
            atomSet.add(a);
        }

        rs.close();
        query.close();
        conn.close();
        
        if(atomSet.isEmpty()){
            throw new SQLException("NO ATOMS FOUND IN THE DATABASE");
        }
        
        return atomSet;
    }
    
    public Atom downloadAtom(String name) throws SQLException {
        conn = DriverManager.getConnection(URL);
        Atom a = null;
        PreparedStatement atomQuery = conn.prepareStatement("SELECT * FROM atoms WHERE name = ?");
        atomQuery.setString(1, name);
        ResultSet rs = atomQuery.executeQuery();
        
        while(rs.next()){
            a = new Atom(rs.getString(1), rs.getBoolean(2), rs.getString(3));
        }

        rs.close();
        atomQuery.close();
        conn.close();
        
        if(a==null){
            throw new SQLException("ATOM NOT FOUND IN THE DATABASE");
        }
        
        return a;
    }

    public void uploadProposition(PropositionalDefiniteClause prop) throws SQLException {
        conn = DriverManager.getConnection(URL);
        PreparedStatement propStm =
                conn.prepareStatement("INSERT INTO propositions(head,prop) VALUES(?,?)");
        propStm.setString(1, prop.getHead().getName());
        propStm.setObject(2, prop);
        propStm.executeUpdate();
        propStm.close();
        conn.close();
    }
    
    public List<PropositionalDefiniteClause> downloadAllPropositions() throws SQLException {
        conn = DriverManager.getConnection(URL);
        List<PropositionalDefiniteClause> kb = new ArrayList<>();
        String STATEMENT = "SELECT * FROM propositions";
        
        Statement query = conn.createStatement();
        ResultSet rs = query.executeQuery(STATEMENT);
        
        while (rs.next()){
            Object o = rs.getObject(2);
            if(o instanceof PropositionalDefiniteClause){
                kb.add((PropositionalDefiniteClause)o);
            }
        }
        
        rs.close();
        query.close();
        conn.close();
        
        if(kb.isEmpty()){
            throw new SQLException("NO PROPOSITIONS FOUND IN THE DATABASE");
        }
        
        return kb;
    }
    
    public List<PropositionalDefiniteClause> downloadPropositions(Atom a) throws SQLException {
        conn = DriverManager.getConnection(URL);
        List<PropositionalDefiniteClause> propositions = new ArrayList<>();
        PreparedStatement propQuery =
                conn.prepareStatement("SELECT prop FROM propositions WHERE head = ?");
        propQuery.setString(1, a.getName());
        ResultSet rs = propQuery.executeQuery();
        
        while(rs.next()){
            Object o = rs.getObject(1);
            if(o instanceof PropositionalDefiniteClause){
                propositions.add((PropositionalDefiniteClause)o);
            }
        }      
        
        rs.close();
        propQuery.close();
        conn.close();
        
        if(propositions.isEmpty()){
            throw new SQLException("NO PROPOSITIONS FOR THE ATOM " +a.getName() + " FOUND IN THE DATABASE");
        }
        
        return propositions;
    }
    
    public List<PropositionalDefiniteClause> downloadPropositions(String head) throws SQLException {
        conn = DriverManager.getConnection(URL);
        List<PropositionalDefiniteClause> propositions = new ArrayList<>();
        PreparedStatement propQuery =
                conn.prepareStatement("SELECT prop FROM propositions WHERE head = ?");
        propQuery.setString(1, head);
        ResultSet rs = propQuery.executeQuery();
        
        while(rs.next()){
            Object o = rs.getObject(1);
            if(o instanceof PropositionalDefiniteClause){
                propositions.add((PropositionalDefiniteClause)o);
            }
        }
        rs.close();
        propQuery.close();
        conn.close();
        
        if(propositions.isEmpty()){
            throw new SQLException("NO PROPOSITIONS FOR THE ATOM " + head + " FOUND IN THE DATABASE");
        }
        
        return propositions;
    }
}
