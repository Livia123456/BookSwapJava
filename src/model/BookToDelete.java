package model;

import java.io.Serializable;

public class BookToDelete implements Serializable {

    private final static long serialVersionUID = 1337L;
    private int bookId;

    public BookToDelete(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }
}
