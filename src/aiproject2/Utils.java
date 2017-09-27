/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @param x - column of the previous move
     * @param y - row of the previous move
     * @param program - program name of the opponent program
     * @param isFirst - true if our program is first otherwise the opponent program is first
     */
    public static void updateBoard(Node[][] board, int x, int y, String program, boolean isFirst){
        if(isFirst == false){ //we are black - they are white
            Node n = new Node();
            n.setColor("white");
            n.setProgram(program);
            n.setIsOurNode(false);
            board[x][y] = n;
            Utils.ourColor = "black";
            Utils.theirColor = "white";
                           
        } else { //we are white - they are black
            Node n = new Node();
            n.setColor("black");
            n.setProgram(program);
            n.setIsOurNode(false);
            board[x][y] = n;
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
        BufferedWriter fw = null;
        try{
            System.out.println(ourMove);
            fw = new BufferedWriter( new FileWriter(fileName, false));
            fw.write(ourMove);
            fw.close();
            
        } catch (IOException e){
            System.out.println(e.getMessage());
            //something went very wrong -- we'll just forfeit if this happens
            //let the referee timeout - so maybe use wait
        } finally {
            try {
                if(fw != null){
                    fw.close();
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
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
    
    //literally just calls the minimax function
    public static Node callMiniMax(Node[][] board) {
    	int miniMaxDepth = 5;
    	return minimax(board, miniMaxDepth, true, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }
    
  //determines the best possible next move given a board
    public static Node minimax(Node[][] board, int depth, boolean isMax, double alpha, double beta){
    	Node bestNode = new Node();
        ArrayList<Node> moves = getMoves(board);
    	
    	if (moves.isEmpty() || depth == 0) {//if we're done
    		bestNode.setMiniMaxVal(evalBoard(board, isMax));
    	}
    	
    	else {
    		for(Node move : moves) {
    			if(isMax) { //maximizing player
    				Node[][] tempBoard = board;
    				tempBoard[move.getX()][move.getY()] = move;
    				
    				double score = minimax(tempBoard,depth-1,false,alpha,beta).getMiniMaxVal();
                                
                                System.out.println("Score: "  + score);
    				
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
                if((x == 14)){
                    if(y == 0){
                        if (board[x][y] == null && (board[x][y+1] != null || board[x-1][y+1] != null || board[x-1][y] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y < 14) {
                        if (board[x][y] == null && (board[x][y+1] != null || board[x-1][y+1] != null || board[x-1][y] != null || board[x-1][y-1] != null || board[x][y-1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y == 14){
                        if (board[x][y] == null && (board[x-1][y] != null || board[x-1][y-1] != null || board[x][y-1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    }
                } else if (x == 0){
                    if(y == 0){
                        if (board[x][y] == null && (board[x][y+1] != null || board[x+1][y] != null || board[x+1][y+1] != null )) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y < 14) {
                        if (board[x][y] == null && (board[x][y+1] != null || board[x][y-1] != null || board[x+1][y] != null || board[x+1][y+1] != null || board[x + 1][y - 1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y == 14){
                        if (board[x][y] == null && (board[x][y-1] != null || board[x+1][y] != null || board[x + 1][y - 1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    }
                } else if ( x < 14){
                    if(y == 0){
                        if (board[x][y] == null && (board[x][y+1] != null || board[x+1][y] != null || board[x+1][y+1] != null || board[x-1][y+1] != null || board[x-1][y] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y < 14) {
                        if (board[x][y] == null && (board[x][y+1] != null || board[x][y-1] != null || board[x+1][y] != null || board[x+1][y+1] != null || board[x + 1][y - 1] != null || board[x-1][y+1] != null || board[x-1][y] != null || board[x-1][y-1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    } else if (y == 14){
                        if (board[x][y] == null && (board[x][y-1] != null || board[x+1][y] != null || board[x + 1][y - 1] != null || board[x-1][y] != null || board[x-1][y-1] != null)) { 
                            Node newMove = new Node();
                            newMove.setX(x);
                            newMove.setY(y);
            		
                            moves.add(newMove);
                        }
                    }
                }
            }
    	}
    	System.out.println(Arrays.toString(moves.toArray()));
    	return moves;
    }
    
    //What we need is a new HValue function that evaluates the ENTIRE board and returns a score
    public static int evalBoard(Node[][] board, boolean isMax){
    	int score = 0; 
    	ArrayList<Node> moves = getMoves(board);
        
    	for(Node move : moves) {
    		score += Hvalue(board, move.getX(), move.getY(), isMax);
        }
    	return score;
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
        if (board[x][y] == null) { //if this node is empty
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
        if(x != 0){
            while ((board[x - mod][y] != null) && (mod < x)){  //check nodes to the right
                if(board[x - mod][y].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
        }
      
        mod = 1;
        if(x != 14){
            while((board[x + mod][y] != null) && ((mod + x) < 14)){  //check nodes to the left
                if(board[x + mod][y].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
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
        if(y != 0){
            while ((board[x][y - mod] != null) && (mod < y)){  //check nodes below
                if(board[x][y - mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
        }
        mod = 1;
        if(y != 14){
            while((board[x][y + mod] != null) && ((mod + y) < 14)){  //check nodes above
                if(board[x][y + mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod++;
            }
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
        if((x != 0) && (y != 0)){
            while ((board[x - mod][y - mod] != null) && (mod < y) && (mod < x)){  //check nodes to the lower right
                if(board[x - mod][y - mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
        }
        mod = 1;
        if((x != 14) && (y != 14)){
            while((board[x + mod][y + mod] != null) && ((mod + x) < 14) && ((mod + y) < 14)){  //check nodes to the upper left
                if(board[x + mod][y + mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
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
        if((x != 0) && (y != 14)){
            while ((board[x - mod][y + mod] != null) && ((mod + y) < 14) && (mod < x)){  //check nodes to the upper right
                if(board[x - mod][y + mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
        }
        mod = 1;
        if((y != 0) && (x != 14)){
            while((board[x + mod][y - mod] != null) && (mod < y) && ((mod + x) < 14)){  //check nodes to the lower left
                if(board[x + mod][y - mod].getIsOurNode() == isOurTurn){
                    break;
                }
                accumulator++;
                mod ++;
            }
        }
        return accumulator;
    }
    
}