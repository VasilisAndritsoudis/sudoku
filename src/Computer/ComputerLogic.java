package Computer;

/**
 * Ένα interface που περιέχει μεθόδους σχετικές με τις κινήσεις του υπολογιστή
 * σε ένα παιχνίδι Duidoku
 */
public interface ComputerLogic {
    /**
     * Θέλουμε τη θέση στην οποίο ο υπολογιστής θα τοποθετήσει τον αριθμό
     * @return τη θέση
     */
    int getPosition();

    /**
     * Θέλουμε τον αριθμό που θα τοποθετήσει ο υπολογιστής στη συγκεκριμένη θέση
     * @return τον αριθμό που θα βάλουμε στο Board
     */
    int getValue();
}
