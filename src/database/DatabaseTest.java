package database;


import database.books.DatabaseBooks;
import database.chat.DatabaseChat;
import database.search.DatabaseSearch;
import database.user.DatabaseUser;
import model.SearchAble;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class that tests the database and it's querys.
 */
public class DatabaseTest {

    public void printArray(ArrayList<String> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main(String[] args) throws SQLException {

        DatabaseSearch searchTest = new DatabaseSearch();

        ArrayList<SearchAble> s = searchTest.search("bibeln");

        for (int i = 0; i < s.size(); i++) {
            System.out.println(s.get(i));

        }

       // DatabaseTest test = new DatabaseTest();

       // DatabaseUser userTest = new DatabaseUser();

       // DatabaseChat chatTest = new DatabaseChat();

       // DatabaseBooks bookTest = new DatabaseBooks();


        /**
         * Tests when a book is added.
         */
        /*book_test.addBook(1, "Frankenstein", "Mary Shelley", "1818-01-01",
                "123456789", "Sci-fi", "files/frankenstein.jpg"); */


        /**
         * Prints the books (titles) added by a specific user.
         */
        //test.printArray(bookTest.getTitlesByUser(1));

        /**
         * Not done
         */
        /* test.printArray(book_test.getBook(1)); */


    }

}
