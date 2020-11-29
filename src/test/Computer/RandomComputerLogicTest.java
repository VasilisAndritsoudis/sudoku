package test.Computer;

import Computer.ComputerLogic;
import Computer.RandomComputerLogic;
import GameLogic.SudokuLogic;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RandomComputerLogicTest {

    private ComputerLogic test;
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
    public void findRandomEmptyPositionTest(){
        for (int i=0; i<10; i++) {
            test = new RandomComputerLogic(game);
            assertNotEquals(2, test.getPosition());
            assertNotEquals(5, test.getPosition());
            assertNotEquals(7, test.getPosition());
            assertNotEquals(9, test.getPosition());
            assertNotEquals(10, test.getPosition());
            assertNotEquals(12, test.getPosition());
        }
    }

    @Test
    public void generateNumberTest(){
        for (int i=0; i<10; i++) {
            test = new RandomComputerLogic(game);
            if (test.getPosition() == 0) {
                assertNotEquals(2, test.getValue());
                assertNotEquals(3, test.getValue());
                assertNotEquals(4, test.getValue());
            } else if (test.getPosition() == 1) {
                assertNotEquals(1, test.getValue());
                assertNotEquals(3, test.getValue());
                assertNotEquals(4, test.getValue());
            } else if (test.getPosition() == 3) {
                assertNotEquals(2, test.getValue());
                assertNotEquals(3, test.getValue());
            } else if (test.getPosition() == 4) {
                assertNotEquals(2, test.getValue());
                assertNotEquals(4, test.getValue());
            } else if (test.getPosition() == 6) {
                assertNotEquals(2, test.getValue());
                assertNotEquals(3, test.getValue());
                assertNotEquals(4, test.getValue());
            }
        }
    }
}
