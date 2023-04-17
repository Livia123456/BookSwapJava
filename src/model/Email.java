package model;

import java.io.Serializable;

public class Email implements Serializable {
    static final long serialVersionUID = 43L;
    private String email;
    private boolean isRegistered = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
