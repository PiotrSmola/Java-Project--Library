package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBook extends JFrame {
    private JPanel addBookPanel;
    private JTextField tytulDodawanyField;
    private JTextField autorDodawanyField;
    private JTextField iloscDodawanaField;
    private JButton dodajButton;
    private JButton anulujButton;
    private JLabel addBookImage;

    private BookDatabase bookDatabase;

    public AddBook() {
        super("Dodawanie książki");
        bookDatabase = new BookDatabase();
        this.setContentPane(addBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = tytulDodawanyField.getText();
                    String author = autorDodawanyField.getText();
                    int quantity = Integer.parseInt(iloscDodawanaField.getText());

                    // Stworzenie instancji nowej książki
                    Book newBook = new RegularBook(title, author, quantity);

                    // Dodanie książki do bazy
                    bookDatabase.addBook(newBook);

                    JOptionPane.showMessageDialog(AddBook.this, "Książka została dodana.", "Sukces", JOptionPane.INFORMATION_MESSAGE);

                    tytulDodawanyField.setText("");
                    autorDodawanyField.setText("");
                    iloscDodawanaField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddBook.this, "Nieprawidłowa ilość. Wprowadź liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(AddBook.this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddBook.this, "Wystąpił błąd przy dodawaniu książki.", "Błąd", JOptionPane.ERROR_MESSAGE);
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
