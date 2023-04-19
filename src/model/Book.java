package model;

import database.books.DB_books;
import database.user.DB_user;

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


    public void upload() throws SQLException {
        //DB_user dbUser = new DB_user();
        //int userId = dbUser.getUserId(uploadedBy);


        DB_books dbBooks = new DB_books();

        dbBooks.addBook(uploadedBy.getUserId(), title, author, release_date, genre, null);

    }
}
