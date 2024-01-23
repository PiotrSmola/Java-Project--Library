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
        this.setSize(500, 270);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        wyszukajKsiazkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tytulSzukajField.getText();
                String author = autorSzukajField.getText();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pola 'Tytuł' i 'Autor' nie mogą być puste.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book foundBook = BookDatabase.findBookByTitleAndAuthor(title, author);

                if (foundBook != null) {
                    String availabilityMessage = foundBook.isAvailable() ? "Książka jest dostępna do wypożyczenia." : "Wszystkie egzemplarze są wypożyczone.";
                    JOptionPane.showMessageDialog(null, "Książka została znaleziona. " + availabilityMessage, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Książka nie została znaleziona.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
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
