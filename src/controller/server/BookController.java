package controller.server;

import database.books.DatabaseBooks;
import model.Book;
import model.BookToDelete;
import model.BookUpdate;
import model.UpdateBookList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is responsible for all the book-related communications with the database.
 * @author Livia Tengelin, Sebastian Zulj
 */

public class BookController {

    private ObjectOutputStream oos;

    private DatabaseBooks dbBook;
    private ClientHandler clientHandler;

    public BookController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.oos = clientHandler.getOOS();
        dbBook = new DatabaseBooks(this);
    }

    public void uploadBook(Book book) {
        book.setUploadedBy(clientHandler.getCurrentUser());
        clientHandler.sendMessage(dbBook.addBook(book));
        updateCurrentUsersBooks();
    }

    public void deleteBook(int bookId) {
        dbBook.deleteBook(bookId);
        updateCurrentUsersBooks();
    }

    private void updateCurrentUsersBooks() {
        ArrayList<Book> books = loadCurrentUsersUploadedBooks();
        clientHandler.getCurrentUser().setCurrentUsersUploadedBooks(books);
        clientHandler.sendMessage(new UpdateBookList(books));
    }

    public ArrayList<Book> loadCurrentUsersUploadedBooks() {
        return dbBook.getBooksUploadedByUser(clientHandler.getCurrentUser().getUserId());
    }

    public ArrayList<Book> loadBooksUploadedByUser(int userID) {
        return dbBook.getBooksUploadedByUser(userID);
    }
    public void updateBook(BookUpdate book) {
        clientHandler.sendMessage(dbBook.updateBookInfo(book));
        updateCurrentUsersBooks();
    }
}
