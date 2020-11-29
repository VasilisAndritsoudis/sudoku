package test.SudokuGameTest;
import SudokuGame.Sudoku;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

class SudokuTest
{
    private static ArrayList<Integer> simulatedBoard;
    private static Sudoku sudokuGame;
    private static HashSet<Integer> greySpots;

    public SudokuTest(){

    }

    @BeforeAll
    public static void setUp(){
        simulatedBoard = new ArrayList<>();
        greySpots = new HashSet<>();

        simulatedBoard.add(0);
        for(int i = 1; i < 5; i++){
            simulatedBoard.add(i+1);
            greySpots.add(i);
        }


        sudokuGame = new Sudoku(simulatedBoard, greySpots);
    }

    @Test
    public void sudokuPutMethodTest(){
        simulatedBoard.set(0, 1);
        sudokuGame.put(0, 1);

        assertEquals(simulatedBoard, sudokuGame.getBoard());
        assertEquals(greySpots, sudokuGame.getGreySpots());
    }

    @Test
    public void sudokuRemoveMethodTest(){
        simulatedBoard.set(4, 0);
        sudokuGame.remove(4);

        assertEquals(simulatedBoard, sudokuGame.getBoard());
        assertEquals(greySpots, sudokuGame.getGreySpots());
    }

    @Test
    public void indexOutOfRangeTest(){
        sudokuGame.remove(5);
        sudokuGame.put(5, 5);

        assertEquals(simulatedBoard, sudokuGame.getBoard());
        assertEquals(greySpots, sudokuGame.getGreySpots());
    }
}