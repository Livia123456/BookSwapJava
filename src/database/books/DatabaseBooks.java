package database.books;

import database.Database;
import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseBooks {

    private Database db;

    public DatabaseBooks(){
        db = new Database();
    }

    public ArrayList<String> getBook(int book_id) throws SQLException { //todo: ej klar

        ArrayList<String> book = new ArrayList<>();

        Connection con = db.getDatabaseConnection();


        String QUERY = String.format("select * from book where book_id = %d", book_id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);
        while (rs.next())
        {


        }

        stmt.close();
        con.close();
        db.terminateIdle();

        return book;
    }


    /**
     * Returns title of a book from user_id.
     */
    public ArrayList<String> getTitlesByUser(int user_id) throws SQLException {

        ArrayList<String> titles = new ArrayList<>();

        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("select title from book where user_id = %d", user_id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        while (rs.next()) {
            titles.add(rs.getString("title"));
        }

        stmt.close();
        con.close();
        db.terminateIdle();

        return titles;
    }

    /**
     * Adds a book from parameters.
     */
    //public void addBook(int user_id, String title, String author, String release_year, String genre, String imagePath) {
    public void addBook(Book book) {
        //todo ändra satt den tar emot en bok istället
        System.out.println("Vi kom hit");

        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("insert into book(book_id, user_id, title, author, release_year, genre, image_path)" +
                "values(default, %d, '%s', '%s', '%s', '%s', '%s');",
                book.getUploadedBy().getUserId(), book.getTitle(), book.getAuthor(), book.getRelease_date(),
                book.getGenre(), "imagepath...");
                //user_id, title, author, release_year, genre, imagePath);
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            QUERY = "SELECT * from book";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }

            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (Exception e) {
             e.printStackTrace();

        }
    }
}

