package org.biblioteka;

import javax.swing.*;

public class AddBook extends JFrame{
    private JPanel addBookPanel;
    private JTextField tytulDodawanyField;
    private JTextField autorDodawanyField;
    private JTextField iloscDodawanaField;
    private JButton dodajButton;
    private JButton anulujButton;
    private JLabel addBookImage;

    public AddBook() {
        super("Logowanie pracownika");
        this.setContentPane(addBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
