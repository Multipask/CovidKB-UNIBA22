/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author giuse
 * @param <VT>
 * @param <ET>
 */
public class Graph<VT, ET> {
    
    private class WeightedOutLink{
        
        private VT vertex;
        private ET weight;

        public WeightedOutLink(VT vertex, ET weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public VT getVertex() {
            return vertex;
        }

        public void setVertex(VT vertex) {
            this.vertex = vertex;
        }

        public ET getWeight() {
            return weight;
        }

        public void setWeight(ET weight) {
            this.weight = weight;
        }
    }
    
    private Map<VT, List<WeightedOutLink>> adjacencyMap;
    private VT root;

    public Graph() {
        this.adjacencyMap = new HashMap<>();
        this.root = null;
    }

    public VT getRoot() {
        return root;
    }
    
    public void addVertex(VT vertex){
        
        if(this.root == null){
            root = vertex;
        }
        
        if(!this.adjacencyMap.containsKey(vertex)){
            this.adjacencyMap.put(vertex, new ArrayList<>());
        }
    }
    
    public void addEdge(VT source, VT destination, ET weight){
        if(!this.adjacencyMap.containsKey(source)){
            this.addVertex(source);
        }
        
        if(!this.adjacencyMap.containsKey(destination)){
            this.addVertex(destination);
        }
        
        WeightedOutLink outLink = new WeightedOutLink(destination, weight);
        
        this.adjacencyMap.get(source).add(outLink);
    }
    
    public List<VT> getSuccessors(VT vertex){
        
        List<VT> successorsList = new ArrayList<>();
        
        if(this.adjacencyMap.containsKey(vertex)){
            for(WeightedOutLink outLink : this.adjacencyMap.get(vertex)){
                successorsList.add(outLink.getVertex());
            }
        }
        
        return successorsList;
    }
    
    public ET getEdgeWeight(VT source, VT destination){
        
        ET weight = null;
        
        if(this.adjacencyMap.containsKey(source)){
            
            List<WeightedOutLink> outLinks = this.adjacencyMap.get(source);
            
            for(WeightedOutLink outLink : outLinks){
                if(outLink.getVertex().equals(destination)){
                    weight = outLink.getWeight();
                    break;
                }
            }
        }
        
        return weight;
    }
}
