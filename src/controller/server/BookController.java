package controller.server;

import database.books.DatabaseBooks;
import model.Book;
import model.BookToDelete;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
        (book).setUploadedBy(clientHandler.getCurrentUser());
        clientHandler.sendMessage(dbBook.addBook(book));
    }

    public void deleteBook(int bookId) {
        dbBook.deleteBook(bookId);
        ArrayList<Book> books = clientHandler.getCurrentUser().getCurrentUsersUploadedBooks();
       /* for (Book: //TODO skicka tillbaka lista med böcker som är aktuell!!!
             ) {
            
        }*/
    }

    public ArrayList<Book> loadCurrentUsersUploadedBooks() {
        return dbBook.getBooksUploadedByUser(clientHandler.getCurrentUser().getUserId());
    }

}
