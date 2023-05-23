package database.books;

import controller.server.BookController;
import database.Database;
import model.Book;
import model.BookUpdate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class DatabaseBooks {

    private Database db;
    private Semaphore semaphore;
    private BookController bookController;

    public DatabaseBooks(BookController bookController){
        db = Database.getInstance();
        semaphore = db.getDbSemaphore();
        this.bookController = bookController;
    }


    /**
     * Returns title of a book from user_id.
     */
    public ArrayList<String> getTitlesByUser(int user_id) {

        ArrayList<String> titles = new ArrayList<>();

        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("select title from book where user_id = %d", user_id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (Exception e){
            e.printStackTrace();
        }

        return titles;
    }

    /**
     * Gets a list of books uploaded by current user
     */
    public ArrayList<Book> getBooksUploadedByUser(int userId) {
        ArrayList<Book> uploadedBooks = new ArrayList<>();
        try {
            Connection con = db.getDatabaseConnection();
            String QUERY = String.format("SELECT * FROM book where user_id = %d", userId);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                Book book = new Book.BookBuilder().title(rs.getString("title")).author(rs.getString("author")).
                        genre(rs.getString("genre")).release_date(rs.getString("release_year")).
                        edition(rs.getString("edition")).publisher(rs.getString("publisher")).
                        isbn(rs.getString("isbn")).bookId(rs.getInt("book_id")).build();
                uploadedBooks.add(book);
                System.out.println(book.getTitle());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedBooks;
    }

    /**
     * Adds a book from parameters.
     */
    public Book addBook(Book book) {

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = "";

        if (book.getRelease_date() == null || book.getRelease_date().isEmpty() || book.getRelease_date().isBlank()) {
            QUERY = String.format("insert into book(book_id, user_id, title, author, genre, image_path, edition, " +
                            "publisher, isbn) values(default, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    book.getUploadedBy().getUserId(), book.getTitle(), book.getAuthor(),
                    book.getGenre(), "imagepath...", book.getEdition(), book.getPublisher(), book.getIsbn());

        } else {

            QUERY = String.format("insert into book(book_id, user_id, title, author, release_year, genre, image_path, " +
                            "edition, publisher, isbn) values(default, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    book.getUploadedBy().getUserId(), book.getTitle(), book.getAuthor(), book.getRelease_date(),
                    book.getGenre(), "imagepath...", book.getEdition(), book.getPublisher(), book.getIsbn());
        }
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();
            book.setUploaded(true);

        } catch (Exception e) {
             e.printStackTrace();

        }
        semaphore.release();
        return book;
    }

    /**
     * Removes a book from the database given the book's unique book ID.
     */
    public void deleteBook(int bookId) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("DELETE FROM book WHERE book_id = %d", bookId);

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);
            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public void updateBookInfo(BookUpdate book) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = "";

        if (book.getYear() == null || book.getYear().isEmpty() || book.getYear().isBlank()) {
            QUERY = String.format("UPDATE book SET title = '%s', author = '%s', " +
                            "genre = '%s', edition = '%s', publisher = '%s', " +
                            "isbn = '%s' WHERE book_id = %d", book.getTitle(), book.getAuthor(),
                    book.getGenre(), book.getEdition(), book.getPublisher(),
                    book.getIsbn(), book.getBookId());
        } else {
            QUERY = String.format("UPDATE book SET title = '%s', author = '%s', " +
                            "release_year = '%s', genre = '%s', edition = '%s', publisher = '%s', " +
                            "isbn = '%s' WHERE book_id = %d", book.getTitle(), book.getAuthor(),
                    book.getYear(), book.getGenre(), book.getEdition(), book.getPublisher(),
                    book.getIsbn(), book.getBookId());
        }

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);
            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        semaphore.release();
    }
}

