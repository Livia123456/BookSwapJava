package model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    static final long serialVersionUID = 42L;
    private String email;
    private String password;
    private String name;
    private int userId;
    private boolean correctInfo = false;

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
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
