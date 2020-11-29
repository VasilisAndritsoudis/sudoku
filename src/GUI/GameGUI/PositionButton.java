package GUI.GameGUI;

import GameLogic.DuidokuLogic;
import GameLogic.KillerSudokuLogic;
import GameLogic.SudokuLogic;
import SudokuGame.KillerSudoku;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class PositionButton extends JButton {

    private JLabel value;
    private JLabel sum;

    public PositionButton(SudokuLogic game, int positionOnBoard, boolean grayButton, int value, Locale locale){
        storeColor(positionOnBoard, game);
        setActionCommand(Integer.toString(positionOnBoard));
        setBorder(BorderFactory.createBevelBorder(0));

        setLayout(new BorderLayout());
        this.value = new JLabel(value==0 ? " ":Integer.toString(value));
        add(this.value, BorderLayout.CENTER);
        this.value.setHorizontalAlignment(SwingConstants.CENTER);
        sum = new JLabel();
        sum.setFont(new Font("Arial", Font.BOLD, 10));
        add(sum, BorderLayout.PAGE_START);
        sum.setHorizontalAlignment(SwingConstants.LEFT);

        disableGrayButtons(grayButton);
    }

    void setSum(int sum){
        this.sum.setText(Integer.toString(sum));
    }

    private void disableGrayButtons(boolean forDisable){
        if (forDisable){
            setEnabled(false);
            value.setFont(new Font("ArialBlack", Font.BOLD, 30));
            setUI(new MetalButtonUI(){
                protected Color getDisabledTextColor() {
                    return new Color(7, 7, 7, 223);
                }
            });
        }
        else{
            setEnabled(true);
            value.setFont(new Font("Arial", Font.PLAIN, 25));
        }

    }

    void storeColor(int position, SudokuLogic game) {
        if (game instanceof DuidokuLogic)
            setBackground(Color.white);

        else if (game instanceof KillerSudokuLogic){
            if (game.getRedPositions().contains(position) && ((KillerSudokuLogic) game).getRedSums().contains(position))
                setBackground(Color.orange);
            else if (game.getRedPositions().contains(position))
                setBackground(Color.red);
            else if (((KillerSudokuLogic) game).getRedSums().contains(position))
                setBackground(Color.yellow);
            else
                setBackground(((KillerSudokuLogic) game).getColorOfPosition(position));
        }

        else{
            if (game.getRedPositions().contains(position))
                setBackground(Color.red);
            else
                setBackground(Color.WHITE);
        }

    }

    void setValue(int value, boolean alphabetic, Locale locale){
        if (value == 0)
            this.value.setText(" ");
        else{
            if (alphabetic)
                this.value.setText(toAlphabetic(value, locale));
            else
                this.value.setText(Integer.toString(value));
        }
    }

    public String getValue(boolean alphabetic){
        if (value.getText().equals(" "))
            return null;
        return "not null";
    }

    static String toAlphabetic(int num, Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Bun", locale);
        switch (num){
            case 1: return resourceBundle.getString("sudoku.A");
            case 2: return resourceBundle.getString("sudoku.B");
            case 3: return resourceBundle.getString("sudoku.C");
            case 4: return resourceBundle.getString("sudoku.D");
            case 5: return resourceBundle.getString("sudoku.E");
            case 6: return resourceBundle.getString("sudoku.F");
            case 7: return resourceBundle.getString("sudoku.G");
            case 8: return resourceBundle.getString("sudoku.H");
            case 9: return resourceBundle.getString("sudoku.I");
        }
        return null;
    }
}