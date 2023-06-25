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



}
