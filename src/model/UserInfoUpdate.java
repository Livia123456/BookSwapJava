package model;

import java.io.Serializable;

public class UserInfoUpdate implements Serializable {

    static final long serialVersionUID = 52L;
    private Email email;
    private String password;
    private String name;

    public UserInfoUpdate(Email email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
