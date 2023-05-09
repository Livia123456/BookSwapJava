package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class UpdateBookList implements Serializable {
    @Serial
    private final static long serialVersionUID = 777L;
    private ArrayList<Book> books;

    public UpdateBookList(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}