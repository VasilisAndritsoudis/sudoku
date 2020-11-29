package test.GameLogicTest;
import GameLogic.*;
import SudokuGame.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


public class SudokuLogicTest
{
    private SudokuLogic game;

    @Before
    public void init(){
        ArrayList<Integer> aBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 3, 0,
                                0, 4, 0, 2,
                                0, 1, 2, 0,
                                2, 0, 0, 0)
        );
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(2, 5, 7, 9, 10, 12)));
    }

    @Test
    public void emptyPositionsTest(){
        ArrayList<Integer> actualEmptyPositions = new ArrayList<>(
                Arrays.asList(0, 1, 3, 4, 6, 8, 11, 13, 14, 15)
        );
        assertEquals(actualEmptyPositions, game.emptyPositions());

        game.makeMove(15, 1);
        actualEmptyPositions.remove(9);
        assertEquals(actualEmptyPositions, game.emptyPositions());

        game.undoMove(15);
        actualEmptyPositions.add(15);
        assertEquals(actualEmptyPositions, game.emptyPositions());

        game.undoMove(0);
        assertEquals(actualEmptyPositions, game.emptyPositions());

        game.makeMove(1, 1);
        actualEmptyPositions.remove(1);
        assertEquals(actualEmptyPositions, game.emptyPositions());
    }

    @Test
    public void getBoardTest(){
        ArrayList<Integer> actualBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 3, 0,
                                0, 4, 0, 2,
                                0, 1, 2, 0,
                                2, 0, 0, 0)
        );
        assertEquals(actualBoard, game.getBoard());

        game.makeMove(15, 3);
        actualBoard.remove(15);
        actualBoard.add(3);
        assertEquals(actualBoard, game.getBoard());
    }

    @Test
    public void getRedPositionsTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();

        assertEquals(actualRedPositions, game.getRedPositions());

        ArrayList<Integer> aBoard = new ArrayList<>(
                Arrays.asList(  0, 4, 3, 0,
                                0, 4, 0, 2,
                                0, 1, 2, 0,
                                2, 0, 0, 0)
        );
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(1, 2, 5, 7, 9, 10, 12)));
        actualRedPositions.add(1);
        actualRedPositions.add(5);
        assertEquals(actualRedPositions, game.getRedPositions());

        aBoard = new ArrayList<>(
                Arrays.asList(  0, 4, 4, 0,
                                0, 4, 0, 2,
                                0, 1, 2, 0,
                                2, 0, 0, 0)
        );
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(1, 2, 5, 7, 9, 10, 12)));
        actualRedPositions.add(2);
        assertEquals(actualRedPositions, game.getRedPositions());
    }

    @Test
    public void makeMoveTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();

        game.makeMove(0, 1);
        assertEquals(actualRedPositions, game.getRedPositions());

        game.makeMove(8, 1);
        actualRedPositions.add(0);
        actualRedPositions.add(8);
        actualRedPositions.add(9);
        assertEquals(actualRedPositions, game.getRedPositions());

        //Δε γίνεται να γίνει αυτό αλλά σε περίτπωση bug που κάποιος βάλει 0 σε μία θέση
        //δε πρέπει να δημιουργηθουν redPositions
        game.makeMove(1, 0);
        assertEquals(actualRedPositions, game.getRedPositions());
    }

    @Test
    public void undoMoveTest(){
        HashSet<Integer> actualRedPositions = new HashSet<>();
        ArrayList<Integer> actualBoard = new ArrayList<>(
                Arrays.asList(  0, 0, 3, 0,
                                0, 4, 0, 2,
                                0, 1, 2, 0,
                                2, 0, 0, 0)
        );

        game.makeMove(8, 1);
        game.undoMove(8);
        assertEquals(actualRedPositions, game.getRedPositions());
        assertEquals(actualBoard, game.getBoard());

        game.makeMove(15, 1);
        game.makeMove(3, 1);
        game.makeMove(0, 1);
        actualRedPositions.add(0);
        actualRedPositions.add(3);
        actualRedPositions.add(15);
        assertEquals(actualRedPositions, game.getRedPositions());
        game.undoMove(3);
        actualRedPositions.clear();
        assertEquals(actualRedPositions, game.getRedPositions());

    }

    @Test
    public void availableMovesTest(){
        ArrayList<Integer> actualAvailableMove = new ArrayList<>(
                Arrays.asList(1, 3, 4)
        );

        assertEquals(actualAvailableMove, game.availableMoves(15));

        actualAvailableMove.clear();
        actualAvailableMove.add(1);
        assertEquals(actualAvailableMove, game.availableMoves(0));
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
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(2, 5, 7, 9, 10, 12)));
        assertTrue(game.gameIsOver());

        aBoard = new ArrayList<>(
                Arrays.asList(  1, 2, 3, 4,
                                3, 4, 1, 2,
                                4, 1, 2, 1,
                                2, 3, 4, 1)
        );
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(2, 5, 7, 9, 10, 12)));
        assertFalse(game.gameIsOver());

        aBoard = new ArrayList<>(
                Arrays.asList(  1, 2, 3, 4,
                                3, 4, 1, 2,
                                4, 1, 2, 0,
                                2, 3, 4, 1)
        );
        game = new SudokuLogic(aBoard, new HashSet<>(Arrays.asList(2, 5, 7, 9, 10, 12)));
        assertFalse(game.gameIsOver());
        game.makeMove(11, 2);
        assertFalse(game.gameIsOver());
        game.undoMove(11);
        game.makeMove(11, 3);
        assertTrue(game.gameIsOver());
    }

}
