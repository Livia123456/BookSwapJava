package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class SearchResult implements Serializable {
    @Serial
    private final static long serialVersionUID = 8000L;
    private ArrayList<Book> books;

    public SearchResult(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
