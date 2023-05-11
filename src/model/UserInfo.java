package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.chat.*;

/**
 * Class to store information about a user.
 * @author Livia Tengelin, Kasper Lindberg, Sebasitan Zulj
 */
public class UserInfo implements Serializable {

    static final long serialVersionUID = 42L;
    private Email email;
    private String password;
    private String name;
    private int userId;
    private boolean correctInfo = false;
    private ArrayList<Book> currentUsersUploadedBooks;
    private ArrayList<ChatsWith> chatsWith;



    public UserInfo(Email email, String password) {
        this.email = email;
        this.password = password;
        this.currentUsersUploadedBooks = new ArrayList<>();
        this.chatsWith = new ArrayList<>();
    }

    public ArrayList<ChatsWith> getChatsWith() {
        return chatsWith;
    }

    public void setChatsWith(ArrayList<ChatsWith> chatsWith) {
        this.chatsWith = chatsWith;
    }

    public void setCurrentUsersUploadedBooks(ArrayList<Book> currentUsersUploadedBooks) {
        this.currentUsersUploadedBooks = currentUsersUploadedBooks;
    }

    public ArrayList<Book> getCurrentUsersUploadedBooks() {
        return currentUsersUploadedBooks;
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
