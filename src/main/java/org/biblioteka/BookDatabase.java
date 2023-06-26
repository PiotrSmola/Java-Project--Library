package org.biblioteka;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

class BookDatabase implements DatabaseOperations, CSVOperations {
    private List<Book> books;
    private List<BorrowInfo> borrowInfoList;

    public BookDatabase() {
        this.books = new ArrayList<>();
        this.borrowInfoList = new ArrayList<>();
        try {
            loadFromDatabase("books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) throws IllegalArgumentException {
        if (findBookByTitleAndAuthor(book.getTitle(), book.getAuthor()) != null) {
            throw new IllegalArgumentException("Książka jest już w bazie");
        }
        books.add(book);
        try {
            saveToDatabase("books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(Book book) throws NoSuchElementException {
        if (!books.remove(book)) {
            throw new NoSuchElementException("Nie ma podanej książki w bazie");
        }
        try {
            saveToDatabase("books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book findBookByTitleAndAuthor(String title, String author) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                return book;
            }
        }
        return null;
    }

    public BorrowInfo borrowBook(String title, String author, String readerId) {
        if (title == null || title.isBlank() || author == null || author.isBlank() || readerId == null || readerId.isBlank()) {
            throw new IllegalArgumentException("Nieprawidłowe dane. Żadne z pól nie może pozostać puste");
        }
        Book book = findBookByTitleAndAuthor(title, author);
        UUID readerID = UUID.fromString(readerId);
        if (book != null && book.isAvailable()) {
            book.setNumberOfCopies(book.getNumberOfCopies() - 1);
            BorrowInfo borrowInfo = new BorrowInfo(book, readerID, LocalDate.now());
            borrowInfoList.add(borrowInfo);
            System.out.println("Pomyślnie wypożyczono książkę: " + book);
            return borrowInfo;
        } else {
            System.out.println("Książka nie jest dostępna do wypożyczenia");
            return null;
        }
    }

    public BorrowInfo returnBook(String title, String author, String readerId) {
        if (title == null || title.isBlank() || author == null || author.isBlank() || readerId == null || readerId.isBlank()) {
            throw new IllegalArgumentException("Nieprawidłowe dane. Żadne z pól nie może pozostać puste.");
        }
        Book book = findBookByTitleAndAuthor(title, author);
        BorrowInfo borrowInfo = findBorrowInfo(title, author, readerId);
        if (book != null && borrowInfo != null) {
            book.setNumberOfCopies(book.getNumberOfCopies() + 1);
            borrowInfoList.remove(borrowInfo);
            System.out.println("Książka zwrócona pomyślnie: " + book);
            return borrowInfo;
        } else {
            System.out.println("Nieprawidłowy tytuł, ID czytelnika bądź książka nie jest wypożyczona przez tego czytelnika");
            return null;
        }
    }

    private BorrowInfo findBorrowInfo(String title, String author, String readerId) {
        for (BorrowInfo borrowInfo : borrowInfoList) {
            if (borrowInfo.getBook().getTitle().equals(title) && borrowInfo.getBook().getAuthor().equals(author) && borrowInfo.getReaderId().equals(readerId)) {
                return borrowInfo;
            }
        }
        return null;
    }

    @Override
    public void saveToDatabase(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getNumberOfCopies() + "\n");
            }
        }
    }

    @Override
    public void loadFromDatabase(String filePath) throws IOException {
        books.clear();
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String title = parts[0];
                    String author = parts[1];
                    int numberOfCopies = Integer.parseInt(parts[2]);
                    books.add(new RegularBook(title, author, numberOfCopies));
                }
            }
        } else {
            System.out.println("Plik " + filePath + " nie istnieje. Dane nie zostały pobrane");
        }
    }


    @Override
    public void importFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                String title = line[0];
                String author = line[1];
                int copies = Integer.parseInt(line[2]);
                this.books.add(new RegularBook(title, author, copies));
            }
        } catch (IOException e) {
            System.out.println("Wystąpił problem w odczycie pliku");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format danych w pliku");
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportToCSV(String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            for (Book book : books) {
                String[] line = {book.getTitle(), book.getAuthor(), Integer.toString(book.getNumberOfCopies())};
                writer.writeNext(line);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisu pliku");
            e.printStackTrace();
        }
    }
}
