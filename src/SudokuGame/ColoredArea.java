package SudokuGame;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class of an colored area in a Sudoku Killer
 */
public class ColoredArea implements Serializable
{

    private HashSet<Integer> positions;
    private Color color;
    private int characteristicSum;

    /**
     * Constructor
     * @param positions the positions that the area contains
     * @param color the color of the area
     * @param sum the characteristic sum of the area
     */
    public ColoredArea(HashSet<Integer> positions, Color color, int sum){
        this.positions = positions;
        this.color = color;
        characteristicSum = sum;
    }

    /**
     * @param position a position of the game board
     * @return true if the area contains the position
     */
    public boolean contains(int position){
        return positions.contains(position);
    }

    /**
     * Getter of the positions that the area contains
     * @return the positions
     */
    public HashSet<Integer> getPositions() {
        return positions;
    }

    /**
     * Getter of the color
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter of the characteristicSum
     * @return the characteristic sum of the area
     */
    public int getCharacteristicSum() {
        return characteristicSum;
    }
}

