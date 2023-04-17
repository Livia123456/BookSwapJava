package database;


import database.books.DB_books;
import database.user.DB_user;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class that tests the database and it's querys.
 */
public class DBtest {

    public void printArray(ArrayList<String> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main(String[] args) throws SQLException {

        DBtest test = new DBtest();

        DB_user user_test = new DB_user();

        DB_books book_test = new DB_books();


        /**
         * Tests when a book is added.
         */
        /*book_test.addBook(1, "Frankenstein", "Mary Shelley", "1818-01-01",
                "123456789", "Sci-fi", "files/frankenstein.jpg"); */


        /**
         * Prints the books (titles) added by a specific user.
         */
        /*test.printArray(book_test.getTitlesByUser(1)); */

        test.printArray(book_test.getBook(1));


    }

}
