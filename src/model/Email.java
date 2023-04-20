package model;

import java.io.Serializable;

public class Email implements Serializable {


    static final long serialVersionUID = 43L;
    private String emailAddress;
    private boolean isRegistered = false;


    public Email(String email) {
        this.emailAddress = email;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
