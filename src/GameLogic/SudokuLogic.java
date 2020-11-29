package GameLogic;
import SudokuGame.Sudoku; //ίσως χρειάζεται import SudokuGame.*
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Math;
import java.util.Iterator;

/**
 * Η κλάση που υλοποιεί γενικά τους κανονισμούς του Sudoku
 */
public class SudokuLogic {

    Sudoku game;
    private HashSet<Pair<Integer, Integer>> redPositions; //Pairs always saved as (max, min)
    HashSet<Integer> greySpots;

    /**
     * Ένας κατασκευαστής που αρχικοποιεί μόνο τα redPositions
     * Χρησιμοποιείται στις υποκλάσεις ώστε να μπορει να αρχικοποιηθεί το game ως άλλο είδος Sudoku
     */
    SudokuLogic(){  //Υπάρχει για να χρησιμοποιείτε απο τις υποκλάσεις
        redPositions = new HashSet<>();
        greySpots = new HashSet<>();
    }

    /**
     * Ο καρακευαστής που χρησιμοποιείται για το κανονικό Sudoku
     * Αρχικοποιεί και τα redPosition αν υπάρχουν, αλλιώς σε ένα κενό HashSet
     * @param startingBoard ένα παιχνίδι Sudoku στο οποίο θέλουμε να αριχοποιηθεί.
     * @param greySpots οι θέσεις του ταμπλό που αποτελούν αρχικά δεδομένα
     */
    public SudokuLogic(ArrayList<Integer> startingBoard, HashSet<Integer> greySpots){
        game = new Sudoku(startingBoard);
        redPositions = new HashSet<>();
        loadRedPositions(startingBoard);
        this.greySpots = greySpots;
    }

    /**
     * Μια από τις συναρτήσεις που χρησιμοποιεί η getConflicts και σχετίζεται με τις γραμμές
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν στην ίδια γραμμ,ή αν βάλουμε το number στη position
     */
    private HashSet<Integer> redInRow(int position, int number){
        int size = (int) Math.sqrt(game.getSize());
        int initial = size * (position / size);
        HashSet<Integer> red = new HashSet<>();

        if (number != 0)
            for (int testPosition=initial; testPosition<initial+size; testPosition++){
                if (game.getValueAt(testPosition) == number && testPosition != position){
                    red.add(testPosition);
                }
            }
        return red;
    }

    /**
     * Μια από τις συναρτήσεις που χρησιμοποιεί η getConflicts και σχετίζεται με τις στήλες
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν στην ίδια στήλη, αν βάλουμε το number στη position
     */
    private HashSet<Integer> redInColumn(int position, int number){
        int size = (int) Math.sqrt(game.getSize());
        int initial = position % size;
        HashSet<Integer> red = new HashSet<>();

        if (number != 0)
            for (int testPosition=initial; testPosition<size*size; testPosition+=size){
                if (game.getValueAt(testPosition) == number && testPosition != position){
                    red.add(testPosition);
                }
            }
        return red;
    }

    /**
     * Μια από τις συναρτήσεις που χρησιμοποιεί η getConflicts και σχετίζεται με τα τετράγωνα
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν στο ίδιο τετράγωνο, αν βάλουμε το number στη position
     */
    private HashSet<Integer> redInSquare(int position, int number){
        int size = (int) Math.sqrt(game.getSize());
        int squareSize = (int) Math.sqrt(size);
        int initial = findInitialOfSquare(position);
        HashSet<Integer> red = new HashSet<>();

        if (number != 0) {
            int testPosition = initial;
            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    if (game.getValueAt(testPosition) == number && testPosition != position) {
                        red.add(testPosition);
                    }
                    testPosition++;
                }
                testPosition += size - squareSize;
            }
        }
        return red;
    }

    /**
     * Ο συνδιασμός των τρειών παραπάνω.
     * Θέλουμε να γνωρίζουμε ποιά redPositions θα προκύψουν αν τοποθετήσουμε θεωρητικά το number στη position
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν σε όλο το board, αν βάλουμε το number στη position
     */
    protected HashSet<Integer> getConflicts(int position, int number){
        HashSet<Integer> conflicts = new HashSet<>();

        conflicts.addAll(redInRow(position, number));
        conflicts.addAll(redInColumn(position, number));
        conflicts.addAll(redInSquare(position, number));
        return conflicts;
    }

    /**
     * Το παιχνίδι έχει τελειώσει όταν 1) το ταμπλό είναι γεμάτο και 2) δεν υπάρχουν κόκκινα positions (δλδ το
     * redPositions θα είναι άδειο)
     * @return true αν το παιχνίδι έχει τελειώσει
     */
    public boolean gameIsOver(){
        if (!redPositions.isEmpty())                    //If there are no redPositions
            return false;
        ArrayList<Integer> board = game.getBoard();     //AND
        for (Integer number : board) {                  //The board in full
            if (number == 0)
                return false;
        }
        return true;
    }

    /**
     * Χρειάζεται μία συνάρτηση που ανα πάσα στιγμή θα μας δίνει το σύνολο των redPositions αλλά όχι ως Pair
     * Χρειάζονται τα μεμονομένα positions ώστε να τα κοκκινίσουμε στο GUI
     * @return το σύνολο των μεμονομένων positions που πρέπει να κοκκινιστούν
     */
    public HashSet<Integer> getRedPositions(){
        HashSet<Integer> uniqueRedPositions = new HashSet<>();
        for (Pair<Integer, Integer> x : redPositions){
            uniqueRedPositions.add(x.getValue());
            uniqueRedPositions.add(x.getKey());
        }
        return uniqueRedPositions;
    }

    /**
     * Ένας απλός getter για τα graySpots.
     * Τα positions του ταμπλό τα οποίο αποτελούν τα αρχικά δεδομένα του παζλ και δεν
     * επιτρέπεται να τα πειράξει ο χρήστης.
     * @return το πεδίο graySpots
     */
    public HashSet<Integer> getGreySpots() {
        return greySpots;
    }

    /**
     * Η μέθοδος αυτή προσομοιώνει μία κίνηση στο παιχνίδι. Βάζουμε τον αριθμο number και θέση position ταμπλο
     * και ψάχνουμε να δούμε αν δημιουγήθηκαν κόκκινα positions και τα προσθέτουμε στο πεδίο redPositions
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     */
    public void makeMove(int position, int number){
        game.put(position, number);
        HashSet<Integer> conflicts = getConflicts(position, number);

        for (int x : conflicts) {
            if (x > number)
                redPositions.add(new Pair<>(x, position));
            else
                redPositions.add(new Pair<>(position, x));
        }
    }

    /**
     * Η μέθοδος προσομοιώνει τη αφαίρεση του αριθμού από μιά συγκεκριμένη position.
     * Αφαιρείται ο αριθμός απο τη θέση και
     * αφαιρούνται και redPositions που τυχόν προέκυπταν εξαιτίας της θέσης αυτής. Πλέον, η θέση είναι άδεια άρα δε
     * μπορεί να προκαλεί redPositions.
     * Η μεθοδος αυτή ειναι ο λόγος που τα redPositions είναι αποθηκευμένα ως Pair για να μπορούμε να αφαιρούμε πολυ
     * γρήγορα όλα τα Pair που περιέχουν τη θέση position, αλλα να παραμένουν άλλες θέσεις που είχαν pair και με την
     * position αλλά και με κάποια άλλη τρίτη θέση.
     * @param position μια θέση του game board
     */
    public void undoMove(int position){
        game.remove(position);

        for (Iterator<Pair<Integer, Integer>> it = redPositions.iterator(); it.hasNext(); ) {
            Pair<Integer, Integer> x = it.next();
            if (x.getKey() == position || x.getValue() == position)
                it.remove();
        }
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για το assist στο παιχνίδι. Δηλαδή μας δείχνει ποιές κινήσεις μπορούμε να κάνουμε
     * σε μία position ώστε να μη προκύψει κανένα redPosition. Η μέθοδος τοποθετεί θεωρητικα (με την getConflicts)
     * όλους τους δυνατούς αριθμούς στη position και κοιτάει αν θα προκύψουν conflicts/redPositions.
     * @param position μια θέση του game board
     * @return το σύνολο των αριθμών που μπορούν να μπουν στη θέση position ωστέ να μη προκύψουν redPositions
     */
    public ArrayList<Integer> availableMoves(int position){
        ArrayList<Integer> available = new ArrayList<>();

        int maxInput = (int) Math.sqrt(game.getSize());
        for (int i=1; i<=maxInput ; i++) {
            if (getConflicts(position, i).isEmpty()) {
                available.add(i);
            }
        }
        return available;
    }

    /**
     * Χρειαζόμαστε μια συνάρτηση που να μας λέει ποιες θέσεις του ταμπλό είναι άδειες ώστε όταν είναι η σειρά του
     * υπολογιστή να παίξει, να μπορεί να τοποθετήσει έναν αριθμο σε μία από αυτές τις θέσεις.
     * @return το σύνολο των θέσεων που έχουν 0 (δλδ δεν έχει βάλει κάτι ο παίκτης)
     */
    public ArrayList<Integer> emptyPositions(){
        int size = game.getSize();
        ArrayList<Integer> empties = new ArrayList<>();

        for (int i=0; i<size; i++)
            if (game.getValueAt(i) == 0)
                empties.add(i);

        return empties;
    }

    /**
     * Ένας getter για τον ταμπλό του παιχνιδιού
     * @return το ταμπλό
     */
    public ArrayList<Integer> getBoard(){
        return game.getBoard();
    }

    /**
     * Για την υλοποίηση της redInSquare χρειάζεται αν ξέρουμε ποιά είναι η πάνω αριστερά θέση του τετραγώνου στο οποίο
     * ανήκει η position ώστε να ξέρουμε από πού θα ξεκινήσουμε να ελέγχουμε τις θέσεις.
     * @param position μια θέση του board
     * @return την αρχική (πάνω αριστερά) θέση του τετραγώνου που ανήκει η position
     */
    private int findInitialOfSquare(int position){
        int size = (int) Math.sqrt(game.getSize());
        int squareSide = (int) Math.sqrt(size);

        return position - size * ((position/size) % squareSide) - position%squareSide;
    }

    /**
     * Στον κατασκευαστή θέλουμε να υπολογίσουμε και τυχον αρχικά redPositions που υπάρχου στο ταμπλό ωστέ να μπορούμε
     * να τα κοκκινίσουμε απο την αρχή του παιχνιδιού. Η μέθοδος αυτή βάζει στο πεδίο redPositions όλα τα Pair που
     * υπάρχουν στο ταμπλό αλλά δεν έχουν μπει ακόμα στο πεδίο
     * @param startingBoard ένα αρχικό ταμπλό παιχνιδιού
     */
    void loadRedPositions(ArrayList<Integer> startingBoard){
        for (int i=0; i<startingBoard.size(); i++){
            if (startingBoard.get(i) != 0) {
                HashSet<Integer> temp = getConflicts(i, startingBoard.get(i));

                for (int x : temp) {
                    if (i > x)
                        redPositions.add(new Pair<>(i, x));
                    else
                        redPositions.add(new Pair<>(x, i));
                }
            }
        }
    }
}
