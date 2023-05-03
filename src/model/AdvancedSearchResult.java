package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class AdvancedSearchResult implements Serializable {
    @Serial
    private final static long serialVersionUID = 7000L;
    private ArrayList<Book> books;

    public AdvancedSearchResult(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
