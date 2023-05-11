package model.search;

import model.Book;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to store the result of an advanced search query represented by
 * an array of book-objects
 * @author Livia Tengelin
 */

public class AdvancedSearchResult implements Serializable {

    private final static long serialVersionUID = 7000L;
    private ArrayList<Book> books;

    public AdvancedSearchResult(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}