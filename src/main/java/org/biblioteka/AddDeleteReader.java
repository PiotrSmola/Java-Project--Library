package org.biblioteka;

import javax.swing.*;

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
        super("Logowanie pracownika");
        this.setContentPane(addDeleteReaderPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
