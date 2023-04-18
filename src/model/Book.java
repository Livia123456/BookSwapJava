package model;

import database.books.DB_books;
import database.user.DB_user;

import javax.swing.*;
import java.sql.SQLException;

public class Book {

    private String title;
    private String author;
    private String release_date;
    private String genre;
    private ImageIcon image;
    private UserInfo uploadedBy;

    public Book(UserInfo uploadedBy, String title, String author, String release_date, String genre, ImageIcon image) throws SQLException {

        DB_user dbUser = new DB_user();
        int userId = dbUser.getUserId(uploadedBy);


        DB_books dbBooks = new DB_books();

        dbBooks.addBook(userId, title, author, release_date, genre, null);

        /*
        this.title = title;
        this.author = author;
        this.release_date = release_date;
        this.genre = genre;
        this.image = image;

         */


    }
}
