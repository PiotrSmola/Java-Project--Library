package org.biblioteka;

import javax.swing.*;

public class SearchBook extends JFrame {
    private JPanel searchBookPanel;
    private JPanel search;
    private JLabel searchBookImage;
    private JTextField tytulSzukajField;
    private JTextField autorSzukajField;
    private JButton anulujButton;
    private JButton wyszukajKsiazkeButton;

    public SearchBook() {
        super("Wyszukaj książkę");
        this.setContentPane(searchBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
