package org.biblioteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDeleteReader extends JFrame{
    private JPanel addDeleteReaderPanel;
    private JTextField idAddDelField;
    private JTextField imieNazwiskoField;
    private JTextField peselField;
    private JButton anulujButton;
    private JButton dodajCzytelnikaButton;
    private JButton usunCZytelnikaButton;
    private JLabel addDeleteImage;

    public AddDeleteReader() {
        super("Dodawanie i usuwanie czytelnika");
        this.setContentPane(addDeleteReaderPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        dodajCzytelnikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        usunCZytelnikaButton.addActionListener(new ActionListener() {
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
