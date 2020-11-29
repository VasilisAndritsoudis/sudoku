package SudokuGame;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class that represents a Sudoku game (the board and the initial value indexes)
 */
public class Sudoku
{
    protected Integer[] board;
    private HashSet<Integer> greySpots;


    /**
     * Constructor that creates a game board and also grey positions based on the given parameters
     * @param startingBoard an ArrayList of integers that represents the game board
     * @param greySpots a HashSet of integers that stores the indexes of the initial values
     *                  (should not be changed becuase you may end up with an unsolvable sudoku)
     */
    public Sudoku(ArrayList<Integer> startingBoard, HashSet<Integer> greySpots){
        this.board = new Integer[startingBoard.size()];

        this.board = startingBoard.toArray(this.board);

        this.greySpots = new HashSet<>();

        this.greySpots.addAll(greySpots);
    }

    /**
     * Constructor that creates a game board based on the given parameter
     * @param startingBoard an ArrayList that represents the game board
     */
    public Sudoku(ArrayList<Integer> startingBoard){
        this.board = new Integer[startingBoard.size()];

        this.board = startingBoard.toArray(this.board);

        this.greySpots = null;
    }

    /**
     * Creates an empty (only 0) sudoku board of given size
     * @param size int that defines the desired sudoku board size
     */
    public Sudoku(int size){
        this.board = new Integer[size];

        for(int i = 0; i < size; i++)
            this.board[i] = 0;

        this.greySpots = null;
    }

    /**
     * Puts the value in the game board at the position of index
     * @param index int that specifies the position to be placed
     * @param value int that represents the value to be entered
     */
    public void put(int index, int value){
        try{
            this.board[index] = value;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes (sets to 0) the value at the position of index
     * @param index int that specifies which position to change to 0
     */
    public void remove(int index){
        try{
            this.board[index] = 0;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the value at a specified position - index
     * @param index int that specifies the position to be retured
     * @return an int value of the position - index
     */
    public int getValueAt (int index){
        int result = 0;

        try{
            result = this.board[index];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Getter for the board
     * @return an ArrayList of integers that represents the game board
     */
    public ArrayList<Integer> getBoard(){
        return new ArrayList<>(Arrays.asList(this.board));
    }

    /**
     * Getter for the gray spots
     * @return a HashSet of integers that stores the indexes of the initial values
     */
    public HashSet<Integer> getGreySpots(){
        if(this.greySpots != null){
            HashSet<Integer> greySpots = new HashSet<>();

            greySpots.addAll(this.greySpots);

            return greySpots;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Getter fot the board size
     * @return an int that represents the board size
     */
    public int getSize() { return this.board.length; }
}
