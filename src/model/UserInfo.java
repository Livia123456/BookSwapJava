package model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    static final long serialVersionUID = 42L;
    private String name;
    private String password;

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
