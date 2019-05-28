/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.up.fc.dcc.taa;

/**
 *
 * @author Ayy lmao
 */
public class KPPair implements Comparable<KPPair> {
    
    int key;
    int priority;

    public KPPair(int key, int priority) {
        this.key = key;
        this.priority = priority;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.key;
        hash = 29 * hash + this.priority;
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
        final KPPair other = (KPPair) obj;
        if (this.key != other.key) {
            return false;
        }
        if (this.priority != other.priority) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public int compareTo(KPPair o) {
        return Integer.valueOf(key).compareTo(o.getKey());
    }
    
    public int comparePriority(KPPair o) {
        return Integer.valueOf(priority).compareTo(o.getPriority());
    }
    
}
