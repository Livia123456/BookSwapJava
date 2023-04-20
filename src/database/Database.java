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

    public void terminateIdle() throws SQLException{

        Connection con = this.getDatabaseConnection();
        String selectPids = "SELECT pid from pg_stat_activity where state = 'idle'";
        Statement getPids = con.createStatement();
        ResultSet rs = getPids.executeQuery(selectPids);

        while (rs.next()){
            PreparedStatement pstmt = con.prepareStatement("Select pg_terminate_backend(?)");
            pstmt.setInt(1, rs.getInt("pid"));
            pstmt.execute();
        }

        rs.close();
        getPids.close();
        con.close();
    }
}
