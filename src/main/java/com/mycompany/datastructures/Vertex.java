/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.datastructures;

import java.util.Objects;

/**
 *
 * @author giuse
 * @param <T>
 */
public class Vertex<T> {
    
    private static Integer vertexCount;
    private Integer label;
    private T content;

    public Vertex(T content) {
        this.label = ++Vertex.vertexCount;
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.label);
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
        final Vertex<?> other = (Vertex<?>) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        return true;
    }
}
