package test.GameLogicTest;
import GameLogic.*;
import SudokuGame.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class DuidokuLogicTest
{
    private static DuidokuLogic duidokuGame;

    @BeforeAll
    public static void setUp(){
        duidokuGame = new DuidokuLogic(16);
    }

    @Test
    public void makeMoveTest(){
        ArrayList<Integer> expectedOutcome = new ArrayList<>(
                Arrays.asList(    0,  3, -1,  4,
                                  4,  2,  1,  3,
                                 -1,  1,  2, -1,
                                  3, -1,  4,  1));


        duidokuGame.makeMove(5, 2);
        duidokuGame.makeMove(3, 4);
        duidokuGame.makeMove(10, 2);
        duidokuGame.makeMove(1, 3);
        duidokuGame.makeMove(4, 4);
        duidokuGame.makeMove(7, 3);
        duidokuGame.makeMove(6, 1);
        duidokuGame.makeMove(14, 4);
        duidokuGame.makeMove(15, 1);
        duidokuGame.makeMove(9, 1);
        duidokuGame.makeMove(12, 3);
        assertEquals(expectedOutcome, duidokuGame.getBoard());
        assertFalse(duidokuGame.gameIsOver());
    }

    @Test
    public void makeComputerMoveTest(){
       assertEquals(0, duidokuGame.makeComputerMove());
       assertTrue(duidokuGame.gameIsOver());
       assertEquals(new HashSet<>( Arrays.asList(2, 8, 11, 13)), duidokuGame.getGreySpots());
    }
}
