package GUI.GameGUI;

import Counter.*;
import GameLogic.DuidokuLogic;
import GameLogic.SudokuLogic;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class SudokuGUI extends JFrame {

    protected PositionButton[] positions;
    JButton[] choices;
    JToggleButton help;
    JToggleButton symbolSelect;
    int buttonOnUse;
    private boolean finished;
    JButton clear;
    DigitalClockTimer time;
    private Thread timer;


    private ResourceBundle resourceBundle;

    public SudokuGUI(SudokuLogic game, Locale locale){
        int boardSize = game.getBoard().size();
        int nofSquares = (int) Math.sqrt(boardSize);
        buttonOnUse = -1;
        finished = false;
        time = new DigitalClockTimer(20, 00);

        resourceBundle = ResourceBundle.getBundle("Bun", locale);

        // Listener του για μη αναμενόμενο κλείσιμο παραθύρου
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) { onClose(); }
        });

        // Φτιάχνω τα κουμπιά των επιλογών
        JPanel choicesPanel = new JPanel();
        choices = new JButton[nofSquares];
        for (int i=0; i<nofSquares; i++){
            choices[i] = new JButton();
            choices[i].setText(Integer.toString(i+1));
            choices[i].setActionCommand(Integer.toString(i+1));
            choices[i].setEnabled(false);

            //ΓΕΝΙΚΑ
            choices[i].setFont(new Font("Arial", Font.BOLD, 25));
            choices[i].setPreferredSize(new Dimension(55, 55));

            choicesPanel.add(choices[i]);

            choices[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int number = Integer.parseInt(actionEvent.getActionCommand());

                    // Αν το buttonOnUse είναι γραμμένο πρέπει πρώτα να το αδειάσω (και μετά να το ξαναγράψω)
                    if (positions[buttonOnUse].getValue(symbolSelect.isSelected()) != null)
                        undoMove(buttonOnUse, game, locale);

                    game.makeMove(buttonOnUse, number);
                    //Γράφω στο κουμπί buttonOnUse τον αριθμό που έβαλα δλδ το number
                    positions[buttonOnUse].setValue(number, symbolSelect.isSelected(), locale);

                    // Βάζω τα κόκκινα που δημιουργήθηκαν
                    for (int j=0; j<boardSize; j++)
                        positions[j].storeColor(j, game);

                    if (game.gameIsOver())
                        onWin(resourceBundle.getString("sudoku.complete"));

                }
            });
        }

        // -------------------------------------------------------------------------------------------------

        // Φτιάχνω το πάνελ με των ρυθμίσεων
        JPanel settings = new JPanel();
        settings.setLayout(new FlowLayout());

        // Φτιάχνω το countdown
        JLabel countdown = new JLabel();
        countdown.setFont(new Font("Arial", Font.BOLD, 15));
        countdown.setText(time.getTime());
        class TimeCountdown implements Runnable {
            public void run(){
                while (!time.isOver()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    time.decrease();
                    if (time.isLowerThan(0, 10))
                        countdown.setForeground(Color.RED);
                    countdown.setText(time.getTime());
                }
                if (time != null)
                    timeIsOver(game);
            }
        }
        timer = new Thread(new TimeCountdown());
        timer.start();
        settings.add(countdown);
        countdown.setHorizontalAlignment(SwingConstants.LEFT);

        // Φτιάχνω το κουμπί Back
        JButton back = new JButton();
        back.setText(resourceBundle.getString("sudoku.back"));
        back.setBackground(Color.orange);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onClose();
            }
        });
        settings.add(back);

        // Φτιάχνω το κουμπί του καθαρισμού
        clear = new JButton();
        clear.setText(resourceBundle.getString("sudoku.clear"));
        clear.setBackground(Color.cyan);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        resourceBundle.getString("sudoku.clearPrompt"),
                        "Suduku :|",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION){
                    if (buttonOnUse != -1)
                        positions[buttonOnUse].setBorder(UIManager.getBorder("Button.border"));
                    help.doClick();
                    buttonOnUse = -1;
                    help.doClick();
                    for (int i=0; i<boardSize; i++)
                        if (!game.getGreySpots().contains(i)) {
                            game.undoMove(i);
                            positions[i].setValue(0, symbolSelect.isSelected(), locale);
                        }
                    for (int i=0; i<boardSize; i++)
                        positions[i].storeColor(i, game);
                }

            }
        });
        settings.add(clear);

        // Φτιάχνω το κουμπί του help
        help = new JToggleButton();
        help.setText(resourceBundle.getString("sudoku.help.off"));
        help.setBackground(Color.pink);
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton temp = (AbstractButton) actionEvent.getSource();
                boolean selected = temp.getModel().isSelected();

                if (selected){
                    help.setText(resourceBundle.getString("sudoku.help.on"));
                    help.setBackground(Color.green);
                    if (buttonOnUse != -1) {
                        for (int j=0; j<nofSquares; j++)
                            if (game.availableMoves(buttonOnUse).contains(j+1)) {
                                choices[j].setEnabled(true);
                                choices[j].setForeground(null);
                            }
                            else {
                                choices[j].setEnabled(false);
                                choices[j].setUI(new MetalButtonUI(){
                                    protected Color getDisabledTextColor() {
                                        return new Color(174, 0, 16, 194);
                                    }
                                });;
                            }
                    }
                }
                else{
                    help.setText(resourceBundle.getString("sudoku.help.off"));
                    help.setBackground(Color.pink);
                    if (buttonOnUse != -1){
                        for (int j=0; j<nofSquares; j++) {
                            choices[j].setEnabled(true);
                            choices[j].setBackground(null);
                        }
                    }
                }
            }
        });
        help.setSize(help.getWidth(), help.getHeight());
        settings.add(help);

        // Φτιάχνω το κουμπί αλλαγής συμβόλων
        symbolSelect = new JToggleButton(resourceBundle.getString("sudoku.numeric"));
        symbolSelect.setBackground(Color.magenta);
        symbolSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton temp = (AbstractButton) actionEvent.getSource();
                boolean selected = temp.getModel().isSelected();

                if (selected){
                    symbolSelect.setText(resourceBundle.getString("sudoku.alphabetic"));
                    symbolSelect.setBackground(Color.gray);
                    for (int i=0; i<nofSquares; i++)
                        choices[i].setText(PositionButton.toAlphabetic(i+1, locale));
                }
                else{
                    symbolSelect.setText(resourceBundle.getString("sudoku.numeric"));
                    symbolSelect.setBackground(Color.magenta);
                    for (int i=0; i<nofSquares; i++)
                        choices[i].setText(Integer.toString(i+1));
                }
                for (int i=0; i<boardSize; i++)
                    positions[i].setValue(game.getBoard().get(i), selected, locale);
            }
        });
        symbolSelect.setSize(symbolSelect.getWidth(), symbolSelect.getHeight());
        settings.add(symbolSelect);

        // --------------------------------------------------------------------------------------------------

        // Φτιάχνω τα panels που αποτελούν τα τετράγωνα του Board
        JPanel[] squares = new JPanel[nofSquares];
        for (int i=0; i<nofSquares; i++) {
            squares[i] = new JPanel();
            squares[i].setLayout(new GridLayout((int) Math.sqrt(nofSquares), (int) Math.sqrt(nofSquares)));
            squares[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        }

        // Φτιάχνω τα κουμπιά του Board
        positions = new PositionButton[boardSize];
        for (int i=0; i<boardSize; i++){
            if (game instanceof DuidokuLogic)
                positions[i] = new PositionButton(game, i, false, 0, locale);
            else
                positions[i] = new PositionButton(game, i, game.getGreySpots().contains(i), game.getBoard().get(i), locale);

            // Square του Κουμπιού
            int temp = (int) Math.sqrt(nofSquares);
            temp = temp * (i/(temp*temp*temp)) + (i%(temp*temp)/temp);
            squares[temp].add(positions[i]);
            positions[i].setPreferredSize(new Dimension(60, 60));

            positions[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    //Θετω buttonOnUse = thisButton
                    if (buttonOnUse != -1)
                        positions[buttonOnUse].setBorder(BorderFactory.createBevelBorder(0));
                    buttonOnUse = Integer.parseInt(actionEvent.getActionCommand());
                    positions[buttonOnUse].setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

                    if (positions[buttonOnUse].getValue(symbolSelect.isSelected()) != null) //αν το κουμπί έχει αριθμό πάνω
                        undoMove(buttonOnUse, game, locale);

                    //Ενεργοποιώ τις επιλογές
                    if (help.isSelected()){
                        for (int j=0; j<nofSquares; j++)
                            if (game.availableMoves(buttonOnUse).contains(j+1)) {
                                choices[j].setEnabled(true);
                                choices[j].setForeground(null);
                            }
                            else {
                                choices[j].setEnabled(false);
                                choices[j].setUI(new MetalButtonUI(){
                                    protected Color getDisabledTextColor() {
                                        return new Color(174, 0, 16, 194);
                                    }
                                });
                            }
                    }
                    else
                        for (int j=0; j<nofSquares; j++) {
                            choices[j].setEnabled(true);
                            choices[j].setBackground(null);
                        }


                }
            });
        }

        // --------------------------------------------------------------------------------------------------

        // Φτιάχνω το πάνελ του board
        JPanel board = new JPanel();
        for (int i=0; i<nofSquares; i++)
            board.add(squares[i]);
        board.setLayout(new GridLayout((int) Math.sqrt(nofSquares), (int) Math.sqrt(nofSquares)));

        // Βάζω τα panels στο frame
        add(board, BorderLayout.CENTER); // και τα βάζω στο πάνελ τους
        JPanel down = new JPanel();
        down.setLayout(new BorderLayout());
        down.add(choicesPanel, BorderLayout.PAGE_START);
        down.add(settings, BorderLayout.PAGE_END);
        add(down, BorderLayout.PAGE_END);

        // ΓΕΝΙΚΑ
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public boolean isFinished() {
        return finished;
    }


    protected void onClose(){
        int result = JOptionPane.showConfirmDialog(
                null,
                resourceBundle.getString("sudoku.backToMenu"),
                "Suduku :(",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION){
            setVisible(false);
            finished = true;
            timer.stop();
        }
    }

    protected void onWin(String text) {
        Object[] options = {resourceBundle.getString("sudoku.ty")};
        JOptionPane.showOptionDialog(
                null,
                text,
                "Suduku!",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        setVisible(false);
        finished = true;

    }

    protected void timeIsOver(SudokuLogic game){
        if(finished == true)
            return;
        int boardSize = game.getBoard().size();
        JOptionPane.showMessageDialog(null, resourceBundle.getString("sudoku.time"));
        for (int i=0; i<boardSize; i++)
            if (!game.getGreySpots().contains(i))
                game.undoMove(i);
        setVisible(false);
        finished = true;
    }

    protected void undoMove(int position, SudokuLogic game, Locale locale) {
        game.undoMove(position);
        positions[position].setValue(0, symbolSelect.isSelected(), locale);

        int boardSize = game.getBoard().size();
        //Αποκαθιστώ τα χρώματα
        for (int j=0; j<boardSize; j++)
            positions[j].storeColor(j, game);
    }
}