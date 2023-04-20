package database.user;

import database.Database;
import model.UserInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUser {

    private Database db;

    public DatabaseUser(){
        db = new Database();
    }


    public void newUser(UserInfo userInfo) {

        Connection con = db.getDatabaseConnection();

        String QUERY =  String.format("INSERT INTO users(user_name, user_email, user_password) " +
                "VALUES ('%s', '%s', '%s')", userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserInfo checkUserInfo(UserInfo userInfo) {

        Connection con = db.getDatabaseConnection();
        String QUERY =  String.format("SELECT * FROM users");

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            boolean emailChecked = false;
            while (rs.next() && !emailChecked){
                if(rs.getString("user_email").trim().equals(userInfo.getEmail())) {
                    emailChecked = true;
                    if (rs.getString("user_password").trim().equals(userInfo.getPassword())) {
                        userInfo.setCorrectInfo(true);
                    }
                }
            }
            userInfo.setUserId(getUserId(userInfo));
            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }

    public int getUserId(UserInfo userInfo) throws SQLException {
        int userId = 0;
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT user_id FROM users WHERE user_email LIKE '%s' AND user_password LIKE '%s' ",
                userInfo.getEmail(), userInfo.getPassword());

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        while(rs.next()) {
             userId = rs.getInt("user_id");
        }

        stmt.close();
        con.close();
        db.terminateIdle();

        return userId;
    }

    public boolean checkEmail(String email) {

        Connection con = db.getDatabaseConnection();
        String QUERY =  String.format("SELECT COUNT(*) FROM users WHERE user_email LIKE '%s'", email);
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
            db.terminateIdle();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
