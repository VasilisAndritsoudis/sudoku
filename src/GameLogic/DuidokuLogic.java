package GameLogic;

import Computer.ComputerLogic;
import Computer.RandomComputerLogic;
import SudokuGame.Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A class that implements Duidoku logic
 */
public class DuidokuLogic extends SudokuLogic
{
    /**
     * The constructor has to initialize the game-board to an empty (only zeros) ArrayList of a specific size
     * size will always be 4 in our game, but more game options can be easily added later.
     * @param size the dimension of the board
     */
    public DuidokuLogic(int size){
        super();
        game = new Sudoku(size);
    }

    /**
     * Simulates a move.
     * If the 'value' at position 'index' doesn't cause any conflicts and the position 'index' is empty, then place 'value' at 'index'
     * Also, completes the board with -1 in every gray spot
     * @param index a position at the board
     * @param value a number that can be placed at the board
     */
    @Override
    public void makeMove(int index, int value){
        game.put(index, value);
        fillWithUnavailableSpots();
    }

    /**
     * This method implements a computer move.
     * The strategy of the computer depends on the difficulty.
     * @return the position in which the computer make the move
     */
    public int makeComputerMove(){
        ComputerLogic simulator = getDifficulty(this);

        game.put(simulator.getPosition(), simulator.getValue());
        fillWithUnavailableSpots();
        return simulator.getPosition();
    }

    /**
     * A method that can be overwritten in order to set a different difficulty level of the
     * computer moves
     * @param game the game in specific time
     * @return a RandomComputerLogic object;
     */
    protected ComputerLogic getDifficulty(SudokuLogic game){
        return new RandomComputerLogic(game);
    }

    /**
     * A method that fills the graySpots field with the positions that are gray
     * Also, puts -1 in these positions
     */
    private void fillWithUnavailableSpots(){
        for(int i = 0; i < game.getSize(); i++)
            if (game.getValueAt(i) == 0)
                if (availableMoves(i).isEmpty()) {
                    greySpots.add(i);
                    game.put(i, -1);
                }
    }
}
