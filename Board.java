
/*
 * Name: Bo Zhang; Yushan Liu 
 * Login: cs8bscu; cs8bsgr
 * Date: 05/20/2017
 * File: Board.java
 * Sources of Help: textbook, tutors
 *
 * Describe what the program does here:
 * This Class is used to construct a Board object to be used
 * for the simulation of the game 2048. It can create a fresh
 * board or load an already existing board. In addition this
 * class allows the user to save their current game to a new, 
 * specified file. The class also allows for the board to be 
 * rotated 90 degrees to the right or left. Baed on the direction
 * passed in by the user, this class will then move tiles
 * existing on the board in a certain direction, combining tiles
 * of the same value. The game is considered to be over when
 * the board cannot move in any direction.
 * /

//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.Random;

public class Board {
    /**
     * Number of tiles showing when the game starts
     */
    public final int NUM_START_TILES = 2;

    /**
     * The probability (times 100) that a randomly
     * generated tile will be a 2 (vs a 4)
     */
    public final int TWO_PROBABILITY = 90;

    /**
     * The size of the grid
     */
    public final int GRID_SIZE;

    private int[][] grid;  // The grid of tile values
    private int score;     // The current score

    // You do not have to use these variables
    private final Random random;    // A random number generator (for testing)

    /**
     * Name: Board(Random random, int boardSize).
     * 
     * Purpose: The purpose of this method is to create or construct a fresh
     * board for the user with two random tiles places within the board. This
     * board will have a particular boardSize that the user sets, as well as a
     * random
     *
     * @param boardSize size of the 2048 game board to be used.
     * @param random    Random random represents the random number which 
     *                  be used to specific where (after every move) a 
     *                  new tile should be added to the board when playing.
     */
    public Board(Random random, int boardSize) {
        if (boardSize < 2) boardSize = 4;

        // initialize member variables
        this.random = random;
        this.GRID_SIZE = boardSize;
        this.grid = new int[boardSize][boardSize];
        this.score = 0;

        // loop through and add two initial tiles to the board randomly
        for (int index = 0; index < NUM_START_TILES; index++) {
            addRandomTile();
        }
    }


    /**
     * Constructor used to load boards for grading/testing
     * 
     * THIS IS USED FOR GRADING - DO NOT CHANGE IT
     *
     * @param random
     * @param inputBoard
     */
    public Board(Random random, int[][] inputBoard) {
        this.random = random;
        this.GRID_SIZE = inputBoard.length;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                this.grid[r][c] = inputBoard[r][c];
            }
        }
    }

    /**
     * return the tile value in a particular cell in the grid.
     *
     * @param row The row
     * @param col The column
     * @return The value of the tile at (row, col)
     */
    public int getTileValue(int row, int col) {
        return grid[row][col];
    }

    /**
     * Get the current score
     *
     * @return the current score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Name: addRandomTile()
     * 
     * Purpose: The purpose of this method is to add a random tile of either
     * value 2 or 4 to a random empty space on the 2048
     * board. The place where this tile is added is dependent on the random
     * value associated with each board object. If no tiles are empty, it
     * returns without changing the board.
     */
    public void addRandomTile() {
        int count = 0;
        // loop through grid keeping count of every empty space on board
        for (int rowI = 0; rowI < grid.length; rowI++) {
            for (int colI = 0; colI < grid[rowI].length; colI++) {
                if (grid[rowI][colI] == 0) {
                    count++;
                }
            }
        }

        // if count is still 0 after loop, no empty spaces, return
        if (count == 0) {
            System.out.println("There are no empty spaces!");
            return;
        }

        // keep track of where on board random tile should be placed
        int location = random.nextInt(count);
        int value = random.nextInt(100);

        // reset count
        count = 0;
        // loop through grid checking where grid is 0 & incrementing count
        for (int rowI = 0; rowI < grid.length; rowI++) {
            for (int colI = 0; colI < grid[rowI].length; colI++) {
                if (grid[rowI][colI] == 0) {
                    // if count equals random location generated, place tile
                    if (count == location) {
                        System.out.println("Adding a tile to location " + rowI + ", " + colI);
                        if (value < TWO_PROBABILITY) {
                            grid[rowI][colI] = 2;
                        } else {
                            grid[rowI][colI] = 4;
                        }
                    }
                    count++;
                }
            }
        }
    }

    /**
     * Name: isGameOver()
     * <p>
     * Purpose: The purpose of this method is to check whether or not the game
     * in play is over. The game is officially over once there are no longer any
     * valid moves that can be made in any direction. If the game is over, this
     * method will return true and print the words: "Game Over!" This method
     * will be checked before any movement is ever made.
     *
     * @return true if the game is over, and false if the game isn't over
     */
    public boolean isGameOver() {
        return (!canMoveLeft() && !canMoveRight() && !canMoveUp()
                && !canMoveDown());
    }


    /**
     * Name: canMove(Direction direction)
     * 
     * Purpose: The purpose of this method is to check to see if the movement of
     * the tiles in any direction can actually take place. It does not move the
     * tiles, but at every index of the grid, checks to see if there is a tile
     * above, below, to the left or right that has the same value. If this is
     * the case, then that tile can be moved. It also checks if there is an
     * empty (0) tile at a specified index, as this also indicates that movement
     * can be possible. This method is called within move() so that that method
     * can determine whether or not tiles should be moved.
     *
     * @param direction direction the tiles will move (if possible)
     * @return true if the movement can be done and false if it cannot
     */
    public boolean canMove(Direction direction) {
        // utilize helper methods to check if movement in a particular
        // direction is possible
      
      if(direction == null)
        return false;
      
        switch (direction) {
            case UP:
                return canMoveUp();
            case RIGHT:
                return canMoveRight();
            case DOWN:
                return canMoveDown();
            case LEFT:
                return canMoveLeft();
            default:
                // If we got here, something went wrong, so return false
                return false;
        }
    }

    /**
     * Name: move(Direction direction)
     * 
     * Purpose: The purpose of this method is to move the tiles in the game
     * board by a specified direction passed in as a parameter. If the movement
     * cannot be done, the method returns false. If the movement can be done, it
     * moves the tiles and returns true. This method relies on the help of four
     * other helper methods to perform the game play.
     *
     * @param direction direction the tiles will move (if possible)
     * @return true if the movement can be done and false if it cannot
     */
    public boolean move(Direction direction) {
        // if canMove is false, exit and don't move tiles
        if (!canMove(direction)) return false;

        // move in relationship to the direction passed in
        switch (direction) {
            case UP:
                moveUp();
                break;
            case RIGHT:
                moveRight();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            default:
                // This should never happen
                return false;
        }

        return true;
    }

    //TODO: You will implement the methods below this comment as described in
    // the PA writeup

     /**
     * Name: moveRight()
     * 
     * Purpose: The purpose of this method is to move the tiles to empty space 
     * at the right and combine adjacent tiles of same value in the same column
     * 
     * @param: none
     * 
     * @return:none(void)
     */
    private void moveRight() {
     rotate(true);
     moveDown();
     rotate(false);
    }

    /**
     * Name: moveLeft()
     * 
     * Purpose: The purpose of this method is to move the tiles to empty space 
     * at the Left and combine adjacent tiles of same value in the same column
     * 
     * @param: none
     * 
     * @return:none(void)
     */
    private void moveLeft() {
     rotate(false);
     moveDown();
     rotate(true);
    }

    /**
     * Name: moveDown()
     * 
     * Purpose: The purpose of this method is to move the tiles to empty space 
     * below and combine adjacent tiles of same value in the same row
     * 
     * @param: none
     * 
     * @return:none(void)
     */
    private void moveDown() {
      for (int c = 0; c < GRID_SIZE; c++){
       int current = GRID_SIZE-1;    
       //move all non-zero number to the bottom
       for(int r = GRID_SIZE-1; r >= 0; r--){
             if(grid[r][c] != 0){
                  int temp = grid[r][c];
                  grid[r][c] = grid[current][c];
                  grid[current][c] = temp;
                  current--;
                }
               }
          current=GRID_SIZE-1;//initialize current row
           
           //add the adjacent tiles which are of same value
           //set the upper one to be zero and count scores
           for(int r = GRID_SIZE-2; r >= 0; r--){
              if(grid[r][c] != 0 ){
                if(grid[r][c] == grid[r+1][c]){
                  grid[r+1][c] = grid[r][c]*2;
                  score+=grid[r][c]*2;
                  grid[r][c] = 0;
                  }
                 }
           }
            
            //move all modified non-zero number to the bottom  
            for(int r = GRID_SIZE-1; r >= 0; r--){
               if(grid[r][c] != 0){
                  int temp = grid[r][c];
                  grid[r][c] = grid[current][c];
                  grid[current][c] = temp;
                  current--;
                  }
                 }
           }
        }

    /**
     * Name: moveUp()
     * 
     * Purpose: The purpose of this method is to move the tiles to empty space 
     * above and combine adjacent tiles of same value in the same row
     * 
     * @param: none
     * 
     * @return:none(void)
     */
    private void moveUp() {
     rotate(true);
     rotate(true);
     moveDown();
     rotate(true);
     rotate(true);
   }

  /**
   * Name:canMoveLeft()
   * 
   * Purpose: The purpose of this method is to check if tiles can move to left
   * 
   * @param: none
   * 
   * @return: true if can, otherwise false
   */
    private boolean canMoveLeft() {
     for (int r = GRID_SIZE-1; r >=0; r--){
      for(int c = GRID_SIZE-1; c > 0; c--){
        if(grid[r][c] != 0){
         if(grid[r][c-1]==0 
          || grid[r][c]==grid[r][c-1])
        return true;
       }
      }
     }
        return false;
    }

  /**
   * Name:canMoveRight()
   * 
   * Purpose: The purpose of this method is to check if tiles can move to right
   * 
   * @param: none
   * 
   * @return: true if can, otherwise false
   */
    private boolean canMoveRight() {
     for (int r = 0; r < GRID_SIZE; r++){
      for(int c = 0; c< GRID_SIZE-1; c++){
        if(grid[r][c] != 0){
         if(grid[r][c+1] == 0 
          || grid[r][c] == grid[r][c+1])
        return true;
        }
      }
     }
        return false;
    }

  /**
   * Name:canMoveUp()
   * 
   * Purpose: The purpose of this method is to check if tiles can move to the top
   * 
   * @param: none
   * 
   * @return: true if can, otherwise false
   */
    private boolean canMoveUp() {
     for (int r = GRID_SIZE-1; r > 0; r--){
      for(int c = 0; c< GRID_SIZE; c++){
       if(grid[r][c] != 0){
        if(grid[r-1][c]==0 
          || grid[r-1][c]==grid[r][c])
        return true;
       }
      }
     }
        return false;
    }

  /**
   * Name:canMoveDown()
   * 
   * Purpose: The purpose of this method is to check if tiles can move to the bottom
   * 
   * @param: none
   * 
   * @return: true if can, otherwise false
   */
    private boolean canMoveDown() {
     for (int r = 0; r < GRID_SIZE-1; r++){
      for(int c = 0; c < GRID_SIZE; c++){
        if(grid[r][c] != 0){
         if(grid[r+1][c]==0 
          || grid[r+1][c]==grid[r][c])
        return true;
       }
      }
     }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -"
                        : String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }

    /**
     * Set the grid to a new value.  This method can be used for
     * testing and is used by our testing/grading script.
     * @param newGrid The values to set the grid to
     */
    public void setGrid(int[][] newGrid)
    {
        if (newGrid.length != GRID_SIZE ||
                newGrid[0].length != GRID_SIZE) {
            System.out.println("Attempt to set grid to incorrect size");
            return;
                }
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = newGrid[r][c];
            }
        }
    }

    /**
     * get a copy of the grid
     * @return A copy of the grid
     */
    public int[][] getGrid()
    {
        int[][] gridCopy = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++) {
                gridCopy[r][c] = grid[r][c];
            }
        }
        return gridCopy;
    }
    
    /**
     * The purpose of this method is to rotate all 
     * the tiles on the game board either clockwise 
     * 90 degrees or counterclockwise
     * @param boolean clockwise to decide rotate direction 
     * true:clockwise, false:counter-clockwise
     */
    public void rotate(boolean clockwise){
    //copy of the original matrix
   int[][] temp = this.getGrid();
    
   //if clockwise is true rotate clockwise
     if(clockwise){
     for(int r = 0 ; r < GRID_SIZE; r++){
      for (int c = 0 ; c<GRID_SIZE; c++ ){
       grid[c][GRID_SIZE-r-1] = temp[r][c]; 
       }
      }
     }    
    
    //if clockwise is false rotate counter-clockwise
    else{
    for(int r = 0 ; r < GRID_SIZE; r++){
      for (int c = 0 ; c<GRID_SIZE; c++ ){
       grid[GRID_SIZE-c-1][r] = temp[r][c]; 
       }
      }
     }
   }
    


}
