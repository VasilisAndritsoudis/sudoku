package GameLogic;
import SudokuGame.KillerSudoku;
import SudokuGame.ColoredArea;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Μια κλάση που αναπαριστά τη λογική παιχνιδιού ενός Sudoku Killer
 */
public class KillerSudokuLogic extends SudokuLogic {

    private HashSet<ColoredArea> redSums;   //Αποθηκεύει τα coloredAreas που πρέπει να κοκκινήσουμε
    /*
    Κοκκινισμένα coloredAreas πρεπει να είναι εκείνα που:
    1) είναι συμπληρωμένα αλλά με actualSum != characteristicSum
    2) το actualSum ξεπερνάει το characteristicSum
     */

    /**
     * Ένας κατασκευαστής που αρχικοποιεί το παιχνίδι. Φορτώνει:
     * α) το board
     * β) τα redPositions που πιθανόν να υπάρχουν στο αρχικό ταμπλό
     * γ) τα redAreas που πιθανόν να υπάρχουν στο αρχικό ταμπλό
     * @param startingBoard ένα αρχικό ταμπλό
     * @param coloredAreas ένα σύνολο από coloredAreas που οριοθετούν το παιχνίδι
     */
    public KillerSudokuLogic(ArrayList<Integer> startingBoard, ArrayList<ColoredArea> coloredAreas) {
        super();
        game = new KillerSudoku(startingBoard, coloredAreas);
        loadRedPositions(startingBoard);
        redSums = new HashSet<>();
        loadRedSums(startingBoard);
    }

    /**
     * Μια μέθοδος που μας δείχνει το σύνολο των redPositions που θα προκύψουν στην coloredAres αν βάλουμε το
     * number στο position. Χρησιμοποιείται στην getConflicts όπου θα ομαδοποιηθεί με τα redPositions που θα προκύψουν
     * σε γραμμές στήλες και τετράγωνα.
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν στην coloredArea, αν βάλουμε το number στη position
     */
    private HashSet<Integer> redInArea(int position, int number){
        HashSet<Integer> red = new HashSet<>();
        ColoredArea thisArea = areaWithPosition(position);

        for (int testPosition: thisArea.getPositions()){
            if (game.getValueAt(testPosition) == number && testPosition != position)
                red.add(testPosition);
        }
        return red;
    }

    /**
     * Συνδιάζει τη super.getConflicts με την redInArea. Μας δείνει το σύνολο των redPositions που θα προκύψουν στο
     * ταμπλό αν βάλουμε το number στη position.
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return το σύνολο των κόκκινων positions που θα προκύψουν σε όλο το board, αν βάλουμε το number στη position
     */
    @Override
    public HashSet<Integer> getConflicts(int position, int number) {
        HashSet<Integer> conflicts = super.getConflicts(position, number);
        conflicts.addAll(redInArea(position, number));
        return conflicts;
    }

    /**
     * Προσομοιώνεται μια κίνηση του παιχνιδιού. Αυτό που προσθέτεται στη λειτουργία του makeMove του SudokuLogic
     * είναι ότι προσθέτουμε τα redSums που προέκυψαν από τη κίνηση αυτή
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     */
    @Override
    public void makeMove(int position, int number){
        super.makeMove(position, number);
        //Μεχρι τώρα έχουν ανανεωθει το πεδίο των redPositions
        //Τώρα θέλω να ανανεώσω και το redSums
        ColoredArea thisArea = areaWithPosition(position);
        if (createsRedSums(position, number))
            redSums.add(thisArea);

    }


    /**
     * Προσομειώνει την αφαίρεση του αριθμού απο ένα position. Σε σχέση με την undoMove της SudokuLogic, αφαιρούμε
     * και τα redSums που πιθανόν να μην υπάρχουν πια λόγο της απομάκρινσης αυτής της κίνησης.
     * @param position μια θέση του game board
     */
    @Override
    public void undoMove(int position){
        super.undoMove(position);
        //Μέχρι τώρα έχω "καθαρίσε" τα redPositions
        //Τώρα θα καθαρίσω και τα redSums
        //Αν τώρα, με την αφαίρεση του number στη position το actualSum είναι μικρότερο (ΟΧΙ ΙΣΟ) του characteristic
        //μπορώ να αφαιρέσω το area απο τα redSums επειδή:
        //α) αν αφαιρεθει, το board δε θα έιναι πλεον full και το παιχνίδι δε γίνεται να τελειώσει
        //β) η περιοχή δεν έχει πλέον "ακατάλληλο" άθροισμα

        for (Iterator<ColoredArea> it = redSums.iterator(); it.hasNext();){
            ColoredArea x = it.next();
            if (x.contains(position) && sumInArea(x) < x.getCharacteristicSum())
                it.remove();
        }
    }

    /**
     * Το παιχνίδι έχει τελειώσει όταν 1) το ταμπλό είναι γεμάτο, 2) δεν υπάρχουν κόκκινα positions (δλδ το
     * redPositions θα είναι άδειο) και 3) δεν υπάρχουν redSums
     * @return true αν το παιχνίδι έχει τελειώσει.
     */
    @Override
    public boolean gameIsOver() {
        if (super.gameIsOver())     // if there are no redPositions AND the board is full
            if (redSums.isEmpty())  //AND there are no redAreas
                return true;
        return false;
    }

    /**
     * Αντίστοιχα με την getRedPositions()
     * Θέλουμε μια συνάρτηση που να τη καλούμε και να ξέρουμε ποια positions θα κοκκινίνουμε
     * λόγω redSum
     * @return ΟΛΑ τα positions τα οποία ανήκουν σε κάποιο area που ανήκει στα redSums (αν δεν υπάρχουν επιστρέφει κενο ΟΧΙ Null)
     */
    public HashSet<Integer> getRedSums(){
        HashSet<Integer> positionsInRedSums = new HashSet<>();
        for (ColoredArea x : redSums){
            HashSet<Integer> thesePositions = x.getPositions();
            positionsInRedSums.addAll(thesePositions);
        }
        return positionsInRedSums;
    }

    /**
     * Θέλουμε όλες τους αριθμούς που μπορούμε να βάλουμε σε ένα position ώστε να μη προκύψει κανένα redPosition ούτε
     * redSum. Έτσι, από όλα τα νούμερα για τα οποία δε προκύπτει redPosition αφαιρούμε τα νούμερα για τα οποία
     * προκύπτει redSum
     * @param position μια θέση του game board
     * @return όλους τους αριθμούς που μπορούν να μπουν στη position
     */
    @Override
    public ArrayList<Integer> availableMoves(int position){
        ArrayList<Integer> available = super.availableMoves(position);

        for (Iterator<Integer> it = available.iterator(); it.hasNext();){
            int x = it.next();
            if (createsRedSums(position, x))
                it.remove();
        }
        return available;
    }

    /**
     * Μια μέθοδος που μας λέει τι χρώμα πρέπει να έχει ένα συγκεκριμενο position
     * βάσει του ColoredArea του
     * @param position μια θέση του ταμπλό
     * @return το χρώμα της θέσης
     */
    public Color getColorOfPosition(int position){
        ColoredArea x = areaWithPosition(position);
        int index = ((KillerSudoku) game).getColoredAreas().indexOf(x);
        return ((KillerSudoku) game).getColoredAreas().get(index).getColor();
    }

    /**
     * Getter για τα areas του game
     * @return τα ColoredAreas του παιχνιδιού
     */
    public ArrayList<ColoredArea> getColoredAreas(){
        return ((KillerSudoku) game).getColoredAreas();
    }


    /**
     * Θέλουμε μια μεθοδο που να μας λέει σε ποιά coloredArea ανήκει η position
     * @param position μια θέση του game board
     * @return το coloredArea στο οποίο ανήκει η position
     */
    private ColoredArea areaWithPosition(int position){
        ArrayList<ColoredArea> areas = ((KillerSudoku) game).getColoredAreas();

        for (ColoredArea x : areas){
            if (x.contains(position))
                return x;
        }
        return null;
    }

    /**
     * Η μεθοδος μας γυρνάει το actualSum που υπάρχει μέσα σε ένα coloredArea για να μπορέσουμε να το συγκρίνουμε με
     * το characteristicSum.
     * ActualSum είναι το άθροισμα των στοιχείων που υπάρχουν στο ταμπλό στη συγκεκριμένη coloredArea
     * @param anArea μία coloredArea
     * @return το ActualSum της περιοχής
     */
    private int sumInArea(ColoredArea anArea){
        HashSet<Integer> thesePositions = anArea.getPositions();

        int res = 0;
        for (int x : thesePositions)
            res += game.getValueAt(x);
        return res;
    }

    /**
     * Mia μέθοδος αντίστοιχη της getConflicts. Μας λέει αν θα προκύψει redSum, αν θεωρητικά τοποθετήσουμε το
     * number στο position. ΔΕΝ τοποθετεί τον αριθμό. Μας λέει μόνο αν ΘΑ προκύψει redSum
     * @param position μια θέση του game board
     * @param number ένας αριθμός που μπορεί να μπει στη θέση
     * @return true αν θα προκύψει redSum τοποθετώντας το number στη position
     */
    private boolean createsRedSums(int position, int number){
        if (number == 0)
            return false;

        ColoredArea thisArea = areaWithPosition(position);
        HashSet<Integer> thesePositions = thisArea.getPositions();

        int actualSum = 0;
        for (int x : thesePositions)
            if (x != position)
                actualSum += game.getValueAt(x);

        int theoreticalSum = actualSum + number;

        if (theoreticalSum > thisArea.getCharacteristicSum())
            return true;
        else {
            int emptyPositions = 0;
            for (int x : thesePositions) {
                if (game.getValueAt(x) == 0)
                    emptyPositions++;
            }
            if (game.getValueAt(position) != 0)
                emptyPositions++;

            if (theoreticalSum == thisArea.getCharacteristicSum() && emptyPositions != 1)
                return true;
            return theoreticalSum < thisArea.getCharacteristicSum() && emptyPositions == 1;
        }
    }

    /**
     * Αντίστοιχα με την loadRedPositions. Ο κατασκευαστής πρεπει να αρχικοποιεί το πεδίο redSums με τα όλα τα redSums
     * που υπάρχουν στον ταμπλό αλλά δεν είναι αποθηκευμένα στο πεδίο.
     * @param startingBoard ένα αρχικό ταμπλό παιχνιδιού
     */
    private void loadRedSums(ArrayList<Integer> startingBoard){
        for (int i=0; i<startingBoard.size(); i++)
            if (createsRedSums(i, startingBoard.get(i)))
                redSums.add(areaWithPosition(i));
    }
}
