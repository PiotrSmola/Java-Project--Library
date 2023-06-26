package org.biblioteka;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

//zgodnie ze standardami nazewnictwa nazwy klas, interfejsów oraz zmiennych są w języku angielskim, zaś komunikaty i opcje menu dla lepszego odbioru są w języku polskim
class Library {
    private BookDatabase bookDatabase; //zapewniona hermetyzacja
    private ReaderDatabase readerDatabase;
    private Scanner in;

    public Library(BookDatabase bookDatabase, ReaderDatabase readerDatabase) {
        this.bookDatabase = bookDatabase;
        this.readerDatabase = readerDatabase;
        this.in = new Scanner(System.in);
    }

    public void startLibrary() {
        boolean exit = false;

        while (!exit) {
            System.out.println("#################### MENU ####################\n" +
                    "Wybierz co chcesz zrobić podając numer funkcji\n\n" +
                    "1. Dodaj książkę do bazy\n" +
                    "2. Wypożycz książkę\n" +
                    "3. Zwróć książkę\n" +
                    "4. Wyszukaj książkę\n" +
                    "5. Dodaj czytelnika\n" +
                    "6. Usuń czytelnika\n" +
                    "7. Importuj bazę książek z pliku CSV\n" +
                    "8. Eksportuj książki do bazy w pliku CSV\n" +
                    "9. Importuj bazę czytelników z pliku CSV\n" +
                    "10. Eksportuj czytelników do bazy w pliku CSV\n" +
                    "11. Wyjście z programu");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    addBook();
                    saveState();
                    break;
                case "2":
                    borrowBook();
                    saveState();
                    break;
                case "3":
                    returnBook();
                    saveState();
                    break;
                case "4":
                    searchBook();
                    break;
                case "5":
                    addReader();
                    saveState();
                    break;
                case "6":
                    removeReader();
                    saveState();
                    break;
                case "7":
                    importBooksFromCSV();
                    saveState();
                    break;
                case "8":
                    exportBooksToCSV();
                    break;
                case "9":
                    importReadersFromCSV();
                    saveState();
                    break;
                case "10":
                    exportReadersToCSV();
                    break;
                case "11":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void borrowBook(UUID readerId, Book book) throws IOException {
        if (book.isAvailable()) {
            book.setNumberOfCopies(book.getNumberOfCopies() - 1);
            borrows.add(new BorrowInfo(book, readerId, LocalDate.now()));
            saveBorrowsToDatabase("A:\\baza do projektu\\borrows.txt");
        } else {
            System.out.println("The book is not available.");
        }
    }

    public void returnBook(UUID readerId, Book book) throws IOException {
        BorrowInfo borrowInfo = borrows.stream()
                .filter(borrow -> borrow.getBook().equals(book) && borrow.getReaderId().equals(readerId))
                .findFirst()
                .orElse(null);
        if (borrowInfo != null) {
            book.setNumberOfCopies(book.getNumberOfCopies() + 1);
            borrows.remove(borrowInfo);
            saveBorrowsToDatabase("A:\\baza do projektu\\borrows.txt");
        } else {
            System.out.println("No such borrow found.");
        }
    }

    private void saveBorrowsToDatabase(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BorrowInfo borrow : borrows) {
                writer.write(borrow.getReaderId() + "," + borrow.getBook().getTitle() + "," + borrow.getBook().getAuthor() + "," + borrow.getBorrowDate() + "\n");
            }
        }
    }

    private void loadBorrowsFromDatabase(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID readerId = UUID.fromString(parts[0]);
                String title = parts[1];
                String author = parts[2];
                Book book = new RegularBook(title, author, 1); // you can replace this with a proper way to find the book
                LocalDate borrowDate = LocalDate.parse(parts[3]);
                borrows.add(new BorrowInfo(book, readerId, borrowDate));
            }
        }
    }

    public void importBooksFromCSV() {
        System.out.println("Enter CSV file path:");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            bookDatabase.importFromCSV(filePath);
        } else {
            System.out.println("File not found. Please check the path and try again.");
        }
    }

    public void exportBooksToCSV() {
        System.out.println("Enter CSV file path:");
        String filePath = scanner.nextLine();
        // W tym przypadku, jeżeli plik nie istnieje, zostanie stworzony.
        // Jeżeli jednak istnieje, dane zostaną do niego dopisane, więc warto o tym poinformować użytkownika.
        System.out.println("If the file already exists, new data will be appended to it.");
        bookDatabase.exportToCSV(filePath);
    }

    public void importReadersFromCSV() {
        System.out.println("Enter CSV file path:");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            readerDatabase.importFromCSV(filePath);
        } else {
            System.out.println("File not found. Please check the path and try again.");
        }
    }

    public void exportReadersToCSV() {
        System.out.println("Enter CSV file path:");
        String filePath = scanner.nextLine();
        System.out.println("If the file already exists, new data will be appended to it.");
        readerDatabase.exportToCSV(filePath);
    }

    private void borrowBook() {
        System.out.println("Enter reader ID:");
        String readerId = scanner.nextLine();
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        bookDatabase.borrowBook(title, author, readerId);
    }



    private void addBook() {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        System.out.println("Enter number of copies:");
        int copies = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        bookDatabase.addBook(new RegularBook(title, author, copies));
    }


    private void returnBook() {
        System.out.println("Enter reader ID:");
        String readerId = scanner.nextLine();
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        // Obliczanie kary za opóźnienie
        BorrowInfo borrowInfo = bookDatabase.returnBook(title, author, readerId);
        if (borrowInfo != null) {
            long daysBetween = ChronoUnit.DAYS.between(borrowInfo.getBorrowDate(), LocalDate.now());
            if (daysBetween > 14) {
                double fine = (daysBetween - 14) * 1.0; // 1.0 PLN per day fine
                System.out.println("Book returned successfully. Fine for late return: " + fine + " PLN");
            } else {
                System.out.println("Book returned successfully.");
            }
        } else {
            System.out.println("Invalid book title, reader ID, or book not borrowed by this reader.");
        }
    }

    private void searchBook() {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        Book book = bookDatabase.findBookByTitleAndAuthor(title, author);
        if (book != null) {
            System.out.println("Book found: " + book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private void addReader() {
        System.out.println("Enter reader name:");
        String name = scanner.nextLine();
        System.out.println("Enter reader PESEL:");
        String pesel = scanner.nextLine();
        try {
            readerDatabase.addReader(new Reader(name, pesel));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid PESEL number. Please try again.");
        }
    }


    private void removeReader() {
        System.out.println("Enter reader name:");
        String name = scanner.nextLine();
        System.out.println("Enter reader PESEL:");
        String pesel = scanner.nextLine();
        try {
            readerDatabase.removeReader(new Reader(name, pesel));
        } catch (NoSuchElementException e) {
            System.out.println("No such reader in the database.");
        }
    }


    private void loadBorrowInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("A:\\baza do projektu\\borrows.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String readerId = parts[0];
                String bookTitle = parts[1];
                String bookAuthor = parts[2];
                UUID uid = UUID.fromString(readerId);
                LocalDate borrowDate = LocalDate.parse(parts[3]);
                Book book = bookDatabase.findBookByTitleAndAuthor(bookTitle, bookAuthor);
                if (book != null) {
                    borrows.add(new BorrowInfo(book, uid, borrowDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBorrowInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrows.txt"))) {
            for (BorrowInfo borrowInfo : borrows) {
                writer.write(borrowInfo.getBook().getTitle() + ","
                        + borrowInfo.getBook().getAuthor() + ","
                        + borrowInfo.getReaderId() + ","
                        + borrowInfo.getBorrowDate() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveState() {
        try {
            this.bookDatabase.saveToDatabase("books.txt");
            this.readerDatabase.saveToDatabase("readers.txt");
            this.saveBorrowInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BookDatabase bookDatabase = new BookDatabase();
        ReaderDatabase readerDatabase = new ReaderDatabase();
        Library library = new Library(bookDatabase, readerDatabase);
        library.startLibraryOperations();
    }
}

