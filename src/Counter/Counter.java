package Counter;

public interface Counter {

    /**
     * Getter για την ώρα στην επιθυμητή μορφή
     * @return την ώρα
     */
    String getTime();

    /**
     * Αυξάνει κατά ένα το πεδίο με την μικρότερη σημαντικότητα
     */
    void increase();

    /**
     * Μειώνει κατά ένα το πεδίο με την μικρότερη σημαντικότητα
     */
    void decrease();

    /**
     * Μας λέει όταν ο χρόνος τελείωσε, δηλαδή η ώρα είναι 0
     * @return true αν η ώρα είναι 0
     */
    boolean isOver();

    /**
     * Μας λέει πότε ώρα του αντικειμένου είναι μικρότερη από κάποιο όριο
     * @param m λεπτά ορίου
     * @param s δευτερόλεπρα ορίου
     * @return true αν η ώρα είναι μικρότερη απο m:s
     */
    boolean isLowerThan(int m, int s);
}
