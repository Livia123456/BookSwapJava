package database.search;

import database.Database;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles the database query's associated with the search function.
 * @author Kasper
 */
public class DatabaseSearch {

    private Database db;

    public DatabaseSearch(){
        db = new Database();
    }


    /**
     * Collects book and user objects and returns them together as an Arraylist.
     */
    public ArrayList<SearchAble> search(String search) {

        ArrayList<SearchAble> searchAble = getBooks(search);
        //searchAble.addAll(getUsers(search));

        return searchAble;
    }


    /**
     * Fetches and returns the user objects from the database based on the search String provided by the user.
     */
    private ArrayList<SearchAble> getUsers(String search) {

        ArrayList<SearchAble> users = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT * FROM users WHERE " +
                "user_name LIKE '%s' OR " +
                "user_email LIKE '%s'", search);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                UserInfo user = new UserInfo(rs.getInt("user_id"),
                        rs.getString("user_name"),
                        (new Email(rs.getString("user_email"))));

                users.add(user);
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    /**
     * Fetches and returns the book objects from the database based on the search String provided by the user.
     */
    private ArrayList<SearchAble> getBooks(String search) {

        ArrayList<SearchAble> books = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT * FROM book WHERE title ILIKE '%s' OR " +
                "author ILIKE '%s' OR " +
                "release_year :: text ILIKE '%s' OR " +
                "genre ILIKE '%s' OR " +
                "edition ILIKE '%s' OR " +
                "publisher ILIKE '%s' OR " +
                "isbn ILIKE '%s'", search, search, search, search, search, search, search);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
               Book book = new Book.BookBuilder().bookId(rs.getInt("book_id")).
                       title(rs.getString("title")).
                       author(rs.getString("author")).
                       release_date(String.valueOf(rs.getDate("release_year"))).
                       genre(rs.getString("genre")).
                       edition(rs.getString("edition")).
                       publisher(rs.getString("publisher")).
                       isbn(rs.getString("isbn")).build();

               books.add(book);
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }


}
