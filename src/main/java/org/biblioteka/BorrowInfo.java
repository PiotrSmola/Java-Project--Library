package org.biblioteka;

import java.time.LocalDate;
import java.util.UUID;

class BorrowInfo {
    private Book book;
    private UUID readerId;
    private LocalDate borrowDate;

    public BorrowInfo(Book book, UUID readerId, LocalDate borrowDate) {
        this.book = book;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}

