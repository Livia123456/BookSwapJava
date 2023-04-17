package model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    static final long serialVersionUID = 42L;
    private String email;
    private String password;
    private String name;
    private boolean correctInfo = false;

    public UserInfo(String name, String password) {
        this.email = name;
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

    public void setName(String name) {
        this.name = name;
    }
}
