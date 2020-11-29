package GUI.LoginFrame;

import User.User;
import GUI.MenuFrame.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame {

    private Locale locale;

    public LoginFrame(){
        if(Locale.getDefault().getLanguage() == "el")
            locale = new Locale("el", "GR");
        else
            locale = new Locale("en", "US");
        makeFrame();
    }

    private void makeFrame(){
        final ResourceBundle[] resourceBundle = {ResourceBundle.getBundle("Bun", locale)};

        this.setTitle(resourceBundle[0].getString("login.title"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel buttonsPanel = new JPanel();

        // Username Text Field

        JTextField usernameTextField = new JTextField(resourceBundle[0].getString("login.Username"), 35);
        usernameTextField.setForeground(Color.GRAY);
        usernameTextField.setFont(new Font("Munich", Font.PLAIN, 20));
        usernameTextField.setPreferredSize(new Dimension(600,40));
        usernameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getText().equals(resourceBundle[0].getString("login.Username"))) {
                    usernameTextField.setText("");
                    usernameTextField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getText().isEmpty()) {
                    usernameTextField.setForeground(Color.GRAY);
                    usernameTextField.setText(resourceBundle[0].getString("login.Username"));
                }
            }
        });

        // Labels

        // Intro Label

        JLabel introLabel = new JLabel(resourceBundle[0].getString("login.welcome"));
        introLabel.setHorizontalAlignment(JLabel.CENTER);
        introLabel.setFont(new Font("Munich", Font.PLAIN, 50));
        introLabel.setPreferredSize(new Dimension(250, 120));
        introLabel.setVerticalAlignment(JLabel.BOTTOM);
        introLabel.setForeground(new Color(214, 237, 23));

        // Error Label

        JLabel errorLabel = new JLabel("");
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setFont(new Font("Munich", Font.PLAIN, 20));
        errorLabel.setPreferredSize(new Dimension(250, 50));
        errorLabel.setVerticalAlignment(JLabel.TOP);
        errorLabel.setForeground(new Color(214, 237, 23));

        // Button Creation

        // Login Button

        JButton loginButton = new JButton(resourceBundle[0].getString("login.login"));
        modifyButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameTextField.getText();

                if(username.compareTo(resourceBundle[0].getString("login.Username")) == 0){
                    errorLabel.setText(resourceBundle[0].getString("login.error.noUsername"));
                    return;
                }

                User user = new User(username);

                if(user.login()){
                    Menu menu = new Menu(user, locale);
                    dispose();
                }else{
                    errorLabel.setText(resourceBundle[0].getString("login.error.userNotFound"));
                }
            }
        });

        // Register Button

        JButton registerButton = new JButton(resourceBundle[0].getString("login.register"));
        modifyButton(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameTextField.getText();
                User user = new User(username);

                if(username.equals(resourceBundle[0].getString("login.Guest")) || username.equals(resourceBundle[0].getString("login.guest")) || username.equals(resourceBundle[0].getString("login.username")))
                    errorLabel.setText(resourceBundle[0].getString("login.error.usernameInvalid"));
                else if(username.equals(resourceBundle[0].getString("login.username")) || username.isEmpty() || username.equals(resourceBundle[0].getString("login.Username")))
                    errorLabel.setText(resourceBundle[0].getString("login.error.noUsername"));
                else if(user.login()){
                    errorLabel.setText(resourceBundle[0].getString("login.error.usernameExists"));
                }
                else{
                    user.saveData();

                    Menu menu = new Menu(user, locale);
                    dispose();
                }
            }
        });

        // Continue as Guest Button

        JButton continueAsGuestButton = new JButton(resourceBundle[0].getString("login.Guest"));
        modifyButton(continueAsGuestButton);
        continueAsGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = resourceBundle[0].getString("login.Guest");
                User user = new User(username);

                Menu menu = new Menu(user, locale);
                dispose();
            }
        });

        // Change Language Button

        JButton changeLanguageButton = new JButton(resourceBundle[0].getString("login.language"));
        modifyButton(changeLanguageButton);
        changeLanguageButton.setPreferredSize(new Dimension(40,40));
        changeLanguageButton.setFont(new Font("Munich", Font.BOLD, 20));
        changeLanguageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent actionEvent)
            {
                ResourceBundle.clearCache();

                if(locale.getLanguage() == "el"){
                    locale = new Locale("en", "US");
                    resourceBundle[0] = ResourceBundle.getBundle("Bun", locale);
                }else{
                    locale = new Locale("el", "GR");
                    resourceBundle[0] = ResourceBundle.getBundle("Bun", locale);
                }

                usernameTextField.setText(resourceBundle[0].getString("login.Username"));
                loginButton.setText(resourceBundle[0].getString("login.login"));
                registerButton.setText(resourceBundle[0].getString("login.register"));
                continueAsGuestButton.setText(resourceBundle[0].getString("login.Guest"));
                changeLanguageButton.setText(resourceBundle[0].getString("login.language"));
                errorLabel.setText("");
            }
        });

        this.setLayout(new BorderLayout());
        this.add(introLabel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);
        this.add(errorLabel, BorderLayout.SOUTH);

        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(-110,7,25,7);
        buttonsPanel.add(usernameTextField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,7,-75,7);
        buttonsPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(registerButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonsPanel.add(continueAsGuestButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        buttonsPanel.add(changeLanguageButton, gbc);

        this.pack();
        this.getContentPane().setBackground(new Color(96, 96, 96));
        buttonsPanel.setBackground(new Color(96, 96, 96));
        introLabel.requestFocusInWindow();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                introLabel.requestFocusInWindow();
            }
        });
        this.setSize(new Dimension(this.getWidth() + 50, this.getHeight() + 250));
        this.setMinimumSize(new Dimension(getWidth(), getHeight()));

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void modifyButton(JButton button){
        button.setPreferredSize(new Dimension(200,40));
        button.setBorder(null);
        button.setBackground(new Color(214, 237, 23));
        button.setForeground(new Color(56, 56, 56));
        button.setFont(new Font("Munich", Font.BOLD, 25));
    }
}