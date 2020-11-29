package FileManager;

import SudokuGame.ColoredArea;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Interface that is used to manage the user's data
 * It is currently implemented by the UserDataManager class, however it can be extended in the future
 */
public interface UserDataInterface
{
    /**
     * Reads all the user's data from a file
     * @return true if the user's file exist else false
     */
    boolean retrieveUserData();

    /**
     * Writes the user's data to a file
     */
    void saveData();

    /**
     * Getter for username
     * @return String username
     */
    String getUsername();

    /**
     * Getter for wins
     * @return int wins
     */
    int getWins();

    /**
     * Getter for losses
     * @return int losses
     */
    int getLosses();

    /**
     * Getter for draws
     * @return int draws
     */
    int getDraws();

    /**
     * Getter for sudoku games played
     * @return int sudoku games played
     */
    int getSudokuGamesPlayed();

    /**
     * Getter for killer sudoku games played
     * @return int killer sudoku games played
     */
    int getKillerGamesPlayed();

    /**
     * Getter for sudoku board
     * @return ArrayList<Integer> sudoku board
     */
    ArrayList<Integer> getSudokuBoard();

    /**
     * Getter for sudoku gray spots
     * @return HashSet<Integer> sudoku gray spots
     */
    HashSet<Integer> getSudokuGreySpots();

    /**
     * Getter for killer sudoku board
     * @return ArrayList<Integer> killer sudoku board
     */
    ArrayList<Integer> getKillerBoard();

    /**
     * Getter for killer sudoku colored areas
     * @return ArrayList<ColoredArea> killer sudoku colored areas
     */
    ArrayList<ColoredArea> getKillerColoredAreas();

    /**
     * Add a win
     */
    void addWin();

    /**
     * Add a loss
     */
    void addLoss();

    /**
     * Add a draw
     */
    void addDraw();

    /**
     * Add a sudoku game played
     */
    void addSudokuGamePlayed();

    /**
     * Add a killer sudoku game played
     */
    void addKillerGamePlayed();

    /**
     * Setter for sudoku board
     * @param board ArrayList<Integer> a sudoku game board
     */
    void setSudokuBoard(ArrayList<Integer> board);

    /**
     * Setter for killer board
     * @param board ArrayList<Integer> a killer sudoku game board
     */
    void setKillerBoard(ArrayList<Integer> board);


}
