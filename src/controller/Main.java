package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {



            System.out.println("Hello world!");
            Database db = new Database();
            Connection con = db.getDatabaseConnection();
            //connectionUtil.getDatabaseConnection();
            String QUERY = String.format("select title from book where book_id = 2");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(QUERY);
            while (rs.next())
            {
                System.out.println(rs.getString("title"));
            }


    }
}