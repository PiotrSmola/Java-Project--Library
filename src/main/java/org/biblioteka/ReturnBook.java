package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBook extends JFrame {
    private JPanel returnBookPanel;
    private JTextField idZwrotField;
    private JTextField tytulZwrotField;
    private JTextField autorZwrotField;
    private JButton zwrocButton;
    private JButton anulujButton;
    private JLabel returnBookImage;

    private BookDatabase bookDatabase;

    public ReturnBook(BookDatabase bookDatabase) {
        super("Zwracanie książki");
        this.bookDatabase = bookDatabase;
        this.setContentPane(returnBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        zwrocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idZwrotField.getText();
                String title = tytulZwrotField.getText();
                String author = autorZwrotField.getText();

                //Sprawdzanie czy któreś z pól jest puste
                if (id.trim().isEmpty() || title.trim().isEmpty() || author.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(ReturnBook.this,
                            "Wszystkie pola muszą być wypełnione.",
                            "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    BorrowInfo result = bookDatabase.returnBook(title, author, id);
                    if (result != null) {
                        int fine = bookDatabase.calculateFine(result);
                        if (fine > 0) {
                            JOptionPane.showMessageDialog(ReturnBook.this,
                                    "Książka zwrócona pomyślnie. Kara: " + fine + " PLN",
                                    "Sukces", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ReturnBook.this,
                                    "Książka zwrócona pomyślnie bez kary.",
                                    "Sukces", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ReturnBook.this,
                                "Nie udało się zwrócić książki. Nieprawidłowy tytuł, ID czytelnika bądź książka nie jest wypożyczona przez tego czytelnika. Spróbuj ponownie",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ReturnBook.this,
                            ex.getMessage(),
                            "Błąd", JOptionPane.ERROR_MESSAGE);
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
