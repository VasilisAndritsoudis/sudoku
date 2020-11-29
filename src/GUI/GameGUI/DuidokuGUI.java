package GUI.GameGUI;

import Counter.DigitalClockTimer;
import GameLogic.DuidokuLogic;
import GameLogic.SudokuLogic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JButton;
import javax.swing.plaf.metal.MetalButtonUI;

public class DuidokuGUI extends SudokuGUI {
    private int result;

    public DuidokuGUI(final DuidokuLogic game, Locale locale) {
        super(game, locale);
        this.help.doClick();
        time = new DigitalClockTimer(01, 00);
        this.help.setVisible(false);
        this.clear.setVisible(false);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("Bun", locale);

        for(int i = 0; i < (int)Math.sqrt((double)game.getBoard().size()); ++i) {
            choices[i].removeActionListener(this.choices[i].getActionListeners()[0]);
            choices[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    int number = Integer.parseInt(actionEvent.getActionCommand());

                    // Βάζω το number στη θέση του buttonOnUse και απενεργοποιώ το κουμπί
                    game.makeMove(buttonOnUse, number);
                    positions[buttonOnUse].setValue(number, symbolSelect.isSelected(), locale);
                    positions[buttonOnUse].setEnabled(false);

                    // Απενεργοποιώ τις επιλογές
                    for(int j = 0; j < (int)Math.sqrt(game.getBoard().size()); ++j) {
                        choices[j].setEnabled(false);
                        choices[j].setUI(new MetalButtonUI(){
                            protected Color getDisabledTextColor() {
                                return new Color(114, 114, 114, 255);
                            }
                        });
                    }

                    // Απενεργοποιώ κουμπιά που έγιναν gray
                    for (int pos : game.getGreySpots()){
                        positions[pos].setEnabled(false);
                        positions[pos].setBackground(Color.gray);
                    }

                    // Αν το παιχνίδι τελείωσε, νίκησε ο παίκτης
                    if (game.gameIsOver()) {
                        result = 1;
                        onWin(resourceBundle.getString("duidoku.win"));
                    }

                    // Αν δε τελείωσε, παίζει ο υπολογιστής
                    else {
                        int computerPosition = game.makeComputerMove();
                        positions[computerPosition].setEnabled(false);
                        positions[computerPosition].setValue(game.getBoard().get(computerPosition), symbolSelect.isSelected(), locale);

                        // Απενεργοποιώ κουμπιά που έγιναν gray
                        for (int pos : game.getGreySpots()){
                            positions[pos].setEnabled(false);
                            positions[pos].setBackground(Color.gray);
                        }

                        // Αν το παιχνίδι τελείωσε
                        if (game.gameIsOver()) {
                            // Και δεν υπάρχουν graySpots, ισοπολία
                            if (game.getGreySpots().isEmpty()){
                                result = 0;
                                onWin(resourceBundle.getString("duidoku.draw"));
                            }
                            //Και υπάρχουν graySpots, νίκησε ο υπολογιστής
                            else {
                                result = -1;
                                DuidokuGUI.this.onWin(resourceBundle.getString("duidoku.loss"));
                            }
                        }
                    }
                }
            });
        }
    }

    public int getResult() {
        return result;
    }

    @Override
    protected void onClose() {
        super.onClose();
        result = -1;
    }

    @Override
    protected void timeIsOver(SudokuLogic game) {
        super.timeIsOver(game);
        result = -1;
    }
}

