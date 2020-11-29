package test.SudokuGameTest;
import SudokuGame.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;


public class KillerSudokuTest
{
    private static ArrayList<Integer> simulatedBoard;
    private static KillerSudoku sudokuGame;
    private static ArrayList<ColoredArea> coloredAreas;

    public KillerSudokuTest(){

    }

    @BeforeAll
    public static void setUp(){
        HashSet<Integer> coloredPositions = new HashSet<>();
        simulatedBoard = new ArrayList<>();
        coloredAreas = new ArrayList<>();

        for(int i = 0; i < 81; i++)
            simulatedBoard.add(i+1);

        for(int i = 0; i < 3; i++){
            coloredPositions.add(i);
        }

        coloredAreas.add(new ColoredArea(coloredPositions, Color.GREEN, 15));

        sudokuGame = new KillerSudoku(simulatedBoard, coloredAreas);
    }

    @Test
    public void killerSudokuPutMethodTest(){
        for(int i = 0; i < 81; i++)
            sudokuGame.put(i, i + 1);

        assertEquals(simulatedBoard, sudokuGame.getBoard());
    }

    @Test
    public void killerSudokuReturnColoredAreasTest(){
        assertEquals(coloredAreas, sudokuGame.getColoredAreas());
    }
}
