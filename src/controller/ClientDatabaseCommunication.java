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
                        System.out.println("Correct!");
                    }
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("hallå");
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

    public boolean checkEmail(String email) {
        Connection con = database.getDatabaseConnection();
        String QUERY =  String.format("SELECT count(*) FROM users where user_email = '%s'", email);
        boolean result = false;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                if (rs.getInt("count") == 0) {
                    result = true;
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void newUser(UserInfo userInfo) { //todo detta e ej testat!! pga pgadmin >:(
        Connection con = database.getDatabaseConnection();
        String QUERY =  String.format("INSERT INTO users(user_name, user_email, user_password) " +
                        "values ('%s', '%s', '%s')", userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
