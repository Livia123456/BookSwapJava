package database.books;

import database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_books {


    public String[] getBook() throws SQLException {     //EJ KLAR

        Database db = new Database();
        Connection con = db.getDatabaseConnection();

        String[] book = new String[8];


        String QUERY = String.format("select * from book where book_id = 2");
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(QUERY);
        while (rs.next())
        {
            System.out.println(rs.getString("title"));
        }

        return book;
    }

}
