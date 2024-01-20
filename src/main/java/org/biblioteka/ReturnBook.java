package org.biblioteka;

import javax.swing.*;

public class ReturnBook extends JFrame{
    private JPanel returnBookPanel;
    private JTextField idZwrotField;
    private JTextField tytulZwrotField;
    private JTextField autorZwrotField;
    private JButton zwrocButton;
    private JButton anulujButton;
    private JLabel returnBookImage;

    public ReturnBook() {
        super("Zwróć książkę");
        this.setContentPane(returnBookPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
