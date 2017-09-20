/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.ArrayList;

/**
 *
 * @author apand
 */
public class Node {
    
    private String color;
    private String program;
    private ArrayList<ArrayList<Node>> children; 
    
    //constructor
    public Node(){
        
    }
    
    //getters + setters
    public String getColor(){
        return this.color;
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public String getProgram(){
        return this.program;
    }
    
    public void setProgram(String program){
        this.program = program;
    }
    
    public ArrayList<ArrayList<Node>> getChildren(){
        return this.children;
    }
    
    public void setChildren(ArrayList<ArrayList<Node>> nodes){
        this.children = nodes;
    }
    
}
