/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.up.fc.dcc.taa;

/**
 *
 * @author Ayy lmao
 * @param <T>
 */
public interface Tree<T extends Comparable<T>> {
    
    public T search(T t);
    
    public void insert(T t);
    
    public boolean remove(T t);
    
    public void printContents();
    
}
