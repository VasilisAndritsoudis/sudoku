package test.GameLogicTest;

import GameLogic.KillerSudokuLogic;
import SudokuGame.ColoredArea;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class KillerSudokuLogicTest {

    private KillerSudokuLogic game;
    private ArrayList<ColoredArea> areas;

    @Before
    public void init(){
        HashSet<Integer> posA = new HashSet<>(Arrays.asList(0, 4, 8, 12));
        ColoredArea A = new ColoredArea(posA, Color.BLACK, 10);

        HashSet<Integer> posB = new HashSet<>(Arrays.asList(1, 5, 9));
        ColoredArea B = new ColoredArea(posB, Color.GREEN, 7);

        HashSet<Integer> posC = new HashSet<>(Arrays.asList(2, 3, 7));
        ColoredArea C = new ColoredArea(posC, Color.BLUE, 9);

        HashSet<Integer> posD = new HashSet<>(Arrays.asList(6, 10, 11));
        ColoredArea D = new ColoredArea(posD, Color.RED, 6);

        HashSet<Integer> posE = new HashSet<>(Arrays.asList(13, 14, 15));
        ColoredArea E = new ColoredArea(posE, Color.YELLOW, 8);

        areas = new ArrayList<>(Arrays.asList(A, B, C, D, E));

        ArrayList<Integer> aBoard = new ArrayList<>(
                    Arrays.asList(  0, 0, 0, 0,
                                    0, 0, 0, 0,
                                    0, 0, 0, 0,
                                    0, 0, 0, 0)
        );

        game = new KillerSudokuLogic(aBoard, areas);
    }

    @Test
    public void getRedSumsTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();
        HashSet<Integer> actualRedSums = new HashSet<>();

        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        ArrayList<Integer> aBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 0, 0,
                                0, 0, 0, 0,
                                0, 0, 0, 0,
                                0, 9, 0, 0)
        );
        game = new KillerSudokuLogic(aBoard, areas);
        actualRedSums.add(13);
        actualRedSums.add(14);
        actualRedSums.add(15);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        aBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 0, 0,
                                0, 0, 2, 0,
                                0, 0, 0, 2,
                                0, 8, 0, 0)
        );
        game = new KillerSudokuLogic(aBoard, areas);
        actualRedPositions.add(6);
        actualRedPositions.add(11);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        aBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 0, 0,
                                0, 0, 2, 0,
                                0, 0, 4, 2,
                                0, 8, 0, 0)
        );
        game = new KillerSudokuLogic(aBoard, areas);
        actualRedSums.add(10);
        actualRedSums.add(6);
        actualRedSums.add(11);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());
    }

    @Test
    public void makeMoveTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();
        HashSet<Integer> actualRedSums = new HashSet<>();

        game.makeMove(5, 4);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        game.makeMove(1, 4);
        actualRedPositions.add(1);
        actualRedPositions.add(5);
        actualRedSums.add(1);
        actualRedSums.add(5);
        actualRedSums.add(9);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        game.makeMove(11, 2);
        game.makeMove(6, 2);
        actualRedPositions.add(6);
        actualRedPositions.add(11);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        game.makeMove(10, 1);
        actualRedSums.add(6);
        actualRedSums.add(10);
        actualRedSums.add(11);
    }

    @Test
    public void undoMoveTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();
        HashSet<Integer> actualRedSums = new HashSet<>();

        game.makeMove(6, 1);
        game.makeMove(11, 1);
        game.undoMove(11);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        game.makeMove(10, 9);
        game.undoMove(11);
        actualRedSums.add(6);
        actualRedSums.add(10);
        actualRedSums.add(11);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());

        game.makeMove(12, 3);
        game.makeMove(15, 3);
        game.makeMove(0, 3);
        game.makeMove(8, 3);
        game.undoMove(12);
        actualRedPositions.add(8);
        actualRedPositions.add(0);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualRedSums, game.getRedSums());
    }

    @Test
    public void availableMovesTest(){
        game.makeMove(5, 4);
        game.makeMove(9, 1);
        assertEquals(new ArrayList<Integer>(Arrays.asList(2)), game.availableMoves(1));
        game.undoMove(5);
        game.undoMove(9);

        game.makeMove(11, 1);
        assertEquals(new ArrayList<Integer>(Arrays.asList(2, 3, 4)), game.availableMoves(6));

        game.makeMove(4, 3);
        assertEquals(new ArrayList<Integer>(Arrays.asList(2, 4)), game.availableMoves(6));
    }

    @Test
    public void gameIsOverTest(){
        assertFalse(game.gameIsOver());

        ArrayList<Integer> aBoard = new ArrayList<>(
                Arrays.asList(  1, 2, 3, 4,
                                3, 4, 1, 2,
                                4, 1, 2, 3,
                                2, 3, 4, 1)
        );
        game = new KillerSudokuLogic(aBoard, areas);
        assertTrue(game.gameIsOver());

        aBoard = new ArrayList<>(
                Arrays.asList(  1, 2, 3, 4,
                                3, 4, 1, 2,
                                4, 1, 2, 3,
                                2, 3, 0, 1)
        );
        game = new KillerSudokuLogic(aBoard, areas);
        game.makeMove(14, 4);
        assertTrue(game.gameIsOver());

        game = new KillerSudokuLogic(aBoard, areas);
        game.makeMove(14, 5);
        assertFalse(game.gameIsOver());
        game.undoMove(14);

        game.makeMove(14, 4);
        assertTrue(game.gameIsOver());
    }
}
