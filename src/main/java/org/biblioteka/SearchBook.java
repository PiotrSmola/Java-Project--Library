package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBook extends JFrame {
    private JPanel searchBookPanel;
    private JPanel search;
    private JTextField tytulSzukajField;
    private JTextField autorSzukajField;
    private JButton anulujButton;
    private JButton wyszukajKsiazkeButton;
    private JLabel searchImage;

    public SearchBook() {
        super("Wyszukiwanie książki");
        this.setContentPane(searchBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        wyszukajKsiazkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
