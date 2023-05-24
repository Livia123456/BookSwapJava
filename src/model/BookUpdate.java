package model;

import java.io.Serializable;

public class BookUpdate implements Serializable {

    private final static long serialVersionUID = 1243412L;
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String edition;
    private String publisher;
    private String year;
    private String genre;
    private UserInfo uploadedBy;
    private boolean infoChanged;

    public BookUpdate() {}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getBookId() {
        return bookId;
    }
    public boolean infoIsChanged() { return infoChanged; }
    public void setInfoChanged(boolean value) { infoChanged = value; }

    public static class BookUpdateBuilder {
        private BookUpdate newBookInfo;

        public BookUpdateBuilder() {
            newBookInfo = new BookUpdate();
        }

        public BookUpdateBuilder bookId(int bookId) {
            newBookInfo.bookId = bookId;
            return this;
        }

        public BookUpdateBuilder title(String title) {
            newBookInfo.title = title;
            return this;
        }

        public BookUpdateBuilder author(String author) {
            newBookInfo.author = author;
            return this;
        }

        public BookUpdateBuilder isbn(String isbn) {
            newBookInfo.isbn = isbn;
            return this;
        }

        public BookUpdateBuilder edition(String edition) {
            newBookInfo.edition = edition;
            return this;
        }

        public BookUpdateBuilder publisher(String publisher) {
            newBookInfo.publisher = publisher;
            return this;
        }

        public BookUpdateBuilder year(String year) {
            newBookInfo.year = year;
            return this;
        }

        public BookUpdateBuilder genre(String genre) {
            newBookInfo.genre = genre;
            return this;
        }

        public BookUpdateBuilder uploadedBy(UserInfo userInfo) {
            newBookInfo.uploadedBy = userInfo;
            return this;
        }

        public BookUpdate build() {
            return newBookInfo;
        }

    }
}
