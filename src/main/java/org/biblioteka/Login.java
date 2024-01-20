package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JTextField loginField;
    private JPasswordField loginPasswordField;
    private JButton logInButton;
    private JButton cancelLoginButton;
    private JPanel loginPanel;
    private JLabel welcomePanel;

    private LoginManager loginManager; // Assume this is the class handling the login process

    public Login() {
        super("Logowanie pracownika");
        this.setContentPane(loginPanel);
        this.setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        loginManager = new LoginManager(); // Initialize your LoginManager here

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText();
                String password = new String(loginPasswordField.getPassword());

                // This method call is hypothetical and should match your actual LoginManager method
                boolean isValid = loginManager.validateLogin(username, password);

                if (isValid) {
                    // Assuming Dashboard is another JFrame that needs to be displayed upon successful login
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);
                    dispose(); // Close the login window
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
