package database.search;

import database.Database;
import model.Book;
import model.SearchAble;
import model.SearchObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseSearch {

    private Database db;

    public DatabaseSearch(){
        db = new Database();
    }


    public ArrayList<SearchAble> search(String search) {

        ArrayList<Book> books = getBooks(search);

        ArrayList<SearchAble> searchAble = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("");

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {

            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return searchAble;
    }

    private ArrayList<Book> getBooks(String search) {

        ArrayList<Book> books = new ArrayList<>();


        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT * FROM book WHERE title LIKE '%s' OR " +
                "author LIKE '%s' OR " +
                "release_year LIKE '%s' OR " +
                "genre LIKE '%s' OR " +
                "edition LIKE '%s' OR " +
                "publisher LIKE '%s' OR " +
                "isbn LIKE '%s'", search);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
               Book book = new Book.BookBuilder().title(rs.getString("title"))
                       .author(rs.getString("author")).
                       release_date(rs.getString("release_year")).
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
