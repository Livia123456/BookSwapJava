package model.search;

import java.io.Serial;
import java.io.Serializable;

public class AdvancedSearchObject implements Serializable {
    @Serial
    private final static long serialVersionUID = 99L;
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String year;
    private String edition;
    private String publisher;

    public AdvancedSearchObject(String isbn, String title, String author, String genre, String year, String edition,
                                String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre.equals("--") ? null : genre;
        this.year = year;
        this.edition = edition;
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }
}


