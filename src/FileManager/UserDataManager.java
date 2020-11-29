package FileManager;

import SudokuGame.ColoredArea;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * UserDataManager class implements UserDataInterface
 * In this implementation game boards are stored in files and when a game is requested it is loaded from this file.
 * In the future game boards can be created (with some algorithm) instead of being loaded from a file
 */

public class UserDataManager implements UserDataInterface
{
    private String filename;
    private ArrayList<String> userData;

    /**
     * Constructor that initializes a new user.
     * Sets everything to 0 and loads (from the user's file) the first sudoku board and the first killer colored areas.
     * All the values are stored in a ArrayList of Strings
     *  - userData.get(0) : username (String)
     *  - userData.get(1) : wins (int)
     *  - userData.get(2) : losses (int)
     *  - userData.get(3) : draws (int)
     *  - userData.get(4) : sudokuGamesPlayed (int)
     *  - userData.get(5) : killerGamesPlayed (int)
     *  - userData.get(6) : sudokuBoard (ArrayList<Integer>)
     *  - userData.get(7) : sudokuGreySpots (HashSet<Integer>)
     *  - userData.get(8) : killerBoard (ArrayList<Integer>)
     *  - userData.get(9) : killerColoredAreas (ArrayList<ColoredArea>)
     * @param filename string that represents the path and filename of the user's data (users/username.txt)
     */
    public UserDataManager(String filename){
        this.filename = filename;

        userData = new ArrayList<>();
        userData.add(filename.split(Pattern.quote("\\"))[1].split(Pattern.quote("."))[0]); // Takes only the username part of the filename (users/username.txt)
        userData.add("0");
        userData.add("0");
        userData.add("0");
        userData.add("0");
        userData.add("0");

        // Add the first sudoku board
        ArrayList<Integer> board = FileManager.readSudokuGameBoard("games\\gameBoards.txt", 0);
        StringBuilder stringBoard = new StringBuilder();
        assert board != null;
        for(int x : board)
            stringBoard.append(x);
        userData.add(stringBoard.toString());

        // Add the first sudoku grey areas
        HashSet<Integer> greySpots = FileManager.readSudokuGreySpots("games\\gameBoards.txt", 0);
        StringBuilder stringGreySpots = new StringBuilder();
        for(int x : greySpots)
            stringGreySpots.append(x + " ");
        userData.add(stringGreySpots.toString());

        // Add the first killer sudoku board (consists only of 0)
        stringBoard = new StringBuilder();
        stringBoard.append("0".repeat(board.size()));
        userData.add(stringBoard.toString());

        // Add the first killer colored areas
        userData.add(FileManager.readKillerColoredAreas("games\\gameBoards.txt", 10));
    }

    /**
     * Reads all the user's data from a file
     * @return true if the user's file exist else false
     */
    public boolean retrieveUserData(){
        ArrayList<String> tempUserData = FileManager.read(this.filename);

        if(tempUserData != null){
            userData = tempUserData;
            return true;
        }

        return false;
    }

    /**
     * Writes the user's data to a file
     */
    public void saveData(){
        FileManager.write(this.filename, this.userData);
    }

    /**
     * Getter for username
     * @return String username
     */
    public String getUsername(){ return userData.get(0); }

    /**
     * Getter for wins
     * @return int wins
     */
    public int getWins() { return Integer.parseInt(userData.get(1)); }

    /**
     * Getter for losses
     * @return int losses
     */
    public int getLosses() { return Integer.parseInt(userData.get(2)); }

    /**
     * Getter for draws
     * @return int draws
     */
    public int getDraws() { return Integer.parseInt(userData.get(3)); }

    /**
     * Getter for sudoku games played
     * @return int sudoku games played
     */
    public int getSudokuGamesPlayed() { return Integer.parseInt(userData.get(4)); }

    /**
     * Getter for killer sudoku games played
     * @return int killer sudoku games played
     */
    public int getKillerGamesPlayed() { return Integer.parseInt(userData.get(5)); }

    /**
     * Getter for sudoku board
     * @return ArrayList<Integer> sudoku board
     */
    public ArrayList<Integer> getSudokuBoard(){
        ArrayList<Integer> board = new ArrayList<>();

        String stringBoard = userData.get(6);

        for(int i = 0; i < stringBoard.length(); i++)
            board.add(Integer.parseInt(String.valueOf(stringBoard.charAt(i))));

        return board;
    }

    /**
     * Getter for sudoku gray spots
     * @return HashSet<Integer> sudoku gray spots
     */
    public HashSet<Integer> getSudokuGreySpots(){
        HashSet<Integer> greySpots = new HashSet<>();

        String[] stringGreySpots = userData.get(7).split(" ");

        for(int i = 0; i < stringGreySpots.length; i++)
            greySpots.add(Integer.parseInt(stringGreySpots[i]));

        return greySpots;
    }

    /**
     * Getter for killer sudoku board
     * @return ArrayList<Integer> killer sudoku board
     */
    public ArrayList<Integer> getKillerBoard(){
        ArrayList<Integer> board = new ArrayList<>();

        String stringBoard = userData.get(8);

        for(int i = 0; i < stringBoard.length(); i++)
            board.add(Integer.parseInt(String.valueOf(stringBoard.charAt(i))));

        return board;
    }

    /**
     * Getter for killer sudoku colored areas
     * @return ArrayList<ColoredArea> killer sudoku colored areas
     */
    public ArrayList<ColoredArea> getKillerColoredAreas(){
        ArrayList<ColoredArea> coloredAreaList = new ArrayList<>();

        ArrayList<String> stringColoredArea = new ArrayList<>(Arrays.asList(userData.get(9).split(" ")));

        for(String string : stringColoredArea){
            try
            {
                coloredAreaList.add((ColoredArea) fromString(string));
            }catch(IOException | ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
        }

        return coloredAreaList;
    }

    /**
     * Add a win
     */
    public void addWin() { userData.set(1, Integer.toString(Integer.parseInt(userData.get(1)) + 1)); }

    /**
     * Add a loss
     */
    public void addLoss() { userData.set(2, Integer.toString(Integer.parseInt(userData.get(2)) + 1)); }

    /**
     * Add a draw
     */
    public void addDraw() { userData.set(3, Integer.toString(Integer.parseInt(userData.get(3)) + 1)); }

    /**
     * Add a sudoku game played
     */
    public void addSudokuGamePlayed(){
        if(this.getSudokuGamesPlayed() < 9)
            userData.set(4, Integer.toString(Integer.parseInt(userData.get(4)) + 1));
        else{
            System.out.println("Already played all 10 sudoku games.");
            return;
        }

        // Load the next sudoku board

        ArrayList<Integer> board = FileManager.readSudokuGameBoard("games\\gameBoards.txt", this.getSudokuGamesPlayed());
        StringBuilder stringBoard = new StringBuilder();

        assert board != null;

        for(int x : board)
            stringBoard.append(x);

        userData.set(6, stringBoard.toString());

        // Load the next sudoku grey spots

        HashSet<Integer> greySpots = FileManager.readSudokuGreySpots("games\\gameBoards.txt", this.getSudokuGamesPlayed());

        StringBuilder stringGreySpots = new StringBuilder();
        for(int x : greySpots)
            stringGreySpots.append(x + " ");
        userData.set(7, stringGreySpots.toString());
    }

    /**
     * Add a killer sudoku game played
     */
    public void addKillerGamePlayed(){
        if(this.getKillerGamesPlayed() < 9)
            userData.set(5, Integer.toString(Integer.parseInt(userData.get(5)) + 1));
        else{
            System.out.println("Already played 10 killer sudoku games.");
            return;
        }

        // Load the next killer sudoku board

        userData.set(8, "0".repeat(this.getKillerBoard().size()));

        // Load the next killer Colored Area

        userData.set(9, FileManager.readKillerColoredAreas("games\\gameBoards.txt", this.getKillerGamesPlayed() + 10));
    }

    /**
     * Setter for sudoku board
     * @param board ArrayList<Integer> a sudoku game board
     */
    public void setSudokuBoard(ArrayList<Integer> board){
        StringBuilder stringBoard = new StringBuilder();

        for(int x : board)
            stringBoard.append(x);

        userData.set(6, stringBoard.toString());
    }

    /**
     * Setter for killer board
     * @param board ArrayList<Integer> a killer sudoku game board
     */
    public void setKillerBoard(ArrayList<Integer> board){
        StringBuilder stringBoard = new StringBuilder();

        for(int x : board)
            stringBoard.append(x);

        userData.set(8, stringBoard.toString());
    }

    /**
     * Converts a String to an Object (of type ColoredArea)
     * @param string the given object saved as a String
     * @return the Object in its normal state
     */
    private static Object fromString(String string) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(string);
        ObjectInputStream oiStream = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = oiStream.readObject();
        oiStream.close();
        return o;
    }
}
