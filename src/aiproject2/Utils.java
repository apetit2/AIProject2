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
    
    //Have no idea what this is supposed to do
    // NOT DONE
    public static void findBestMove(ArrayList<ArrayList<Node> > board){       //returns the best move according to minimax
        int bestX;                                                       //initialize the best move accumulator
        int bestY;
        int bestMove = 0;
        for (int x = 0; x < 15; x++) {                                            //scan all X
                for(int y = 0; y < 15; y++) {                                     //scan all y
                        if(minimax(x,y) > bestMove) {                       //compare the value of that nodes value to the best value so far
                                bestX = x;
                                bestY = y;
                        }
                }
        } 
    }

    //not done
    public static int minimax(int col, int row){
        return 0;
    }


    // returns the Hvalue of a node based on the nodes around it.
    // the Hvalue is the largest combo that would be possible if Maximizer/Minimizer
    // to choose that node next
    // UPDATE THIS FUNCTION TO PROPERLY DO MAXIMIZER MINIZER STUFF
    // UPDATE THIS FUNCTION TO BE MORE EFFICIENT
    public int Hvalue(ArrayList<ArrayList<Node>> board, int col, int row){
        int accumulator = 0;      // stores the largest possible combo,
        if (board.get(col).get(row).getProgram() == null) { //if this node is empty
            int HorizontalValueHolder = HorizontalValue(board, col, row);     // use holder so code doesnt need to make multiple passes through same function
            int VerticalValueHolder = VerticalValue(board, col, row);
            int FdiagonalValueHolder = FdiagonalValue(board, col, row);
            int RdiagonalValueHolder = RdiagonalValue(board, col, row);
            //Pretty sure there a function that can pick the highest value out of this set of 4
            if (HorizontalValueHolder  > accumulator){
                accumulator = HorizontalValueHolder;  
            }
            if (VerticalValueHolder   > accumulator){
                accumulator = VerticalValueHolder;  
            }
            if (FdiagonalValueHolder   > accumulator){
                accumulator = FdiagonalValueHolder;  
            }
            if (RdiagonalValueHolder   > accumulator){
                accumulator = RdiagonalValueHolder;  
            }
        }
        return accumulator;       //returns the largest possible combo
    }


    // Function returns the Hvalue of the node based on nodes in a horizontal line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X+-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public int HorizontalValue(ArrayList<ArrayList<Node> > board, int col, int row){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board.get(col - mod).get(row).getIsOurNode() == true) && (mod < col)){  //check nodes to the right
            accumulator++;
            mod ++;
        }
      
        mod = 1;
        while((board.get(col + mod).get(row).getIsOurNode() == true) && ((mod + col) < 15)){  //check nodes to the left
            accumulator++;
            mod ++;
        }
   
        return accumulator;
    }

    // Function returns the Hvalue of the node based on nodes in a vertical line
    // this function assumes that the node at X,Y is empty and looks at the nodes in Y+-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public int VerticalValue(ArrayList<ArrayList<Node> > board, int col, int row){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board.get(col).get(row - mod).getIsOurNode() == true) && (mod < row)){  //check nodes below
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board.get(col).get(row + mod).getIsOurNode() == true) && ((mod + row) < 15)){  //check nodes above
            accumulator++;
            mod++;
        }
        return accumulator;
    }
    // Function returns the Hvalue of the node based on nodes in a / diagonal line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X-1 Y-1 , X+1 Y+1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public int FdiagonalValue(ArrayList<ArrayList<Node> > board, int col, int row){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board.get(col - mod).get(row - mod).getIsOurNode() == true) && (mod < row) && (mod < col)){  //check nodes to the lower right
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board.get(col + mod).get(row + mod).getIsOurNode() == true) && ((mod + col) < 15) && ((mod + row) < 15)){  //check nodes to the upper left
            accumulator++;
            mod ++;
        }
        return accumulator;
    }
    // Function returns the Hvalue of the node based on nodes in a \ line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X-1 Y+1 , X+1 Y-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public int RdiagonalValue(ArrayList<ArrayList<Node> > board, int col, int row){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board.get(col - mod).get(row - mod).getIsOurNode() == true) && (mod < row) && (mod < col)){  //check nodes to the upper right
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board.get(col - mod).get(row - mod).getIsOurNode() == true) && (mod < row) && (mod < col)){  //check nodes to the lower left
            accumulator++;
            mod ++;
        }
        return accumulator;
    }
    
}
