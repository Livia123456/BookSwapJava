package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing an updated version of a users uploaded books
 * @author Livia Tengelin
 */

public class UpdateBookList implements Serializable {
    private final static long serialVersionUID = 777L;
    private ArrayList<Book> books;

    public UpdateBookList(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}