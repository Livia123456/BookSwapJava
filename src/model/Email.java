package model;

import java.io.Serializable;

/**
 * Class to store an email address.
 * @author Livia Tengelin, Kasper Lindberg
 */
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

