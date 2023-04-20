package model;

import java.io.Serializable;

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


    public UserInfo(Email email, String password) {
        this.email = email;
        this.password = password;
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
