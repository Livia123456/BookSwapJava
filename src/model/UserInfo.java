package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to store information about a user.
 * @author Livia Tengelin, Kasper Lindberg, Sebasitan Zulj
 */
public class UserInfo implements Serializable, SearchAble{

    static final long serialVersionUID = 42L;
    private Email email;
    private String password;
    private String name;
    private int userId;
    private boolean correctInfo = false;
    private ArrayList<Book> currentUsersUploadedBooks;


    public void setCurrentUsersUploadedBooks(ArrayList<Book> currentUsersUploadedBooks) {
        this.currentUsersUploadedBooks = currentUsersUploadedBooks;
    }

    public ArrayList<Book> getCurrentUsersUploadedBooks() {
        return currentUsersUploadedBooks;
    }

    public UserInfo(Email email, String password) {
        this.email = email;
        this.password = password;
        this.currentUsersUploadedBooks = new ArrayList<>();
    }

    public UserInfo(int userId, String name, Email email){
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCorrectInfo() {
        return correctInfo;
    }

    public void setCorrectInfo(boolean correctInfo) {
        this.correctInfo = correctInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
