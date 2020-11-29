package test.SudokuGameTest;

import SudokuGame.ColoredArea;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ColoredAreaTest {

    private ColoredArea anArea;

    @Before
    public void init(){
        anArea = new ColoredArea(new HashSet<>(Arrays.asList(1, 2, 3)), Color.BLACK, 13);
    }

    @Test
    public void containsTest(){
        assertTrue(anArea.contains(3));
        assertFalse(anArea.contains(0));
    }

    @Test
    public void getColorTest(){
        assertEquals(Color.BLACK, anArea.getColor());
    }

    @Test
    public void getPositionsTest(){
        assertEquals(new HashSet<>(new HashSet<>(Arrays.asList(3, 1, 2))), anArea.getPositions());
    }

}
