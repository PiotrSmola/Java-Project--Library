package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField loginField;
    private JPasswordField loginPasswordField;
    private JButton logInButton;
    private JButton cancelLoginButton;
    private JPanel loginPanel;
    private JLabel welcomePanel;

    private LoginManager loginManager;

    public Login() {
        super("Logowanie pracownika");
        this.setContentPane(loginPanel);
        this.setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        loginManager = new LoginManager();

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText();
                String password = new String(loginPasswordField.getPassword());

                boolean isValid = loginManager.validateLogin(username, password);

                if (isValid) {
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Nieprawidłowy login lub hasło!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
