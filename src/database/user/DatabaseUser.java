package database.user;

import database.Database;
import model.AccountToDelete;
import model.UserInfo;
import model.UserInfoUpdate;

import java.sql.*;
import java.util.concurrent.Semaphore;

public class DatabaseUser {

    private Database db;
    private Semaphore semaphore;

    public DatabaseUser(){
        db = Database.getInstance();
        semaphore = db.getDbSemaphore();
    }


    public void newUser(UserInfo userInfo) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Connection con = db.getDatabaseConnection();

        String QUERY =  String.format("INSERT INTO users(user_name, user_email, user_password) " +
                "VALUES ('%s', '%s', '%s')", userInfo.getName(), userInfo.getEmail().getEmailAddress(), userInfo.getPassword());

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();

            int userId = getUserId(userInfo);
            userInfo.setUserId(userId);

        } catch (org.postgresql.util.PSQLException e) {}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
    }

    public UserInfo checkUserInfo(UserInfo userInfo) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Connection con = db.getDatabaseConnection();
        String QUERY =  String.format("SELECT * FROM users");

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            boolean emailChecked = false;
            while (rs.next() && !emailChecked){
                if(rs.getString("user_email").trim().equals(userInfo.getEmail().getEmailAddress())) {
                    emailChecked = true;
                    if (rs.getString("user_password").trim().equals(userInfo.getPassword())) {
                        userInfo.setCorrectInfo(true);
                    }
                    userInfo.setName(rs.getString("user_name"));
                    userInfo.setUserId(rs.getInt("user_id"));
                }
            }

            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
        return userInfo;
    }

    public void updateUserInfo(int currentUserId, UserInfoUpdate newUserInfo) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String newName = newUserInfo.getName();
        String newPassword = newUserInfo.getPassword();
        String newEmail = newUserInfo.getEmail().getEmailAddress();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("UPDATE users SET user_name = '%s', user_email = '%s', user_password = '%s' WHERE user_id = '%s'",
                newName, newPassword, newEmail, currentUserId);

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public void removeUserFromDatabase(int userId) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("CALL delete_user(%d)", userId);

        try {
            CallableStatement stmt = con.prepareCall(QUERY);
            stmt.executeUpdate();

            stmt.close();
            con.close();
            db.terminateIdle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public int getUserId(UserInfo userInfo) throws SQLException {
        int userId = 0;

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT user_id FROM users WHERE user_email LIKE '%s' AND user_password LIKE '%s' ",
                userInfo.getEmail().getEmailAddress(), userInfo.getPassword());

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
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        semaphore.release();
        return result;
    }

}
