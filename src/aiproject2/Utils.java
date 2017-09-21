/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author apand
 */
public class Utils {
    
    /**
     * Creates an initial board where neither player has made a move
     * @return a gomoku board where neither player has made a move 
     */
    public static ArrayList<ArrayList<Node>> initBoard(){
        
        ArrayList<ArrayList<Node>> board = new ArrayList<>();
        
        //initialize the board with empty nodes
        for(int i = 0; i < 15; i++){
            ArrayList<Node> nodes = new ArrayList<>();
            board.add(nodes);
            for(int j = 0; j < 15; j++){
                Node node = new Node();
                board.get(i).add(node);
            }
        }
        
        return board;
    }
    
    /**
     * Updates the gomoku board with the past move of our opponent
     * @param board - current state of the gomoku board
     * @param col - column of the previous move
     * @param row - row of the previous move
     * @param program - program name of the opponent program
     * @param isFirst - true if our program is first otherwise the opponent program is first
     */
    public static void updateBoard(ArrayList<ArrayList<Node>> board, int col, int row, String program, boolean isFirst){
        if(isFirst == false){ //we are black - they are white
            board.get(col).get(row).setColor("white");
            board.get(col).get(row).setProgram(program);
                           
        } else { //we are white - they are black
            board.get(col).get(row).setColor("black");
            board.get(col).get(row).setProgram(program);               
        }
    }
    
    /**
     * Writes out our move to the move_file
     * @param fileName - the name of the file we are writing out to
     * @param ourMove - the string containing our move that we are writing out to
     */
    public static void writeMove(String fileName, String ourMove){
        FileWriter fw = null;
        try{
            fw = new FileWriter(fileName, false);
            fw.write(ourMove);
            fw.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
            //something went very wrong -- we'll just forfeit if this happens
            //let the referee timeout - so maybe use wait
        }
    }
    
}
