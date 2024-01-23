package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AddDeleteReader extends JFrame{
    private JPanel addDeleteReaderPanel;
    private JTextField idAddDelField;
    private JTextField imieNazwiskoField;
    private JTextField peselField;
    private JButton anulujButton;
    private JButton dodajCzytelnikaButton;
    private JButton usunCZytelnikaButton;
    private JLabel addDeleteImage;

    private ReaderDatabase readerDatabase = new ReaderDatabase(); // Instancja bazy danych czytelników

    public AddDeleteReader() {
        super("Dodawanie i usuwanie czytelnika");
        this.setContentPane(addDeleteReaderPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        dodajCzytelnikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = imieNazwiskoField.getText();
                String pesel = peselField.getText();

                if (name.isEmpty() || pesel.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pola 'Imię i nazwisko' oraz 'Pesel' nie mogą być puste.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Reader.validatePesel(pesel)) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowy numer PESEL.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Reader newReader = new Reader(name, pesel);
                readerDatabase.addReader(newReader);
                JOptionPane.showMessageDialog(null, "Czytelnik został dodany pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        usunCZytelnikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idAddDelField.getText();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pole 'ID' musi być wypełnione.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    UUID uuid = UUID.fromString(id);
                    Reader readerToRemove = readerDatabase.getReaders().stream()
                            .filter(reader -> reader.getId().equals(uuid))
                            .findFirst()
                            .orElseThrow(() -> new NoSuchElementException("Nie znaleziono czytelnika z ID: " + id));

                    readerDatabase.removeReader(readerToRemove);
                    JOptionPane.showMessageDialog(null, "Czytelnik został usunięty pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException | NoSuchElementException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
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
