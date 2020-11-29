package SudokuGame;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class that represents a Killer Sudoku game (the board and all the colored areas with their sums)
 */
public class KillerSudoku extends Sudoku
{
    private ArrayList<ColoredArea> coloredAreas;

    /**
     * Constructor that initializes the board and the coloredAreas to the given parameters
     * @param board an ArrayList of integers that represents the game board
     * @param coloredAreas an ArrayList of ColoredArea that represents all the colored areas (color, sum, positions)
     */
    public KillerSudoku(ArrayList<Integer> board, ArrayList<ColoredArea> coloredAreas){
        super(board);

        this.coloredAreas = new ArrayList<>();

        this.coloredAreas.addAll(coloredAreas);
    }

    /**
     * Getter for the coloredAreas
     * @return an ArrayList of ColoredArea that has all the board colored areas
     */
    public ArrayList<ColoredArea> getColoredAreas(){
        return coloredAreas;
    }
}
