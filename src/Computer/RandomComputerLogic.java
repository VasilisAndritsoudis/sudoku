package Computer;

import GameLogic.SudokuLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Mia κλάση που υλοποιεί το ComputerLogic ώστε ο υπολογιστής
 * να εκτελεί τυχαίες κινήσεις
 */
public class RandomComputerLogic implements ComputerLogic {

    private int computerPosition;
    private int computerValue;

    /**
     * Ο κατασκευαστής που βρίσκει ένα τυχαίο άδειο position του πίνακα και
     * βάμια τιμή που μπορεί να μπει στη θέση αυτή
     * @param game ένα παιχνίδι Sudoku με κινήσεις πάνω
     */
    public RandomComputerLogic(SudokuLogic game){
        Random rand = new Random();

        ArrayList<Integer> temp = game.emptyPositions();
        int pos = temp.get(rand.nextInt(temp.size()));
        while (game.availableMoves(pos).isEmpty())
            pos = temp.get(rand.nextInt(temp.size()));
        computerPosition = pos;

        temp = game.availableMoves(pos);
        computerValue = temp.get(rand.nextInt(temp.size()));

    }

    @Override
    public int getPosition() {
        return computerPosition;
    }

    @Override
    public int getValue() {
        return computerValue;
    }
}
