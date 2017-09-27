/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//UI Framework Stuff
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author apand
 */
public class AIproject2 extends Application {
    
    //this method is here in case we want to add a UI otherwise ignore
    @Override
    public void start(Stage primaryStage) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //this is what we would call if we want to have a UI
        //launch(args);
        
        //are we first
        boolean first = false;
        
        //end_game file, go file, and move file names
        String goTime = "AIProject2.go"; //change based upon what we want the program name to be
        String endTime = "end_game";
        String moveName = "move_file";
        
        String goPath = new File(goTime).getAbsolutePath();
        String endPath = new File(endTime).getAbsolutePath();
        String movePath = new File(moveName).getAbsolutePath();
        
        
        //initialize the board
        Node[][] board = Utils.initBoard();
        
        //check if end file is in the directory
        boolean checkEnd = new File(endPath).exists();
        System.out.println("CheckEnd: " + checkEnd);
        
        //while the end file has not been added to the directory
        //keep checking to see if its our turn, and if it is we should
        //make a move
        while(checkEnd == false){
            //check if our go file is in the directory or not
            boolean checkTurn = new File(goPath).exists();
            System.out.println("Checkturn: " + checkTurn);
            
            //if it is, we make a move
            if(checkTurn == true){
                
                //input values for the previously made move
                String[] priorMove;
                int column, row;
                
                //read previously made move (if not first move) 
                try {
                    
                   //needed to read in the file 
                   FileReader fr = new FileReader(moveName);
                   BufferedReader br = new BufferedReader(fr);
                   
                   //read - in move 
                   String sCurrentLine;
                   sCurrentLine = br.readLine();//only should be one line
                   
                   System.out.println(sCurrentLine);
                   
                   if(sCurrentLine == null){
                       //no prior move was made
                       //we are going first - so we are white
                       first = true;
                       
                   } else {
                       //a move was made read it in
                       priorMove = sCurrentLine.split(" ");
                       column = Integer.parseInt(priorMove[1]);
                       row = Integer.parseInt(priorMove[2]);
                       
                       System.out.println(priorMove[1]);
                       System.out.println(priorMove[2]);
                       
                       //update the board to reflect the previous move
                       Utils.updateBoard(board, column, row, priorMove[0], first);
                       
                   }
                  
                } catch (IOException e){
                    System.out.println(e.getMessage());
                    //something went very wrong -- we'll just forfeit if this happens
                    //let the referee timeout - so maybe use wait
                }
                
                //make our move here
                Node n =  Utils.callMiniMax(board);
                System.out.print(n.getX());
                System.out.print(n.getY());
                
                String ourMove = "AIProject2 " + n.getX() + " " + n.getY();

                //write our move out
                Utils.writeMove(moveName, ourMove);
                //move should be over now
            }
            
            //check again to see if the end file is there, and if not we 
            //continue to loop and wait for our next turn
            checkEnd = new File(endPath).exists();
            System.out.println("CheckEnd: " + checkEnd);
            
            //we might need to make a check for the opponent file here and wait until it appears before continuing the loop
        }
    }
    
}

