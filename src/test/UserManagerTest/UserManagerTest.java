package test.UserManagerTest;

import SudokuGame.ColoredArea;
import User.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Τhese tests test classes:
 *     -User
 *     -UserDataManager
 *     -FileManager
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserManagerTest
{
    private static User currentUser;

    UserManagerTest(){

    }

    @Test
    @Order(1)
    public void userRegisterTest(){
        currentUser = new User("Vasilis");
        currentUser.saveData();

        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0, 0, 0, 2, 6, 0, 7, 0, 1, 6, 8, 0, 0, 7, 0, 0, 9, 0, 1, 9, 0, 0, 0, 4, 5, 0, 0, 8, 2, 0, 1, 0, 0, 0, 4, 0, 0, 0, 4, 6, 0, 2, 9, 0, 0, 0, 5, 0, 0, 0, 3, 0, 2, 8, 0, 0, 9, 3, 0, 0, 0, 7, 4, 0, 4, 0, 0, 5, 0, 0, 3, 6, 7, 0, 3, 0, 1, 8, 0, 0, 0));

        assertEquals(currentUser.getUsername(), "Vasilis");
        assertEquals(currentUser.getWins(), 0, 0.001);
        assertEquals(currentUser.getLosses(), 0, 0.001);
        assertEquals(currentUser.getDraws(), 0, 0.001);
        assertEquals(currentUser.getSudokuGamesPlayed(), 0, 0.001);
        assertEquals(currentUser.getKillerGamesPlayed(), 0, 0.001);
        assertEquals(result, currentUser.getSudokuBoard());
        HashSet<Integer> greyResult = new HashSet<>(Arrays.asList(3, 4, 6, 8, 9, 10, 13, 16, 18, 19, 23, 24, 27, 28, 30, 34, 38, 39, 41, 42, 46, 50, 52, 53, 56, 57, 61, 62, 64, 67, 70, 71, 72, 74, 76, 77));
        assertEquals(greyResult, currentUser.getSudokuGreySpots());
        result = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        assertEquals(result, currentUser.getKillerBoard());
    }

    @Test
    @Order(2)
    public void userLoginTest(){
        currentUser = new User("JoLo");

        currentUser.addSudokuGamePlayed();
        currentUser.addKillerGamePlayed();
        currentUser.addWin();
        currentUser.addDraw();
        currentUser.addDraw();

        currentUser.saveData();

        // Login to get the data

        currentUser = new User("JoLo");

        assertTrue(currentUser.login());
        assertEquals(currentUser.getUsername(), "JoLo");
        assertEquals(currentUser.getWins(), 1, 0.001);
        assertEquals(currentUser.getLosses(), 0, 0.001);
        assertEquals(currentUser.getDraws(), 2, 0.001);
        assertEquals(currentUser.getSudokuGamesPlayed(), 1, 0.001);
        assertEquals(currentUser.getKillerGamesPlayed(), 1, 0.001);
    }

    @Test
    @Order(3)
    public void sudokuSimulationTest(){
        currentUser = new User("Vasilis");

        assertTrue(currentUser.login());

        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0, 0, 0, 2, 6, 0, 7, 0, 1, 6, 8, 0, 0, 7, 0, 0, 9, 0, 1, 9, 0, 0, 0, 4, 5, 0, 0, 8, 2, 0, 1, 0, 0, 0, 4, 0, 0, 0, 4, 6, 0, 2, 9, 0, 0, 0, 5, 0, 0, 0, 3, 0, 2, 8, 0, 0, 9, 3, 0, 0, 0, 7, 4, 0, 4, 0, 0, 5, 0, 0, 3, 6, 7, 0, 3, 0, 1, 8, 0, 0, 0));

        HashSet<Integer> greyResult = new HashSet<>(Arrays.asList(3, 4, 6, 8, 9, 10, 13, 16, 18, 19, 23, 24, 27, 28, 30, 34, 38, 39, 41, 42, 46, 50, 52, 53, 56, 57, 61, 62, 64, 67, 70, 71, 72, 74, 76, 77));

        assertEquals(result, currentUser.getSudokuBoard());
        assertEquals(greyResult, currentUser.getSudokuGreySpots());

        currentUser.addSudokuGamePlayed();
        currentUser.saveData();
        assertTrue(currentUser.login());

        result = new ArrayList<>(Arrays.asList(1, 0, 0, 4, 8, 9, 0, 0, 6, 7, 3, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 2, 9, 5, 0, 0, 7, 1, 2, 0, 6, 0, 0, 5, 0, 0, 7, 0, 3, 0, 0, 8, 0, 0, 6, 0, 9, 5, 7, 0, 0, 9, 1, 4, 6, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 3, 7, 8, 0, 0, 5, 1, 2, 0, 0, 4));

        greyResult = new HashSet<>(Arrays.asList(0, 3, 4, 5, 8, 9, 10, 16, 23, 24, 25, 26, 29, 30, 31, 33, 36, 39, 41, 44, 47, 49, 50, 51, 54, 55, 56, 57, 64, 70, 71, 72, 75, 76, 77, 80));

        assertEquals(result, currentUser.getSudokuBoard());
        assertEquals(greyResult, currentUser.getSudokuGreySpots());
        assertEquals(currentUser.getSudokuGamesPlayed(), 1, 0.001);

        result = new ArrayList<>();
        result.add(3);
        result.add(6);
        result.add(9);

        currentUser.setSudokuBoard(result);
        currentUser.saveData();

        assertEquals(result, currentUser.getSudokuBoard());
        assertEquals(greyResult, currentUser.getSudokuGreySpots());

        currentUser = new User("Vasilis");
        currentUser.login();
        assertEquals(result, currentUser.getSudokuBoard());

        currentUser.addSudokuGamePlayed();

        result = new ArrayList<>(Arrays.asList(2, 0, 0, 3, 0, 0, 0, 0, 0, 8, 0, 4, 0, 6, 2, 0, 0, 3, 0, 1, 3, 8, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 3, 9, 0, 5, 0, 7, 0, 0, 0, 6, 2, 1, 0, 3, 2, 0, 0, 6, 0, 0, 0, 0, 2, 0, 0, 0, 9, 1, 4, 0, 6, 0, 1, 2, 5, 0, 8, 0, 9, 0, 0, 0, 0, 0, 1, 0, 0, 2));

        greyResult = new HashSet<>(Arrays.asList(0, 3, 9, 11, 13, 14, 17, 19, 20, 21, 24, 31, 33, 34, 36, 38, 42, 43, 44, 46, 47, 50, 55, 59, 60, 61, 63, 65, 66, 67, 69, 71, 77, 80));

        currentUser.saveData();

        assertEquals(result, currentUser.getSudokuBoard());
        assertEquals(greyResult, currentUser.getSudokuGreySpots());
    }

    @Order(4)
    @Test
    public void killerSimulationTest(){
        currentUser = new User("Vasilis");

        assertTrue(currentUser.login());

        ArrayList<ColoredArea> coloredAreaList = new ArrayList<>();

        Color red = new Color(162,250,163);
        Color yellow = new Color(255,255,255);
        Color blue = new Color(79,117,155);
        Color green = new Color(190,55,116);
        Color orange = new Color(93,81,121);

        HashSet<Integer> positions = new HashSet<>();
        positions.add(0);
        positions.add(9);
        positions.add(10);
        positions.add(18);
        positions.add(19);
        positions.add(27);
        coloredAreaList.add(new ColoredArea(positions, red, 29));

        positions = new HashSet<>();
        positions.add(1);
        positions.add(2);
        positions.add(3);
        coloredAreaList.add(new ColoredArea(positions, blue, 16));

        positions = new HashSet<>();
        positions.add(4);
        positions.add(13);
        coloredAreaList.add(new ColoredArea(positions, yellow, 8));

        positions = new HashSet<>();
        positions.add(5);
        positions.add(6);
        positions.add(7);
        coloredAreaList.add(new ColoredArea(positions, blue, 12));

        positions = new HashSet<>();
        positions.add(8);
        positions.add(16);
        positions.add(17);
        positions.add(25);
        positions.add(26);
        positions.add(35);
        coloredAreaList.add(new ColoredArea(positions, green, 32));

        positions = new HashSet<>();
        positions.add(11);
        positions.add(12);
        positions.add(21);
        positions.add(30);
        coloredAreaList.add(new ColoredArea(positions, green, 24));

        positions = new HashSet<>();
        positions.add(14);
        positions.add(15);
        positions.add(23);
        positions.add(32);
        coloredAreaList.add(new ColoredArea(positions, red, 14));

        positions = new HashSet<>();
        positions.add(20);
        positions.add(29);
        coloredAreaList.add(new ColoredArea(positions, blue, 10));

        positions = new HashSet<>();
        positions.add(22);
        positions.add(31);
        positions.add(40);
        coloredAreaList.add(new ColoredArea(positions, orange, 21));

        positions = new HashSet<>();
        positions.add(24);
        positions.add(33);
        coloredAreaList.add(new ColoredArea(positions, orange, 3));

        positions = new HashSet<>();
        positions.add(28);
        positions.add(37);
        coloredAreaList.add(new ColoredArea(positions, yellow, 15));

        positions = new HashSet<>();
        positions.add(34);
        positions.add(43);
        coloredAreaList.add(new ColoredArea(positions, blue, 10));

        positions = new HashSet<>();
        positions.add(36);
        positions.add(45);
        coloredAreaList.add(new ColoredArea(positions, green, 6));

        positions = new HashSet<>();
        positions.add(38);
        positions.add(39);
        coloredAreaList.add(new ColoredArea(positions, red, 4));

        positions = new HashSet<>();
        positions.add(41);
        positions.add(42);
        coloredAreaList.add(new ColoredArea(positions, yellow, 13));

        positions = new HashSet<>();
        positions.add(44);
        positions.add(53);
        coloredAreaList.add(new ColoredArea(positions, yellow, 12));

        positions = new HashSet<>();
        positions.add(46);
        positions.add(47);
        positions.add(48);
        coloredAreaList.add(new ColoredArea(positions, orange, 19));

        positions = new HashSet<>();
        positions.add(49);
        positions.add(57);
        positions.add(58);
        positions.add(59);
        coloredAreaList.add(new ColoredArea(positions, green, 25));

        positions = new HashSet<>();
        positions.add(50);
        positions.add(51);
        positions.add(52);
        coloredAreaList.add(new ColoredArea(positions, orange, 17));

        positions = new HashSet<>();
        positions.add(54);
        positions.add(63);
        positions.add(72);
        positions.add(73);
        positions.add(74);
        coloredAreaList.add(new ColoredArea(positions, blue, 27));

        positions = new HashSet<>();
        positions.add(55);
        positions.add(64);
        coloredAreaList.add(new ColoredArea(positions, green, 3));

        positions = new HashSet<>();
        positions.add(56);
        positions.add(65);
        coloredAreaList.add(new ColoredArea(positions, red, 15));

        positions = new HashSet<>();
        positions.add(60);
        positions.add(69);
        coloredAreaList.add(new ColoredArea(positions, blue, 11));

        positions = new HashSet<>();
        positions.add(61);
        positions.add(70);
        coloredAreaList.add(new ColoredArea(positions, green, 7));

        positions = new HashSet<>();
        positions.add(62);
        positions.add(71);
        positions.add(80);
        positions.add(79);
        positions.add(78);
        coloredAreaList.add(new ColoredArea(positions, red, 27));

        positions = new HashSet<>();
        positions.add(66);
        positions.add(67);
        positions.add(68);
        positions.add(75);
        positions.add(76);
        positions.add(77);
        coloredAreaList.add(new ColoredArea(positions, orange, 25));

        for(int i = 0; i < coloredAreaList.size(); i++){
            assertEquals(coloredAreaList.get(i).getPositions(), currentUser.getKillerColoredAreas().get(i).getPositions());
            assertEquals(coloredAreaList.get(i).getCharacteristicSum(), currentUser.getKillerColoredAreas().get(i).getCharacteristicSum());
            assertEquals(coloredAreaList.get(i).getColor(), currentUser.getKillerColoredAreas().get(i).getColor());
        }
    }
}
