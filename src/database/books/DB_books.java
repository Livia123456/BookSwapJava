package database.books;

import database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_books {


    public ArrayList<String> getBook(int book_id) throws SQLException {

        ArrayList<String> book = new ArrayList<>();

        Database db = new Database();
        Connection con = db.getDatabaseConnection();


        String QUERY = String.format("select * from book where book_id = %d", book_id);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(QUERY);
        while (rs.next())
        {


        }

        con.close();

        return book;
    }


    /**
     * Returns title of a book from user_id.
     */
    public ArrayList<String> getTitlesByUser(int user_id) throws SQLException {

        ArrayList<String> titles = new ArrayList<>();

        Database db = new Database();
        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("select title from book where user_id = %d", user_id);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(QUERY);

        while (rs.next()) {
            titles.add(rs.getString("title"));
        }

        con.close();

        return titles;
    }

    /**
     * Adds a book from parameters.
     */
    public void addBook(int user_id, String title, String author, String release_year, String isbn, String genre, String imagePath) throws SQLException {

        Database db = new Database();
        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("insert into book(book_id, user_id, title, author, release_year, isbn, genre, image_path)" +
                "values(default, %d, '%s', '%s', '%s', '%s', '%s', '%s');",
                user_id, title, author, release_year, isbn, genre, imagePath);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(QUERY);

        con.close();

    }

}
