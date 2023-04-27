package controller.server;

import database.books.DatabaseBooks;
import model.Book;

import java.io.IOException;
import java.io.ObjectOutputStream;

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

}
