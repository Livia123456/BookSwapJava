package database.books;

import database.Database;

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

    public ArrayList<String> getBook(int book_id) { //todo: ej klar

        ArrayList<String> book = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("select * from book where book_id = %d", book_id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {

            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch(Exception e){
            e.printStackTrace();
        }
        return book;
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
    public void addBook(int user_id, String title, String author, String release_year, String genre, String imagePath) {

        System.out.println("Vi kom hit");

        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("insert into book(book_id, user_id, title, author, release_year, genre, image_path)" +
                "values(default, %d, '%s', '%s', '%s', '%s', '%s');",
                user_id, title, author, release_year, genre, imagePath);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();
        }catch(Exception e){
             e.printStackTrace();

        }
    }
}

