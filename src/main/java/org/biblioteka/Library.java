package org.biblioteka;

import java.util.Scanner;

//zgodnie ze standardami nazewnictwa nazwy klas, interfejsów oraz zmiennych są w języku angielskim, zaś komunikaty i opcje menu dla lepszego odbioru są w języku polskim
class Library {
    private BookDatabase bookDatabase;
    private ReaderDatabase readerDatabase;
    private Scanner in;

    public Library(BookDatabase bookDatabase, ReaderDatabase readerDatabase) {
        this.bookDatabase = bookDatabase;
        this.readerDatabase = readerDatabase;
        this.in = new Scanner(System.in);
    }

    public void startLibraryOperations() {
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
        }
    }

}
