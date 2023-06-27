package org.biblioteka;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

// Klasa reprezentująca bazę danych czytelników
class ReaderDatabase implements DatabaseOperations, CSVOperations {
    private List<Reader> readers;
    private static List<BorrowInfo> borrows;
    private static List<Book> books;  // nowa lista do przechowywania książek

    public ReaderDatabase() {
        this.readers = new ArrayList<>();
        this.borrows = new ArrayList<>();
        this.books = new ArrayList<>();  // inicjalizacja nowej listy
        try {
            loadFromDatabase("src/main/java/org/biblioteka/readers.txt");
            loadBorrowsFromDatabase("src/main/java/org/biblioteka/borrows.txt");
            loadBooksFromDatabase("src/main/java/org/biblioteka/books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReader(Reader reader) {
        readers.add(reader);
        try {
            saveToDatabase("src/main/java/org/biblioteka/readers.txt");
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas aktualizacji bazy danych");
            e.printStackTrace();
        }
    }
    public void addBorrow(BorrowInfo borrowInfo) {
        borrows.add(borrowInfo);
        saveBorrowsToDatabase("src/main/java/org/biblioteka/borrows.txt");
    }
    public void removeBorrow(BorrowInfo borrowInfo) {
        borrows.remove(borrowInfo);
        saveBorrowsToDatabase("src/main/java/org/biblioteka/borrows.txt");
    }

    public void removeReader(Reader reader) throws NoSuchElementException {
        if (!readers.remove(reader)) {
            throw new NoSuchElementException("Nie ma takiego czytelnika w bazie danych");
        }
        try {
            saveToDatabase("src/main/java/org/biblioteka/readers.txt");
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas aktualizacji bazy danych");
            e.printStackTrace();
        }
    }

    public void saveToDatabase(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Reader reader : readers) {
                writer.write(reader.getId() + "," + reader.getName() + "," + reader.getPesel() + "\n");
            }
        }
    }

    public void loadFromDatabase(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID id = UUID.fromString(parts[0]);
                String name = parts[1];
                String pesel = parts[2];
                if (!Reader.validatePesel(pesel)) {
                    System.out.println("Niepoprawny nr PESEL: " + pesel);
                    continue;
                }
                this.readers.add(new Reader(id, name, pesel));
            }
        }
    }

    public void borrowBook(UUID readerId, Book book) throws IOException {
        Book bookToBorrow = books.stream()
                .filter(b -> b.equals(book))
                .findFirst()
                .orElse(null);
        if (bookToBorrow != null && bookToBorrow.isAvailable()) {
            bookToBorrow.setNumberOfCopies(bookToBorrow.getNumberOfCopies() - 1);
            borrows.add(new BorrowInfo(bookToBorrow, readerId, LocalDate.now()));
            saveBorrowsToDatabase("src/main/java/org/biblioteka/borrows.txt");
        } else {
            System.out.println("Podana książka jest niedostępna");
        }
    }

    public void returnBook(UUID readerId, Book book) throws IOException {
        BorrowInfo borrowInfo = borrows.stream()
                .filter(borrow -> borrow.getBook().equals(book) && borrow.getReaderId().equals(readerId))
                .findFirst()
                .orElse(null);
        if (borrowInfo != null) {
            Book bookToReturn = books.stream()
                    .filter(b -> b.equals(borrowInfo.getBook()))
                    .findFirst()
                    .orElse(null);
            if (bookToReturn != null) {
                bookToReturn.setNumberOfCopies(bookToReturn.getNumberOfCopies() + 1);
                borrows.remove(borrowInfo);
                saveBorrowsToDatabase("src/main/java/org/biblioteka/borrows.txt");
            }
        } else {
            System.out.println("Nie odnaleziono wypożyczenia");
        }
    }



    public static void saveBorrowsToDatabase(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BorrowInfo borrow : borrows) {
                writer.write(borrow.getReaderId() + "," + borrow.getBook().getId() + "," + borrow.getBorrowDate() + "\n");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania do pliku: " + filePath);
            e.printStackTrace();
        }
    }

    public static void loadBorrowsFromDatabase(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID readerId = UUID.fromString(parts[0]);
                UUID bookId = UUID.fromString(parts[1]);
                LocalDate borrowDate = LocalDate.parse(parts[2]);

                Book book = books.stream()
                        .filter(b -> b.getId().equals(bookId))
                        .findFirst()
                        .orElse(null);

                if (book != null) {
                    borrows.add(new BorrowInfo(book, readerId, borrowDate));
                }
            }
        }
    }



    private void loadBooksFromDatabase(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String author = parts[1];
                int numberOfCopies = Integer.parseInt(parts[2]);
                books.add(new RegularBook(title, author, numberOfCopies));
            }
        }
    }



    @Override
    public void importFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                UUID id = UUID.fromString(line[0]);
                String name = line[1];
                String pesel = line[2];
                this.readers.add(new Reader(id, name, pesel));
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas odczytu pliku");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format danych w podanym pliku");
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportToCSV(String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            for (Reader reader : readers) {
                String[] line = {reader.getId().toString(), reader.getName(), reader.getPesel()};
                writer.writeNext(line);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisu pliku");
            e.printStackTrace();
        }
    }
}

