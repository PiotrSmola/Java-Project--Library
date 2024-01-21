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

    // Reference to the BookDatabase
    private BookDatabase bookDatabase; // Assume bookDatabase is initialized and passed to the constructor

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
                BorrowInfo result = bookDatabase.returnBook(title, author, id);
                if (result != null) {
                    int fine = bookDatabase.calculateFine(result);
                    if (fine > 0) {
                        JOptionPane.showMessageDialog(ReturnBook.this,
                                "Book returned successfully. Fine: " + fine + " PLN",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ReturnBook.this,
                                "Book returned successfully with no fine.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ReturnBook.this,
                            "Failed to return book. Please check the details.",
                            "Error", JOptionPane.ERROR_MESSAGE);
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
