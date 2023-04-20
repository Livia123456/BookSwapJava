package model;

import database.books.DatabaseBooks;

import javax.swing.*;
import java.io.Serializable;
import java.sql.SQLException;

public class Book implements Serializable {

    private final static long serialVersionUID = 1L;
    private String title;
    private String author;
    private String release_date;
    private String genre;
    private ImageIcon image;
    private UserInfo uploadedBy;


    public void upload(int userId) throws SQLException {

        DatabaseBooks dbBooks = new DatabaseBooks();

        dbBooks.addBook(userId, title, author, release_date, genre, null);

    }
}
