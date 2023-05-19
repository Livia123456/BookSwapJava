package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.chat.*;

import javax.swing.*;

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
    private ImageIcon profileImage;

//TODO skapa builder!!

    public UserInfo() {}

    public UserInfo(Email email, String password) {
        this.email = email;
        this.password = password;
        this.currentUsersUploadedBooks = new ArrayList<>(); //todo
        this.chatsWith = new ArrayList<>();
    }

    public UserInfo(int userId, String name, Email email) {
        this.email = email;
        this.userId = userId;
        this.name = name;
    }

    public UserInfo(String name, int userId) {
        this.userId = userId;
        this.name = name;
    }

    public UserInfo(int userId) {
        this.userId = userId;
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

    public ImageIcon getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(ImageIcon profileImage) {
        this.profileImage = profileImage;
    }

    private class UserBuilder {
        private UserInfo userInfo;

        public UserBuilder() {
            userInfo = new UserInfo();
        }

        public UserBuilder email(Email email){
            userInfo.email = email;
            return this;
        }

        public UserBuilder name(String name) {
            userInfo.name = name;
            return this;
        }

        public UserBuilder userId(int userId) {
            userInfo.userId = userId;
            return this;
        }

        public UserBuilder password(String password) {
            userInfo.password = password;
            return this;
        }

        public UserBuilder correctInfo(boolean correctInfo) {
            userInfo.correctInfo = correctInfo;
            return this;
        }

        public UserBuilder uploadedBooks(ArrayList<Book> uploadedBooks) {
            userInfo.currentUsersUploadedBooks = uploadedBooks;
            return this;
        }

        public UserBuilder chatsWith(ArrayList<ChatsWith> chatsWiths) {
            userInfo.chatsWith = chatsWiths;
            return this;
        }

        public UserBuilder profileImage(ImageIcon imageIcon) {
            userInfo.profileImage = imageIcon;
            return this;
        }

        public UserInfo build() {
            return userInfo;
        }
    }
}
