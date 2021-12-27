package puzzles.slide.model;
import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Configuration for the slide puzzle
 *
 * @author Rafid Khan
 */
public class SlideConfig implements Configuration<SlideConfig> {
    /** board representation of the config */
    private String[][] board;

    /** number of rows */
    private final int row;

    /** number of columns */
    private final int col;

    /** temp var */
    private String temp;

    /** array containing possible moves */
    private final String[] directions = { "up", "down", "right", "left" };

    /**
     * constructor
     */
    public SlideConfig(String filename) throws FileNotFoundException {
        Scanner f = new Scanner(new File(filename));
        this.row = f.nextInt();
        this.col = f.nextInt();
        this.board = new String[row][col];
        this.temp = null;

        while (f.hasNext()) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    temp = f.next();
                    this.board[i][j] = temp;
                    }
                }
            }
        f.close();
    }

    /**
     * copy constructor
     * @param copy
     */
     public SlideConfig(SlideConfig copy){
        this.board = copy.board;
        this.row = copy.row;
        this.col = copy.col;
        this.temp = null;
    }

    /**
    getNeighbors()
     called by Solver to completely solve the configuration
     @return: a list of the neighbors of a configuration
     */
    public ArrayList<Configuration<SlideConfig>> getNeighbors(){
        ArrayList<Configuration<SlideConfig>> neighbors = new ArrayList<>();
        int i = 0;
        while (i < directions.length){
            SlideConfig child = new SlideConfig(this);
            child.setBoard(child, copy());
            if(isValid(child.getBoard(), directions[i])){
                if(!neighbors.contains(child)) {
                    neighbors.add(child);
                    i++;
                }
            }
            else {
                i++;
            }
        }
        return neighbors;
    }

    /**
     * isValid()
     * determines if a configuration is a possible solution
     * @param board of the configuration
     * @param direction of the possible move
     * @return true if it is a valid config false if not
     */
    public boolean isValid(String[][] board, String direction){
        int[] position = find();
        int x = position[0];
        int y = position[1];

        switch (direction) {
            case "up":
                if (x - 1 >= 0) {
                    board[x][y] = board[x - 1][y];
                    board[x - 1][y] = (".");
                    return true;
                }
                break;
            case "down":
                if (x + 1 <= board.length - 1) {
                    board[x][y] = board[x + 1][y];
                    board[x + 1][y] = (".");
                    return true;
                }
                break;
            case "right":
                if (y + 1 <= board.length - 1) {
                    board[x][y] = board[x][y + 1];
                    board[x][y + 1] = (".");
                    return true;
                }
                break;
            case "left":
                if (y - 1 >= 0) {
                    board[x][y] = board[x][y - 1];
                    board[x][y - 1] = (".");
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    /**
     * equals()
     * determines if two configs are equivalent by comparing their
     * hashcodes
     * @param other the other object
     * @return true if they are equal otherwise false
     */
    @Override
    public boolean equals(Object other){
        return this.hashCode() == other.hashCode();
    }

    /**
     * hashCode()
     * hashes the board of a configuration
     * @return an integer value representing a hashcode
     */
    @Override
    public int hashCode(){
        return Arrays.deepHashCode(this.board);
    }

    /**
     * isGoal()
     * determines if the config is the solution
     * @return true if it is solution otherwise false
     */
    public boolean isGoal() {
        return this.board == this.getSolvedBoard();
    }

    /**
     * toString()
     * @return string representation of SlideConfig
     */
    public String toString(){
        String result = "";
        for (int i = 0; i < row; i++) {
            result += "\n";
            for (int j = 0; j < col; j++) {
                result += (board[i][j] + " ");
            }
        }
        return result;
    }

    /*
                                ****** HELPER METHODS ******
     */

    /**
     * copy()
     * copies the board
     * @return a copy of the board
     */
    public String[][] copy(){
        String[][] copy = new String[row][col];
        for (int i = 0; i < row; i++){
            if (col >= 0) System.arraycopy(board[i], 0, copy[i], 0, col);
        }
        return copy;
    }

    /**
     * Solution()
     * @return returns the solved config
     */
    public Configuration<SlideConfig> Solution(){
        int dimension = row * col;
        int test = (dimension * -1);
        SlideConfig solved = new SlideConfig(this);
        String[][] solution = new String[row][col];

        String[] temp = new String[dimension];
        for (int a = 0; a < dimension; a++){
            String str1 = Integer.toString(a + 1);
            temp[a] = str1;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                solution[i][j] = (temp[dimension + test]);
                test++;
            }
        }
        solution[getRow() - 1][getCol() - 1] = (".");
        setBoard(solved, solution);
        return solved;
    }

    /**
     * getSolvedBoard()
     * @return the solved board
     */
    public String[][] getSolvedBoard(){
        int dimension = row * col;
        int test = (dimension * -1);
        String[][] solution = new String[row][col];

        String[] temp = new String[dimension];
        for (int a = 0; a < dimension; a++){
            String str1 = Integer.toString(a + 1);
            temp[a] = str1;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                solution[i][j] = (temp[dimension + test]);
                test++;
            }
        }
        solution[getRow() - 1][getCol() - 1] = (".");
        return solution;
    }

    /**
     * find()
     * finds where the "." is
     * @return an array that contains the coordinate of the "."
     */
    public int[] find(){
        int[] location = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (Objects.equals(board[i][j], ".")) {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }
        return location;
    }

    /**
     * getCoord()
     * @param x coord
     * @param y coord
     * @return returns value of the board[x][y] value
     */
    public String getCoord(int x, int y){
        return getBoard()[x][y];
    }

    /**
     * isSol()
     * another instance of isGoal()
     * @return true if it is the solution false otherwise
     */
    public boolean isSol(){
        return Objects.equals(getBoard()[getRow() -1][getCol() -1], ".");
    }

    /**
     * getRow()
     * @return the number of rows
     */
    public int getRow() {
        return row;
    }

    /**
     * getCol()
     * @return the number of columns
     */
    public int getCol() {
        return col;
    }

    /**
     * getBoard()
     * @return the board representation
     */
    public String[][] getBoard() {
        return this.board;
    }

    /**
     * setBoard()
     * sets the board to a given board
     * @param obj desired config
     * @param board desired board
     */
    public void setBoard(SlideConfig obj, String[][] board) {
        obj.board = board;
    }
}
