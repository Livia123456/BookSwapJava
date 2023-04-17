package controller;

import model.UserInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDatabaseCommunication {
    private Database database;

    public ClientDatabaseCommunication() {
        database = new Database();
    }

    public UserInfo checkUserInfo(UserInfo message) {
        Connection con = database.getDatabaseConnection();
        String QUERY =  String.format("SELECT * FROM users");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            boolean emailChecked = false;
            while (rs.next() && !emailChecked){
                if(rs.getString("user_email").trim().equals(message.getEmail())) {
                    emailChecked = true;
                    if (rs.getString("user_password").trim().equals(message.getPassword())) {
                        message.setCorrectInfo(true);
                        //System.out.println("Correct!");
                    }
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return message;

    }

    private void test() {
        Connection con = database.getDatabaseConnection();
        String QUERY =  String.format("SELECT * FROM users");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                System.out.println(rs.getString("user_email") + " " +
                        rs.getString("user_password") );
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ClientDatabaseCommunication cdc = new ClientDatabaseCommunication();
        cdc.test();
    }
}
