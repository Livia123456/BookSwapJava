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
     * Adds a book from parameters.
     */
    //public void addBook(int user_id, String title, String author, String release_year, String genre, String imagePath) {
    public void addBook(Book book) {
        //todo ändra satt den tar emot en bok istället
        System.out.println("Vi kom hit");

        Connection con = db.getDatabaseConnection();
        String QUERY = "";

        if (book.getRelease_date() == null || book.getRelease_date().isEmpty() || book.getRelease_date().isBlank()) {
            QUERY = String.format("insert into book(book_id, user_id, title, author, genre, image_path)" +
                            "values(default, %d, '%s', '%s', '%s', '%s');",
                    book.getUploadedBy().getUserId(), book.getTitle(), book.getAuthor(),
                    book.getGenre(), "imagepath...");

        } else {

            QUERY = String.format("insert into book(book_id, user_id, title, author, release_year, genre, image_path)" +
                            "values(default, %d, '%s', '%s', '%s', '%s', '%s');",
                    book.getUploadedBy().getUserId(), book.getTitle(), book.getAuthor(), book.getRelease_date(),
                    book.getGenre(), "imagepath...");
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
    }
}

