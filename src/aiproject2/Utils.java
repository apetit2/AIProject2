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
    
    public static String ourColor;
    public static String theirColor;
    
    
    /**
     * Creates an initial board where neither player has made a move
     * @return a gomoku board where neither player has made a move 
     */
    public static Node[][] initBoard(){
        
        Node[][] board = new Node[15][15];
        
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
    public static void updateBoard(Node[][] board, int x, int y, String program, boolean isFirst){
        if(isFirst == false){ //we are black - they are white
            board[x][y].setColor("white");
            board[x][y].setProgram(program);
            board[x][y].setIsOurNode(false);
            Utils.ourColor = "black";
            Utils.theirColor = "white";
                           
        } else { //we are white - they are black
            board[x][y].setColor("black");
            board[x][y].setProgram(program);
            board[x][y].setIsOurNode(false);
            Utils.ourColor = "white";
            Utils.theirColor = "black";
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
    
    // NOT DONE
    public static void findBestMove(Node[][] board){       //returns the best move according to minimax
        int bestX;                                                       //initialize the best move accumulator
        int bestY;
        int bestMove = 0;
        for (int x = 0; x < 15; x++) {                                            //scan all X
                for(int y = 0; y < 15; y++) {                                     //scan all y
                        if(true) {                       //compare the value of that nodes value to the best value so far
                                bestX = x;
                                bestY = y;
                        }
                }
        } 
    }
    
  //determines the best possible next move given a board
    public static Node minimax(Node[][] board, int depth, boolean isMax, double alpha, double beta){
    	Node bestNode = new Node();
        ArrayList<Node> moves = getMoves(board);
    	
    	if (moves.isEmpty() || depth == 0) {//if we're done
    		bestNode.setMiniMaxVal(evalBoard(board));
    	}
    	
    	else {
    		for(Node move : moves) {
    			if(isMax) { //maximizing player
    				Node[][] tempBoard = board;
    				tempBoard[move.getX()][move.getY()] = move;
    				
    				double score = minimax(tempBoard,depth-1,false,alpha,beta).getMiniMaxVal();
    				
    				if(score > alpha) {
    					alpha = score;
    					bestNode.setX(move.getX());
    					bestNode.setY(move.getY());
    					bestNode.setMiniMaxVal(alpha);
    				}
    			}
    			else { //minimizing player
    				Node[][] tempBoard = board;
    				tempBoard[move.getX()][move.getY()] = move;
    				
    				double score = minimax(tempBoard,depth-1,true,alpha,beta).getMiniMaxVal();//recursive call
    				
    				if(score < beta) {
    					beta = score;
    					bestNode.setX(move.getX());
    					bestNode.setY(move.getY());
    					bestNode.setMiniMaxVal(beta);
    				}
    			}
                if (alpha >= beta) break;//the actual pruning
    		}
    	}

    	return bestNode;
    }

    // returns an ArrayList<Node> of all possible moves, with no order
    // note that it only returns moves near other already filled spots on the board (but the tile filling that spot can be either color).
    // these aren't the only moves, but this makes the list of possible moves significantly smaller, and encompasses most (but not all) useful moves.
    public static ArrayList<Node> getMoves(Node[][] board){
    	ArrayList<Node> moves = new ArrayList<>();
    	
    	for (int x = 0; x < 15; x++) {                                            //scan all X
            for(int y = 0; y < 15; y++) {                                     //scan all y
            	//if the spot on the board is empty, but adjacent to a filled spot (ours or an opponents)
            	if (board[x][y] == null && (board[x+1][y] != null || board[x+1][y+1] != null || board[x][y+1] != null || board[x-1][y+1] != null || board[x-1][y] != null || board[x-1][y-1] != null || board[x][y-1] != null)) { 
            		Node newMove = new Node();
            		newMove.setX(x);
            		newMove.setY(y);
            		
            		moves.add(newMove);
            	}
            }
    	}
    	
    	return moves;
    }
    
    //What we need is a new HValue function that evaluates the ENTIRE board and returns a score
    public static int evalBoard(Node[][] board){
    	return 0;
        
    }
    // returns the Hvalue of a node based on the nodes around it.
    // the Hvalue is the largest combo that would be possible if Maximizer/Minimizer
    // were to choose that node next
    // This function is based solely on the length of the line that would result.  It currently
    // does not consider:
    // -whether both ends of that line will be open
    // -the maximum posssible length of that line (without being blocked)
    // -if defensive moves are more valuable
    public static int Hvalue(Node[][] board, int x, int y, boolean isMax){
        int accumulator = 0;      // stores the largest possible combo,
        if (board[x][y].getProgram() == null) { //if this node is empty
            int HorizontalValueHolder = HorizontalValue(board, x, y, isMax);     // use holder so code doesnt need to make multiple passes through same function
            int VerticalValueHolder = VerticalValue(board, x, y, isMax);
            int FdiagonalValueHolder = FdiagonalValue(board, x, y, isMax);
            int RdiagonalValueHolder = RdiagonalValue(board, x, y, isMax);
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
        if (isMax) {
        	return accumulator;       //returns the largest possible combo
        }
        else {
        	return accumulator * -1;
        }
    }


    // Function returns the Hvalue of the node based on nodes in a horizontal line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X+-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public static int HorizontalValue(Node[][] board, int x, int y, boolean isOurTurn){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board[x - mod][y].getIsOurNode() == isOurTurn) && (mod < x)){  //check nodes to the right
            accumulator++;
            mod ++;
        }
      
        mod = 1;
        while((board[x + mod][y].getIsOurNode() == isOurTurn) && ((mod + x) < 15)){  //check nodes to the left
            accumulator++;
            mod ++;
        }
   
        return accumulator;
    }

    // Function returns the Hvalue of the node based on nodes in a vertical line
    // this function assumes that the node at X,Y is empty and looks at the nodes in Y+-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public static int VerticalValue(Node[][] board, int x, int y, boolean isOurTurn){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board[x][y - mod].getIsOurNode() == isOurTurn) && (mod < y)){  //check nodes below
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board[x][y + mod].getIsOurNode() == isOurTurn) && ((mod + y) < 15)){  //check nodes above
            accumulator++;
            mod++;
        }
        return accumulator;
    }
    // Function returns the Hvalue of the node based on nodes in a / diagonal line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X-1 Y-1 , X+1 Y+1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public static int FdiagonalValue(Node[][] board, int x, int y, boolean isOurTurn){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board[x - mod][y - mod].getIsOurNode() == isOurTurn) && (mod < y) && (mod < x)){  //check nodes to the lower right
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board[x + mod][y + mod].getIsOurNode() == isOurTurn) && ((mod + x) < 15) && ((mod + y) < 15)){  //check nodes to the upper left
            accumulator++;
            mod ++;
        }
        return accumulator;
    }
    // Function returns the Hvalue of the node based on nodes in a \ line
    // this function assumes that the node at X,Y is empty and looks at the nodes in X-1 Y+1 , X+1 Y-1 to see if they are occupied by
    // the passed player. If they are, it looks at the nodes beyond those, in the same line
    //PROBABLY NEEDS DEBUGGING BUT ASSUME IT WORKS
    public static int RdiagonalValue(Node[][] board, int x, int y, boolean isOurTurn){
        int accumulator = 0;
        int mod = 1;
        // These will need to be fixed but basically this
        while ((board[x - mod][y - mod].getIsOurNode() == isOurTurn) && (mod < y) && (mod < x)){  //check nodes to the upper right
            accumulator++;
            mod ++;
        }
        mod = 1;
        while((board[x - mod][y - mod].getIsOurNode() == isOurTurn) && (mod < y) && (mod < x)){  //check nodes to the lower left
            accumulator++;
            mod ++;
        }
        return accumulator;
    }
    
}