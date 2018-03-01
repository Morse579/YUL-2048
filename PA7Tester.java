//------------------------------------------------------------------//
// PA7Tester.java                                                   //
//                                                                  //
// Simple Tester for students to use while doing PA7  (2048)        //
//                                                                  //
// Author:  Abhishek Kumar                                          //
// Date:    05/14/17                                                //
//------------------------------------------------------------------//

import java.util.Arrays;
import java.util.Random;

public class PA7Tester {

    // Seed passed to random generator to match the expected output
    private static int SEED = 2017;

    public static void main(String[] args) throws Exception {
        System.out.println("****************** Testing PA7 ******************");

        int score = 0;
        score += testRotateAndCanMove();
        score += testClockwise();
        score += testCounterClockwise();

        System.out.println("*************************************************");
        System.out.println("************** PASSED " + score + " / 3 TESTS ***************");

        if (score == 3) {
            System.out.println("NOTE: THIS DOES NOT GUARANTEE FULL CREDIT IN THE PA");
        }

        System.out.println("*************************************************");
    }

    /************************ TEST ROTATE MOVE ************************/

    private static int testRotateAndCanMove() throws Exception {
        System.out.print("Testing canMove method.............................");

        try {
            Board board = new Board(new Random(SEED), new int[][]{
                    {0, 0, 0, 4},
                    {0, 0, 0, 4},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}});

            if (!board.canMove(Direction.UP)) {
                System.out.println("Board should be able to move UP!");
                return 0;
            }

            if (board.canMove(Direction.RIGHT)) {
                System.out.println("Board should NOT be able to move RIGHT!");
                return 0;
            }

            board.rotate(true);

            if (!board.canMove(Direction.RIGHT)) {
                System.out.println("Board should be able to move RIGHT!");
                return 0;
            }
            
            board.rotate(true);

            if (board.canMove(Direction.LEFT)) {
                System.out.println("Board should be NOT able to move LEFT!");
                return 0;
            }
            board.rotate(false);
            board.rotate(false);

            if (!board.canMove(Direction.UP)) {
                System.out.println("Board should be able to move UP!");
                return 0;
            }

            if (board.canMove(Direction.RIGHT)) {
                System.out.println("Board should NOT be able to move RIGHT!");
                return 0;
            }

            System.out.println("Passed!");
            return 1;

        } catch (Exception e) {
            System.out.println("FAILED!");
            System.out.println("Exception was thrown while trying to" + "run the canMove Method");
            e.printStackTrace();
            return 0;
        }

    }

    public static int testMove() throws Exception {
        System.out.print("Testing move method................................");

        try {
            Board board = new Board(new Random(SEED), new int[][]{
                    {2, 4, 8, 2},
                    {2, 4, 8, 2},
                    {2, 4, 8, 0},
                    {2, 4, 8, 2}});

            board.move(Direction.RIGHT);
            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {2, 4, 8, 2},
                    {2, 4, 8, 2},
                    {0, 2, 4, 8},
                    {2, 4, 8, 2}})) {
                System.out.println("FAILED!");
                System.out.println("move(Direction.RIGHT) is implemented incorrectly");
                return 0;
            }

            board.move(Direction.UP);

            if (board.getScore() != 32) {
                System.out.println("FAILED!");
                System.out.println("Incorrect score! Expected 32, got " +
                        board.getScore());
                return 0;
            }

            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {4, 8, 16, 4},
                    {2, 2, 4, 8},
                    {0, 4, 8, 2},
                    {0, 0, 0, 0}})) {
                System.out.println("FAILED!");
                System.out.println("move(Direction.UP) implemented incorrectly");
                return 0;
            }

            board.move(Direction.LEFT);

            if (board.getScore() != 36) {
                System.out.println("FAILED!");
                System.out.println("Incorrect score! Expected 36, got " +
                        board.getScore());
                return 0;
            }

            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {4, 8, 16, 4},
                    {4, 4, 8, 0},
                    {4, 8, 2, 0},
                    {0, 0, 0, 0}})) {
                System.out.println("FAILED!");
                System.out.println("move(Direction.LEFT) implemented incorrectly");
                return 0;
            }

            board.move(Direction.DOWN);

            if (board.getScore() != 44) {
                System.out.println("FAILED!");
                System.out.println("Incorrect score! Expected 44, got " +
                        board.getScore());
                return 0;
            }

            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {0, 0, 0, 0},
                    {0, 8, 16, 0},
                    {4, 4, 8, 0},
                    {8, 8, 2, 4}})) {
                System.out.println("FAILED!");
                System.out.println("move(Direction.DOWN) implemented incorrectly");
                return 0;
            }

            System.out.println("Passed!");
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    /************************ TEST ROTATE CLOCKWISE ************************/
    public static int testClockwise() throws Exception {
        System.out.print("Testing rotate(clockwise) method...................");

        try {
            Board board = new Board(new Random(SEED), new int[][]{
                    {8, 0, 0, 0},
                    {0, 4, 8, 0},
                    {0, 8, 4, 0},
                    {0, 0, 0, 0}});

            board.rotate(true);
            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {0, 0, 0, 8},
                    {0, 8, 4, 0},
                    {0, 4, 8, 0},
                    {0, 0, 0, 0}})) {
                System.out.println("FAILED!");
                System.out.println("rotate(true) is implemented incorrectly");
                return 0;
            }

            System.out.println("Passed!");
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    /************************ TEST ROTATE COUNTER CLOCKWISE ************************/
    public static int testCounterClockwise() throws Exception {
        System.out.print("Testing rotate(counter-clockwise) method...........");

        try {
            Board board = new Board(new Random(SEED), new int[][]{
                    {8, 1, 0, 0},
                    {0, 4, 8, 0},
                    {0, 8, 4, 0},
                    {0, 0, 0, 0}});

            board.rotate(false);
            if (!Arrays.deepEquals(boardToArray(board), new int[][]{
                    {0, 0, 0, 0},
                    {0, 8, 4, 0},
                    {1, 4, 8, 0},
                    {8, 0, 0, 0}})) {
                System.out.println("FAILED!");
                System.out.println("rotate(true) is implemented incorrectly");
                return 0;
            }

            System.out.println("Passed!");
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    // Creates int[][] given a board object
    private static int[][] boardToArray(Board board) {
        if (board == null) {
            return null;
        }
        int[][] array = new int[board.GRID_SIZE][board.GRID_SIZE];
        for (int r = 0; r < board.GRID_SIZE; r++) {
            for (int c = 0; c < board.GRID_SIZE; c++) {
                array[r][c] = board.getTileValue(r, c);
            }
        }
        return array;
    }

    // Prints matrix
    // Can be used for debugging purposes if needed
    private static void print2DArray(int[][] array) {
        for (int i = 0; i < array.length; i++)
            System.out.println(Arrays.toString(array[i]));
    }

}
