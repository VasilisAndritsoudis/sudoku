package GUI.MenuFrame;

import GUI.GameGUI.DuidokuGUI;
import GUI.GameGUI.KillerGUI;
import GUI.GameGUI.SudokuGUI;
import GUI.LoginFrame.LoginFrame;
import User.User;
import GameLogic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Menu extends JFrame {

    private ResourceBundle resourceBundle;

    private SudokuGUI sudokuGUI;
    private SudokuLogic sudokuGame;

    private KillerGUI killerGUI;
    private KillerSudokuLogic killerGame;

    private DuidokuGUI duidokuGUI;
    private DuidokuLogic duidokuGame;

    private User user;
    private Locale locale;

    private JLabel winsLabel;
    private JLabel lossesLabel;
    private JLabel drawsLabel;
    private JLabel sudokuLevelLabel;
    private JLabel killerLevelLabel;

    private Timer timer;

    public Menu(User user, Locale locale) {
        this.user = user;
        this.locale = locale;
        makeFrame(user);
    }

    private void makeFrame(User user){
        resourceBundle = ResourceBundle.getBundle("Bun", locale);

        this.setTitle(resourceBundle.getString("menu.title"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panels

        JPanel buttonsPanel = new JPanel();
        JPanel statsPanel = new JPanel();

        // Labels

        // Menu Label

        JLabel menuLabel = new JLabel(resourceBundle.getString("menu.menu"));
        menuLabel.setFont(new Font("Munich", Font.PLAIN, 50));
        if(locale.getLanguage() == "el")
            menuLabel.setPreferredSize(new Dimension(650, 120));
        else
            menuLabel.setPreferredSize(new Dimension(550, 120));
        menuLabel.setVerticalAlignment(JLabel.BOTTOM);
        menuLabel.setHorizontalAlignment(JLabel.CENTER);
        menuLabel.setForeground(new Color(214, 237, 23));

        // Username Label

        JLabel usernameLabel = new JLabel(user.getUsername());
        modifyLabel(usernameLabel);
        if(locale.getLanguage() == "el"){
            usernameLabel.setPreferredSize(new Dimension(145,100));
            usernameLabel.setFont(new Font("Munich", Font.BOLD, 25));
        }else{
            usernameLabel.setFont(new Font("Munich", Font.BOLD, 30));
        }

        // Wins Label

        winsLabel = new JLabel(String.valueOf(resourceBundle.getString("menu.wins") + user.getWins()));
        modifyLabel(winsLabel);

        // Losses Label

        lossesLabel = new JLabel(String.valueOf(resourceBundle.getString("menu.losses") + user.getLosses()));
        modifyLabel(lossesLabel);

        // Draws Label

        drawsLabel = new JLabel(String.valueOf(resourceBundle.getString("menu.draws") + user.getDraws()));
        modifyLabel(drawsLabel);

        // Sudoku Level Label

        sudokuLevelLabel = new JLabel(String.valueOf(resourceBundle.getString("menu.sudokuLevel") + (user.getSudokuGamesPlayed() + 1)));
        modifyLabel(sudokuLevelLabel);
        sudokuLevelLabel.setPreferredSize(new Dimension(200, 100));

        // Killer Level Label

        killerLevelLabel = new JLabel(String.valueOf(resourceBundle.getString("menu.killerLevel") + (user.getKillerGamesPlayed() + 1)));
        modifyLabel(killerLevelLabel);
        killerLevelLabel.setPreferredSize(new Dimension(200, 100));

        // Buttons

        // Sudoku Button

        JButton sudokuButton = new JButton(resourceBundle.getString("menu.sudoku"));
        modifyButton(sudokuButton);
        sudokuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent actionEvent)
            {
                sudokuGame = new SudokuLogic(user.getSudokuBoard(), user.getSudokuGreySpots());

                sudokuGUI = new SudokuGUI(sudokuGame, locale);

                setVisible(false);
            }
        });

        // Killer Button

        JButton killerButton = new JButton(resourceBundle.getString("menu.killer"));
        modifyButton(killerButton);
        killerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent actionEvent)
            {
                killerGame = new KillerSudokuLogic(user.getKillerBoard(), user.getKillerColoredAreas());

                killerGUI = new KillerGUI(killerGame, locale);

                setVisible(false);
            }
        });

        // Duidoku Button

        JButton duidokuButton = new JButton(resourceBundle.getString("menu.duidoku"));
        modifyButton(duidokuButton);
        duidokuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent actionEvent)
            {
                duidokuGame = new DuidokuLogic(16);

                duidokuGUI = new DuidokuGUI(duidokuGame, locale);

                setVisible(false);
            }
        });

        // Logout Button

        JButton logoutButton = new JButton(resourceBundle.getString("menu.logout"));
        modifyButton(logoutButton);
        logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent actionEvent)
            {
                LoginFrame loginFrame = new LoginFrame();
                dispose();
            }
        });

        this.setLayout(new BorderLayout());
        this.add(menuLabel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);
        this.add(statsPanel, BorderLayout.SOUTH);

        buttonsPanel.setLayout(new GridBagLayout());
        statsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Add buttons in buttons panel

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-100,0,0,0);
        buttonsPanel.add(sudokuLevelLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(-25,0,0,0);
        buttonsPanel.add(sudokuButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(-25,0,0,0);
        buttonsPanel.add(killerLevelLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(-25,0,50,0);
        buttonsPanel.add(killerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0,0,50,0);
        buttonsPanel.add(duidokuButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0,0,-50,0);
        buttonsPanel.add(logoutButton, gbc);

        // Add labels in stats panel

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(-50, 20, 30, 20);
        statsPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        statsPanel.add(winsLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        statsPanel.add(lossesLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        statsPanel.add(drawsLabel, gbc);

        this.pack();
        this.getContentPane().setBackground(new Color(96, 96, 96));
        buttonsPanel.setBackground(new Color(96, 96, 96));
        statsPanel.setBackground(new Color(96, 96, 96));
        this.setSize(new Dimension(this.getWidth() + 50, this.getHeight() + 250));

        timer = new Timer();
        timer.schedule(new checkGameStatus(), 0, 100);

        this.setMinimumSize(new Dimension(getWidth(), getHeight()));

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void modifyButton(JButton button){
        button.setFont(new Font("Munich", Font.BOLD, 30));
        button.setPreferredSize(new Dimension(350, 60));
        button.setBorder(null);
        button.setBackground(new Color(214, 237, 23));
        button.setForeground(new Color(56, 56, 56));
    }

    private void modifyLabel(JLabel label){
        if(locale.getLanguage() == "el")
            label.setPreferredSize(new Dimension(120, 100));
        else
            label.setPreferredSize(new Dimension(100, 100));
        label.setFont(new Font("Munich", Font.PLAIN, 20));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(new Color(214, 237, 23));
    }

    class checkGameStatus extends TimerTask {
        @Override
        public void run () {
            checkForSudokuUpdate();

            checkForKillerUpdate();

            checkForDuidokuUpdate();
        }

        private void checkForSudokuUpdate(){
            if(sudokuGUI == null)
                return;

            if(sudokuGUI.isFinished()){
                // Check if the sudoku game is completed
                if(sudokuGame.gameIsOver()){
                    user.addSudokuGamePlayed();
                    sudokuLevelLabel.setText(resourceBundle.getString("menu.sudokuLevel") + (user.getSudokuGamesPlayed() + 1));
                }else
                    user.setSudokuBoard(sudokuGame.getBoard());

                if(!user.getUsername().equals(resourceBundle.getString("login.Guest")))
                    user.saveData();

                sudokuGUI.dispose();
                sudokuGUI = null;
                setVisible(true);

            }
        }

        private void checkForKillerUpdate(){
            if(killerGUI == null)
                return;

            if(killerGUI.isFinished()){
                // Check if the killer sudoku game is completed
                if(killerGame.gameIsOver()){
                    user.addKillerGamePlayed();
                    killerLevelLabel.setText(resourceBundle.getString("menu.killerLevel") + (user.getKillerGamesPlayed() + 1));
                }else
                    user.setKillerBoard(killerGame.getBoard());

                if(!user.getUsername().equals(resourceBundle.getString("login.Guest")))
                    user.saveData();

                killerGUI.dispose();
                killerGUI = null;
                setVisible(true);

            }
        }

        private void checkForDuidokuUpdate(){
            if(duidokuGUI == null)
                return;

            if(duidokuGUI.isFinished()){
                // Check who won the game
                if(duidokuGUI.getResult() == 1){
                    user.addWin();
                    winsLabel.setText(resourceBundle.getString("menu.wins") + user.getWins());
                }else if(duidokuGUI.getResult() == 0){
                    user.addDraw();
                    drawsLabel.setText(resourceBundle.getString("menu.draws") + user.getDraws());
                }else{
                    user.addLoss();
                    lossesLabel.setText(resourceBundle.getString("menu.losses") + user.getLosses());
                }


                if(!user.getUsername().equals(resourceBundle.getString("login.Guest")))
                    user.saveData();

                duidokuGUI.dispose();
                duidokuGUI = null;
                setVisible(true);
            }
        }
    }
}
