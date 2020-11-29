package User;

import FileManager.UserDataInterface;
import FileManager.UserDataManager;
import SudokuGame.ColoredArea;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class that stores all the user's data and is used in the UI aspect
 * The way the data is handled can be changed without the need to change anything outside this class
 */
public class User
{
    private UserDataInterface dataManager;

    /**
     * Constructor that initializes a new user
     * @param username A String that represents the user's name
     */
    public User(String username){
        String filepath = "users\\" + username + ".txt";
        dataManager = new UserDataManager(filepath);
    }

    /**
     * Searches the user in the saved users and loads his/her data if found
     * @return true if user is found else false
     */
    public boolean login(){
        return dataManager.retrieveUserData();
    }

    /**
     * Saves the user's data to a file
     */
    public void saveData(){
        dataManager.saveData();
    }


    /**
     * Getter for username
     * @return String username
     */
    public String getUsername(){ return dataManager.getUsername(); }

    /**
     * Getter for wins
     * @return int wins
     */
    public int getWins() { return dataManager.getWins(); }

    /**
     * Getter for losses
     * @return int losses
     */
    public int getLosses() { return dataManager.getLosses(); }

    /**
     * Getter for draws
     * @return int draws
     */
    public int getDraws() { return dataManager.getDraws(); }

    /**
     * Getter for sudoku games played
     * @return int sudoku games played
     */
    public int getSudokuGamesPlayed() { return dataManager.getSudokuGamesPlayed(); }

    /**
     * Getter for killer sudoku games played
     * @return int killer sudoku games played
     */
    public int getKillerGamesPlayed() { return dataManager.getKillerGamesPlayed(); }

    /**
     * Getter for sudoku board
     * @return ArrayList<Integer> sudoku board
     */
    public ArrayList<Integer> getSudokuBoard() { return dataManager.getSudokuBoard(); }

    /**
     * Getter for sudoku gray spots
     * @return HashSet<Integer> sudoku gray spots
     */
    public HashSet<Integer> getSudokuGreySpots() { return dataManager.getSudokuGreySpots(); }

    /**
     * Getter for killer sudoku board
     * @return ArrayList<Integer> killer sudoku board
     */
    public ArrayList<Integer> getKillerBoard() { return dataManager.getKillerBoard(); }

    /**
     * Getter for killer sudoku colored areas
     * @return ArrayList<ColoredArea> killer sudoku colored areas
     */
    public ArrayList<ColoredArea> getKillerColoredAreas() { return dataManager.getKillerColoredAreas(); }

    /**
     * Add a win
     */
    public void addWin() { dataManager.addWin(); }

    /**
     * Add a loss
     */
    public void addLoss() { dataManager.addLoss(); }

    /**
     * Add a draw
     */
    public void addDraw() { dataManager.addDraw(); }

    /**
     * Add a sudoku game played
     */
    public void addSudokuGamePlayed() { dataManager.addSudokuGamePlayed(); }

    /**
     * Add a killer sudoku game played
     */
    public void addKillerGamePlayed() { dataManager.addKillerGamePlayed(); }

    /**
     * Setter for sudoku board
     * @param board ArrayList<Integer> a sudoku game board
     */
    public void setSudokuBoard(ArrayList<Integer> board) { dataManager.setSudokuBoard(board); }

    /**
     * Setter for killer board
     * @param board ArrayList<Integer> a killer sudoku game board
     */
    public void setKillerBoard(ArrayList<Integer> board) { dataManager.setKillerBoard(board); }
}
