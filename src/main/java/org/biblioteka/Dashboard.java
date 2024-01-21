package org.biblioteka;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

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
    private JScrollPane bookPane;
    private JButton exitButton;
    private BookDatabase bookDatabase;
    private ReaderDatabase readerDatabase;

    public Dashboard() {
        super("Dashboard");
        bookDatabase = new BookDatabase();
        readerDatabase = new ReaderDatabase();
        this.setContentPane(dashboardPanel);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTableHeader header = bookTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        setVisible(true);
        createTable();
        initButtons();
    }
        private void initButtons() {
            dodajKsiazkeButton.addActionListener(e -> new AddBook().setVisible(true));
            zwrocButton.addActionListener(e -> new ReturnBook().setVisible(true));
            wyszukajButton.addActionListener(e -> new SearchBook().setVisible(true));
            dodajUsunButton.addActionListener(e -> new AddDeleteReader().setVisible(true));
            bookImportButton.addActionListener(e -> performFileOperation(true, bookDatabase::importFromCSV));
            exportBookButton.addActionListener(e -> performFileOperation(false, bookDatabase::exportToCSV));
            importReadersButton.addActionListener(e -> performFileOperation(true, readerDatabase::importFromCSV));
            exportReadersButton.addActionListener(e -> performFileOperation(false, readerDatabase::exportToCSV));
            wypozyczButton.addActionListener(this::borrowBook);
            exitButton.addActionListener(e -> dispose());
        }

    private void borrowBook(ActionEvent e) {
        String id = idField.getText();
        String author = autorField.getText();
        String title = tytulField.getText();

        boolean success = bookDatabase.borrowBook(title, author, id);

        if (success) {
            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to borrow book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performFileOperation(boolean isImport, FileOperation operation) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        int result = isImport ? fileChooser.showOpenDialog(this) : fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            operation.execute(filePath);
        }
    }

    private void createTable() {
        // Assuming that books.txt is a comma-separated file
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/biblioteka/books.txt"))) {
            String line;
            Vector<Vector<String>> dataVector = new Vector<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Vector<String> dataRow = new Vector<>();
                for (String element : data) {
                    dataRow.add(element);
                }
                dataVector.add(dataRow);
            }
            Vector<String> headerVector = new Vector<>();
            headerVector.add("Tytuł");
            headerVector.add("Autor");
            headerVector.add("Dostępne egzemplarze");
            DefaultTableModel model = new DefaultTableModel(dataVector, headerVector);
            bookTable.setModel(model);
            bookTable.getSelectionModel().addListSelectionListener(event -> {
                // Get the index of the selected row
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Assuming the title is in the first column and author in the second
                    String title = bookTable.getValueAt(selectedRow, 0).toString();
                    String author = bookTable.getValueAt(selectedRow, 1).toString();
                    // Update the text fields
                    tytulField.setText(title);
                    autorField.setText(author);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface FileOperation {
        void execute(String filePath);
    }

}
