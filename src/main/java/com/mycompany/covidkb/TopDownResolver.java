/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.covidkb;

import com.mycompany.datastructures.*;
import com.mycompany.gui.AtomAskingWindow;
import com.mycompany.exceptions.WrongQueryFormulationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author giuse
 */
public class TopDownResolver {

    private MainFrame mainFrame;
    private Set<Atom> kbAtoms;
    private List<PropositionalDefiniteClause> kbAxioms;

    public TopDownResolver(MainFrame mainFrame, Set<Atom> kbAtoms, List<PropositionalDefiniteClause> kbAxioms) {
        this.mainFrame = mainFrame;
        this.kbAtoms = kbAtoms;
        this.kbAxioms = kbAxioms;
    }

    public void proveQuery(List<String> askedAtoms) throws WrongQueryFormulationException {
        if(askedAtoms.isEmpty()){
            throw new WrongQueryFormulationException();
        }
        
        for(Atom kbAtom: kbAtoms){
            kbAtom.resetAtom();
        }
        
        List atomsToProve = new ArrayList<>();

        for (String atomName : askedAtoms) {
            Boolean existsInKb = false;

            for (Atom atom : this.kbAtoms) {
                if (atom.getName().equals(atomName)) {
                    atomsToProve.add(atom);
                    existsInKb = true;
                    break;
                }
            }

            if (existsInKb == false) {
                throw new WrongQueryFormulationException();
            }
        }

        Atom yesAtom = new Atom("yes", false);
        PropositionalDefiniteClause answerClause = new PropositionalDefiniteClause(yesAtom, atomsToProve);

        Graph<Vertex<List<Atom>>, Boolean> searchingGraph = this.getSearchingGraph(answerClause);

        this.exploreGraph(searchingGraph, searchingGraph.getRoot(), answerClause);

        if (answerClause.isFact()) {
            mainFrame.appendOutput("Dimostrazione riuscita!");
        } else {
            mainFrame.appendOutput("Dimostrazione fallita...");
        }
        
        for(Atom kbAtom: kbAtoms){
            kbAtom.resetAtom();
        }
    }

    private Graph<Vertex<List<Atom>>, Boolean> getSearchingGraph(PropositionalDefiniteClause answerClause) {
        Graph<Vertex<List<Atom>>, Boolean> searchingGraph = new Graph();

        List<Atom> rootList = new ArrayList<>();
        rootList.addAll(answerClause.getBody());
        Vertex<List<Atom>> root = new Vertex(rootList);

        searchingGraph.addVertex(root);
        buildGraph(searchingGraph, root);

        return searchingGraph;
    }

    private void buildGraph(Graph<Vertex<List<Atom>>, Boolean> graph, Vertex<List<Atom>> root) {
        List<Atom> rootList = root.getContent();

        Atom currentAtom = rootList.get(0);

        List<PropositionalDefiniteClause> neededClauses = new ArrayList<>();

        for (PropositionalDefiniteClause axiom : this.kbAxioms) {
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
            
            List<Atom> newBody = new ArrayList<>();
            newBody.addAll(currentVertex.getContent());
            answerClause.setBody(newBody);
            
            if(answerClause.isFact()){
                return;
            }
            
            //<editor-fold defaultstate="collapsed" desc="Log">
            String vertexContent = "";

            if (currentVertex.getContent().isEmpty()) {
                vertexContent = "Empty";
            }

            for (Atom atom : currentVertex.getContent()) {
                vertexContent = vertexContent + atom.getName() + " ";
            }

            mainFrame.appendOutput("Visiting node: " + vertexContent);

            mainFrame.appendOutput("Successors:");

            for (Vertex<List<Atom>> successor : searchingGraph.getSuccessors(currentVertex)) {
                String successorContent = "";

                if(successor.getContent().isEmpty()){
                    successorContent = "Empty";
                }
                
                for (Atom atom : successor.getContent()) {
                    successorContent = successorContent + atom.getName() + " ";
                }
                
                mainFrame.appendOutput("-" + successorContent);
            }
            
            mainFrame.appendOutput("");
            //</editor-fold>
                        
            for (Vertex<List<Atom>> successor : searchingGraph.getSuccessors(currentVertex)) {
                boolean askable = searchingGraph.getEdgeWeight(currentVertex, successor);
                boolean isVisitable = true;
                
                if (askable) {
                    Atom askableAtom = currentVertex.getContent().get(0);

                    if (!askableAtom.isAlreadyAsked()) {
                        String question = currentVertex.getContent().get(0).getName() + "?";
                        AtomAskingWindow askingWindow = new AtomAskingWindow(mainFrame, true, question);
                        askingWindow.setVisible(true);
                        Boolean providedAnswer = askingWindow.getAnswer();

                        askableAtom.provideAnswer(providedAnswer);
                    }
                    
                    boolean positiveAnswer = askableAtom.getAnswerProvided();
                    
                    if (positiveAnswer) {
                        for (Vertex<List<Atom>> askableSuccessor : searchingGraph.getSuccessors(currentVertex)) {
                            if(askableSuccessor.getContent().isEmpty()){
                                answerClause.setBody(new ArrayList<>());
                                return;
                            }
                        }
                    }
                    
                    isVisitable = positiveAnswer;
                }

                if(isVisitable){
                    vertexQueue.offer(successor);
                }
            }
        }
    }
}
