package database;

import java.sql.*;

public class Database {


    /**
     * Creates and returns a connection to the DB.
     */
    public Connection getDatabaseConnection(){

        String url = "jdbc:postgresql://pgserver.mau.se:5432/bookswap";
        String user = "an8043";
        String password = "nlbpxnn6";

        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
