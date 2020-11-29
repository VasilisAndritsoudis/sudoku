package GUI.GameGUI;

import GameLogic.KillerSudokuLogic;
import GameLogic.SudokuLogic;
import SudokuGame.ColoredArea;

import java.awt.*;
import java.util.*;

public class KillerGUI extends SudokuGUI {

    public KillerGUI(KillerSudokuLogic game, Locale locale){
        super(game, locale);
        setTitle("Killer Sudoku");

        for (ColoredArea x: game.getColoredAreas()){
            int firstPosition = Collections.min(x.getPositions());
            positions[firstPosition].setSum(x.getCharacteristicSum());
        }
    }
}




