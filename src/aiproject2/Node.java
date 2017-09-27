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
   
	private double miniMaxVal;
	private int x;
	private int y;
    private String color;
    private String program;
    private boolean isOurNode;
    private ArrayList<Node> children; 
    
    //constructor
    public Node(){
        
    }
    
    //getters + setters
    public double getMiniMaxVal() {
    	return this.miniMaxVal;
    }
    
    public void setMiniMaxVal(double miniMaxVal) {
    	this.miniMaxVal = miniMaxVal;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
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
    
    public ArrayList<Node> getChildren(){
        return this.children;
    }
    
    public void setChildren(ArrayList<Node> nodes){
        this.children = nodes;
    }
    
    public void setIsOurNode(boolean isOurNode){
        this.isOurNode = isOurNode;
    }
    
    public boolean getIsOurNode(){
        return this.isOurNode;
    }
    
}