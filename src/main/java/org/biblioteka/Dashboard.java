package org.biblioteka;

import javax.swing.*;

public class Dashboard extends JFrame{
    private JPanel dashboardPanel;
    private JTextField idField;
    private JTextField autorField;
    private JTextField tytulField;
    private JButton wypozyczButton;
    private JButton dodajKsiazkeButton;
    private JButton wyszukajButton;
    private JButton dodajUsunButton;
    private JButton zwrocButton;
    private JButton bookImportButton;
    private JButton exportBookButton;
    private JButton importReadersButton;
    private JButton exportReadersButton;
    private JTable bookTable;
    private JLabel dashboardImage;
    private JScrollPane bookPane;
    private JButton exitButton;

    public Dashboard() {
        super("Dashboard");
        this.setContentPane(dashboardPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
