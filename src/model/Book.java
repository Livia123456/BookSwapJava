package model;


import model.search.SearchAble;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class to store information about a book.
 * @author Sebastian Zulj, Kasper Lindberg
 */

public class Book implements SearchAble, Serializable {

    private final static long serialVersionUID = 1L;
    private int bookId;
    private String title;
    private String author;
    private String release_date;
    private String genre;
    private ImageIcon image;
    private String edition;
    private String publisher;
    private String isbn;
    private UserInfo uploadedBy;
    private boolean isUploaded = false;



    public int getBook_id() {
        return bookId;
    }

    public void setBook_id(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public UserInfo getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserInfo uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    /**
     * Builder class to create a book-object.
     * @author Livia Tengelin
     */
    public static class BookBuilder {

        private Book book;

        public BookBuilder() {
            book = new Book();
        }

        public BookBuilder bookId(int bookId) {
            book.bookId = bookId;
            return this;
        }

        public BookBuilder title(String title) {
            book.title = title;
            return this;
        }
        public BookBuilder author(String author) {
            book.author = author;
            return this;
        }
        public BookBuilder release_date(String release_date) {
            book.release_date = release_date;
            return this;
        }
        public BookBuilder genre(String genre) {
            if (!Objects.equals(genre, "-")) {
                book.genre = genre;
            }
            return this;
        }

        public BookBuilder image(ImageIcon image) {
            book.image = image;
            return this;
        }

        public BookBuilder edition(String edition) {
            book.edition = edition;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            book.publisher = publisher;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            book.isbn = isbn;
            return this;
        }

        public BookBuilder uploadedBy(UserInfo uploadedBy) {
            book.uploadedBy = uploadedBy;
            return this;
        }

        public Book build() {
            return book;
        }

    }
}