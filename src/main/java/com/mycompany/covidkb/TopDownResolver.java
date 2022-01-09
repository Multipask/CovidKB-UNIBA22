/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import com.mycompany.datastructures.*;
import exceptions.WrongQueryFormulationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author giuse
 */
public class TopDownResolver {

    private Set<Atom> atoms;
    private List<PropositionalDefiniteClause> kbAxioms;

    public TopDownResolver(Set<Atom> atoms, List<PropositionalDefiniteClause> kbAxioms) {
        this.atoms = atoms;
        this.kbAxioms = kbAxioms;
    }

    public void proveQuery(List<String> askedAtoms) throws WrongQueryFormulationException {

        List atomsToProve = new ArrayList<>();
        Boolean inexistingAtoms = false;

        for (String atomName : askedAtoms) {

            Boolean matches = false;

            for (Atom atom : atoms) {
                if (atom.getName().equals(atomName)) {
                    atomsToProve.add(atom);
                    matches = true;
                    break;
                }
            }

            if (matches == false) {
                inexistingAtoms = true;
                break;
            }
        }

        if (inexistingAtoms == true) {
            throw new WrongQueryFormulationException();
        }

        Atom yesAtom = new Atom("yes", false);
        PropositionalDefiniteClause answerClause = new PropositionalDefiniteClause(yesAtom, atomsToProve);

        Graph<Vertex<List<Atom>>, Boolean> searchingGraph = this.getSearchingGraph(answerClause);

        this.exploreGraph(searchingGraph, searchingGraph.getRoot(), answerClause);

        if (answerClause.isFact()) {
            System.out.println("Dimostrazione riuscita!");
        } else {
            System.out.println("Dimostrazione fallita...");
        }
    }

    private Graph<Vertex<List<Atom>>, Boolean> getSearchingGraph(PropositionalDefiniteClause answerClause) {
        Graph<Vertex<List<Atom>>, Boolean> searchingGraph = new Graph();

        List<Atom> rootList = new ArrayList<>();
        rootList.addAll(answerClause.getBody());
        Vertex<List<Atom>> root = new Vertex(rootList);

        buildGraph(searchingGraph, root);

        return searchingGraph;
    }

    private void buildGraph(Graph<Vertex<List<Atom>>, Boolean> graph, Vertex<List<Atom>> root) {
        
        graph.addVertex(root);

        List<Atom> rootList = root.getContent();

        Atom currentAtom = rootList.get(0);
        
        List<PropositionalDefiniteClause> neededClauses = new ArrayList<>();

        for (PropositionalDefiniteClause axiom : kbAxioms) {
            if (axiom.getHead().equals(currentAtom)) {
                neededClauses.add(axiom);
            }
        }

        if (currentAtom.isAskable()) {
            List<Atom> successorList = new ArrayList<>();
            successorList.addAll(rootList);
            successorList.remove(0);

            Vertex<List<Atom>> successor = new Vertex<>(successorList);
            graph.addEdge(root, successor, Boolean.TRUE);

            if (!successorList.isEmpty()) {
                buildGraph(graph, successor);
            }
        } else {
            if (neededClauses.isEmpty()) {
                return;
            }
        }

        for (PropositionalDefiniteClause neededClause : neededClauses) {
            List<Atom> successorList = new ArrayList<>();
            successorList.addAll(neededClause.getBody());

            List<Atom> tempList = new ArrayList<>();
            tempList.addAll(rootList);
            tempList.remove(0);

            successorList.addAll(tempList);

            Vertex<List<Atom>> successor = new Vertex(successorList);
            graph.addEdge(root, successor, Boolean.FALSE);

            if (!successorList.isEmpty()) {
                buildGraph(graph, successor);
            }
        }
    }

    private void exploreGraph(Graph<Vertex<List<Atom>>, Boolean> searchingGraph, Vertex<List<Atom>> root, PropositionalDefiniteClause answerClause) {

        Queue<Vertex<List<Atom>>> vertexQueue = new LinkedList<>();

        vertexQueue.offer(root);
        
        while (!vertexQueue.isEmpty()) {
            
            Vertex<List<Atom>> currentVertex = vertexQueue.poll();
            
            String vertexContent = "";

            for (Atom atom : currentVertex.getContent()) {
                vertexContent = vertexContent + atom.getName() + " ";
            }

            //System.out.println("Visiting node: " + vertexContent);

            //System.out.println("Successors:");
            
            for (Vertex<List<Atom>> successor : searchingGraph.getSuccessors(currentVertex)) {

                Boolean askable = searchingGraph.getEdgeWeight(currentVertex, successor);
                
                if(askable){
                    System.out.println(currentVertex.getContent().get(0).getName() + "?");
                    Scanner scanner = new Scanner(System.in);
                    
                    String input = scanner.nextLine();
                    
                    if(input.equalsIgnoreCase("yes")){
                        
                    } else {
                        continue;
                    }
                }
                
                String successorContent = "";

                if (successor.getContent().isEmpty()) {
                    successorContent = "Empty List";
                    
                    answerClause.setBody(new ArrayList<>());
                    
                    return;
                } else {
                    for (Atom atom : successor.getContent()) {
                        successorContent = successorContent + atom.getName() + " ";
                    }
                }

                //System.out.println(successorContent);
                
                vertexQueue.offer(successor);
            }
        }
    }
}
